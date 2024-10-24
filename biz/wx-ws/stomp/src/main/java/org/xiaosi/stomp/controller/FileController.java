package org.xiaosi.stomp.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xiaosi.stomp.entity.MultipartFileParam;
import org.xiaosi.stomp.service.ChatService;
import org.xiaosi.stomp.utils.FileDfsUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    @Resource
    private FileDfsUtil fileDfsUtil;

    @Value("${fdfs.thumbImage.width}")
    private Integer width;

    @Value("${fdfs.thumbImage.height}")
    private Integer height;

    @Resource
    private ChatService chatService;

    /**
     * 文件上传
     */
    @PostMapping(value = "/uploadFile", headers = "content-type=multipart/form-data")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        long s1 = System.currentTimeMillis();
        String path = fileDfsUtil.upload(file);
        long s2 = System.currentTimeMillis();
        LOGGER.info("耗时:" + (s2 - s1));
        //实际开发中,应该将path信息,存入数据库,和file进行关联,用户下载时只需要传入fileId即可
        //返回的路径，是不存在http地址，group1/M00/00/00/1312312312.txt,具体的前缀由前端拼接
        return ResponseEntity.ok(path);
    }

    /**
     * 上传缩略图
     * 一般不建议使用fastDFS缩略图,实际开发中,应该自定义尺寸后再进行上传
     */
    @PostMapping(value = "/uploadThumbImage", headers = "content-type=multipart/form-data")
    public ResponseEntity<List<String>> uploadThumbImage(@RequestParam("file") MultipartFile file) {
        String path = fileDfsUtil.uploadThumbImage(file);
        //上传缩略图以后,服务器会生成两个文件,一个是原图,一个缩略图,并且缩略图的名称为原图+"_width*height"
        //group1/M00/00/00/1123.jpg与group1/M00/00/00/1123_150x150.jpg
        //设置缩略图路径
        String prePath = StringUtils.substringBeforeLast(path, ".");
        String suffix = StringUtils.substringAfterLast(path, ".");
        String imagePath = prePath + "_" + width + "x" + height + "." + suffix;
        List<String> paths = new ArrayList<>();
        paths.add(path);
        paths.add(imagePath);
        return ResponseEntity.ok(paths);
    }

    /**
     * 文件删除
     */
    @DeleteMapping
    public ResponseEntity<String> deleteByPath(String path) {
        long s1 = System.currentTimeMillis();
        fileDfsUtil.deleteFile(path);
        long s2 = System.currentTimeMillis();
        LOGGER.info("耗时:" + (s2 - s1));
        return ResponseEntity.ok("SUCCESS");
    }

//    @ApiOperation("大文件分片上传")
    @PostMapping("/chunkUpload")
    public void fileChunkUpload(MultipartFileParam param, HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        System.out.println(param+"--------------------------");
        //自己的业务获取存储路径，可以换成自己的
//        OSSInformation ossInformation = ossInformationService.queryOne();
//        String root = ossInformation.getRoot();
        //验证文件夹规则,不能包含特殊字符
//        File file = new File(root);
//        createDirectoryQuietly(file);

//        String path=file.getAbsolutePath();
        response.setContentType("text/html;charset=UTF-8");
        // response.setStatus对接前端插件
        //        200, 201, 202: 当前块上传成功，不需要重传。
        //        404, 415. 500, 501: 当前块上传失败，会取消整个文件上传。
        //        其他状态码: 出错了，但是会自动重试上传。

//        try {
//            /**
//             * 判断前端Form表单格式是否支持文件上传
//             */
//            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
//            if(!isMultipart){
//                //这里是我向前端发送数据的代码，可理解为 return 数据; 具体的就不贴了
//                System.out.println("不支持的表单格式");
//                response.setStatus(404);
//                response.getOutputStream().write("不支持的表单格式".getBytes());
//            }else {
//                param.setTaskId(param.getIdentifier());
//                materialService.chunkUploadByMappedByteBuffer(param,path);//service层
//                response.setStatus(200);
//                response.getWriter().print("上传成功");
//            }
//            response.getWriter().flush();
//
//        }
//        catch (NotSameFileExpection e){
//            response.setStatus(501);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("上传文件失败");
//            response.setStatus(415);
//        }

        param.setTaskId(param.getIdentifier());
        chatService.chunkUploadByMappedByteBuffer(param,"d:/test");//service层
        response.setStatus(200);
        response.getWriter().print("上传成功");
        response.getWriter().flush();
    }
}
