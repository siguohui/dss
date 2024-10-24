package org.xiaosi.stomp.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.xiaosi.stomp.entity.ChatMessage;
import org.xiaosi.stomp.entity.MultipartFileParam;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.UUID;

/**
 * @Author : JCccc
 * @CreateTime : 2020/8/26
 * @Description :
 **/
@Service
public class ChatService {

    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    public Boolean sendMsg(String msg) {
        try {
            JSONObject msgJson = JSONObject.parseObject(msg);
            if (msgJson.getString("to").equals("all") && msgJson.getString("type").equals(ChatMessage.MessageType.CHAT.toString())){
                simpMessageSendingOperations.convertAndSend("/topic/public", msgJson);

            }else if (msgJson.getString("to").equals("all") && msgJson.getString("type").equals(ChatMessage.MessageType.JOIN.toString())) {
                simpMessageSendingOperations.convertAndSend("/topic/public", msgJson);

            }else if(msgJson.getString("to").equals("all") &&  msgJson.getString("type").equals(ChatMessage.MessageType.LEAVE.toString())) {
                simpMessageSendingOperations.convertAndSend("/topic/public", msgJson);

            }else if (!msgJson.getString("to").equals("all") &&  msgJson.getString("type").equals(ChatMessage.MessageType.CHAT.toString())){
                simpMessageSendingOperations.convertAndSendToUser(msgJson.getString("to"),"/topic/msg", msgJson);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }


    public String chunkUploadByMappedByteBuffer(MultipartFileParam param, String filePath) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        if(param.getTaskId() == null || "".equals(param.getTaskId())){
            param.setTaskId(UUID.randomUUID().toString());
        }
        /**
         *
         * 1：创建临时文件，和源文件一个路径
         * 2：如果文件路径不存在重新创建
         */
        String fileName = param.getFile().getOriginalFilename();
        String tempFileName = param.getTaskId() + fileName.substring(fileName.lastIndexOf(".")) + "_tmp";
        File fileDir = new File(filePath);
        if(!fileDir.exists()){
            fileDir.mkdirs();
        }
        File tempFile = new File(filePath,tempFileName);
        //第一步
        RandomAccessFile raf = new RandomAccessFile(tempFile,"rw");
        //第二步
        FileChannel fileChannel = raf.getChannel();
        //第三步 计算偏移量
        long position = (param.getChunkNumber()-1) * param.getChunkSize();
        //第四步
        byte[] fileData = param.getFile().getBytes();
        //第五步
        long end=position+fileData.length-1;
        fileChannel.position(position);
        fileChannel.write(ByteBuffer.wrap(fileData));
        //使用 fileChannel.map的方式速度更快，但是容易产生IO操作，无建议使用
//        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE,position,fileData.length);
//        //第六步
//        mappedByteBuffer.put(fileData);
        //第七步
//        freedMappedByteBuffer(mappedByteBuffer);
//        Method method = FileChannelImpl.class.getDeclaredMethod("unmap", MappedByteBuffer.class);
//        method.setAccessible(true);
//        method.invoke(FileChannelImpl.class, mappedByteBuffer);
        fileChannel.force(true);
        fileChannel.close();
        raf.close();
        //第八步
        boolean isComplete = checkUploadStatus(param,fileName,filePath);
        if(isComplete){
            //重命名文件，然后校验MD5文件是否一致
            String md5= DigestUtils.md5Hex(new FileInputStream(tempFile.getPath()));
            renameFile(tempFile,fileName);
            if(StringUtils.isNotBlank(md5) && !md5.equals(param.getIdentifier())){
                //不是同一文件抛出异常
                throw new RuntimeException();
            }
        }
        return param.getTaskId();
    }

    public void renameFile(File toBeRenamed, String toFileNewName) {
        //检查要重命名的文件是否存在，是否是文件
        if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {
            System.out.println("文件不存在");
            return;
        }
        String p = toBeRenamed.getParent();
        File newFile = new File(p + File.separatorChar + toFileNewName);
        //修改文件名
        toBeRenamed.renameTo(newFile);
    }

    public boolean checkUploadStatus(MultipartFileParam param,String fileName,String filePath) throws IOException {
        File confFile = new File(filePath,fileName+".conf");
        RandomAccessFile confAccessFile = new RandomAccessFile(confFile,"rw");
        //设置文件长度
        confAccessFile.setLength(param.getTotalChunks());
        //设置起始偏移量
        confAccessFile.seek(param.getChunkNumber()-1);
        //将指定的一个字节写入文件中 127，
        confAccessFile.write(Byte.MAX_VALUE);
        byte[] completeStatusList = FileUtils.readFileToByteArray(confFile);
        confAccessFile.close();//不关闭会造成无法占用
        //这一段逻辑有点复杂，看的时候思考了好久，创建conf文件文件长度为总分片数，每上传一个分块即向conf文件中写入一个127，那么没上传的位置就是默认的0,已上传的就是Byte.MAX_VALUE 127
        for(int i = 0; i<completeStatusList.length; i++){
            if(completeStatusList[i]!=Byte.MAX_VALUE){
                return false;
            }
        }
        //如果全部文件上传完成，删除conf文件
        confFile.delete();
        return true;
    }

    /**
     * 在MappedByteBuffer释放后再对它进行读操作的话就会引发jvm crash，在并发情况下很容易发生
     * 正在释放时另一个线程正开始读取，于是crash就发生了。所以为了系统稳定性释放前一般需要检 查是否还有线程在读或写
     * @param mappedByteBuffer
     */
    /*public static void freedMappedByteBuffer(final MappedByteBuffer mappedByteBuffer) {
        try {
            if (mappedByteBuffer == null) {
                return;
            }
            mappedByteBuffer.force();
            AccessController.doPrivileged(new PrivilegedAction<Object>() {
                @Override
                public Object run() {
                    try {
                        Method getCleanerMethod = mappedByteBuffer.getClass().getMethod("cleaner", new Class[0]);
                        //可以访问private的权限
                        getCleanerMethod.setAccessible(true);
                        //在具有指定参数的 方法对象上调用此 方法对象表示的底层方法
                        sun.misc.Cleaner cleaner = (sun.misc.Cleaner) getCleanerMethod.invoke(mappedByteBuffer,
                                new Object[0]);
                        cleaner.clean();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("清理缓存出错!!!"+e.getMessage());
                    }
                    System.out.println("缓存清理完毕!!!");
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
