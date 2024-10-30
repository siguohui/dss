package com.xiaosi.back.service.impl;

import com.xiaosi.back.commons.CommonConstant;
import com.xiaosi.back.commons.reps.AjaxResult;
import com.xiaosi.back.entity.BackChunk;
import com.xiaosi.back.entity.BackFileList;
import com.xiaosi.back.commons.vo.CheckChunkVO;
import com.xiaosi.back.mapper.BackChunkMapper;
import com.xiaosi.back.mapper.BackFileListMapper;
import com.xiaosi.back.service.IBackFileService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BackFileServiceImpl implements IBackFileService {
    @Value("${config.upload-path}")
    private String uploadPath;

    private final static String folderPath = "/file";

    @Autowired
    private BackChunkMapper backChunkMapper;

    @Autowired
    private BackFileListMapper backFileListMapper;

    /**
     * 每一个上传块都会包含如下分块信息：
     * chunkNumber: 当前块的次序，第一个块是 1，注意不是从 0 开始的。
     * totalChunks: 文件被分成块的总数。
     * chunkSize: 分块大小，根据 totalSize 和这个值你就可以计算出总共的块数。注意最后一块的大小可能会比这个要大。
     * currentChunkSize: 当前块的大小，实际大小。
     * totalSize: 文件总大小。
     * identifier: 这个就是每个文件的唯一标示,md5码
     * filename: 文件名。
     * relativePath: 文件夹上传的时候文件的相对路径属性。
     * 一个分块可以被上传多次，当然这肯定不是标准行为，但是在实际上传过程中是可能发生这种事情的，这种重传也是本库的特性之一。
     * <p>
     * 根据响应码认为成功或失败的：
     * 200 文件上传完成
     * 201 文加快上传成功
     * 500 第一块上传失败，取消整个文件上传
     * 507 服务器出错自动重试该文件块上传
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int postFileUpload(BackChunk chunk, HttpServletResponse response) {
        int result = CommonConstant.UPDATE_FAIL;
        MultipartFile file = chunk.getFile();
        log.debug("file originName: {}, chunkNumber: {}", file.getOriginalFilename(), chunk.getChunkNumber());
        Path path = Paths.get(generatePath(uploadPath + folderPath, chunk));
        try {
            Files.write(path, chunk.getFile().getBytes());
            log.debug("文件 {} 写入成功, md5:{}", chunk.getFilename(), chunk.getIdentifier());
            result = backChunkMapper.insertBackChunk(chunk);
            //写入数据库
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(507);
            return CommonConstant.UPDATE_FAIL;
        }
        return result;
    }

    @Override
    public CheckChunkVO getFileUpload(BackChunk chunk, HttpServletResponse response) {
        CheckChunkVO vo = new CheckChunkVO();
        //检查该文件是否存在于 backFileList 中,如果存在,直接返回skipUpload为true,执行闪传
        BackFileList backFileList = new BackFileList();
        backFileList.setIdentifier(chunk.getIdentifier());
        List<BackFileList> BackFileLists = backFileListMapper.selectBackFileListList(backFileList);
        if (BackFileLists != null && !BackFileLists.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            vo.setSkipUpload(true);
            return vo;
        }

        BackChunk resultChunk = new BackChunk();
        resultChunk.setIdentifier(chunk.getIdentifier());
        List<BackChunk> backChunks = backChunkMapper.selectBackChunkList(resultChunk);
        //将已存在的块的chunkNumber列表返回给前端,前端会规避掉这些块
        if (backChunks != null && !backChunks.isEmpty()) {
            List<Integer> collect = backChunks.stream().map(BackChunk::getChunkNumber).collect(Collectors.toList());
            vo.setUploaded(collect);
        }
        return vo;
    }

    @Override
    public int deleteBackFileByIds(Long id) {
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int mergeFile(BackFileList fileInfo) {
        String filename = fileInfo.getFilename();
        String file = uploadPath + folderPath + "/" + fileInfo.getIdentifier() + "/" + filename;
        String folder = uploadPath + folderPath + "/" + fileInfo.getIdentifier();
        String url = folderPath + "/" + fileInfo.getIdentifier() + "/" + filename;
        merge(file, folder, filename);
        //当前文件已存在数据库中时,返回已存在标识
        if (backFileListMapper.selectSingleBackFileList(fileInfo) > 0) {
            return CommonConstant.UPDATE_EXISTS;
        }
        fileInfo.setLocation(file);
        fileInfo.setUrl(url);
        int i = backFileListMapper.insertBackFileList(fileInfo);
        if (i > 0) {
            //插入文件记录成功后,删除chunk表中的对应记录,释放空间
            BackChunk backChunk = new BackChunk();
            backChunk.setIdentifier(fileInfo.getIdentifier());
            backChunk.setFilename(fileInfo.getFilename());
            backChunkMapper.deleteBackChunkByIdentifier(backChunk);
        }
        return i;
    }

    /**
     * 功能描述:生成块文件所在地址
     */
    private String generatePath(String uploadFolder, BackChunk chunk) {
        StringBuilder sb = new StringBuilder();
        //文件夹地址/md5
        sb.append(uploadFolder).append("/").append(chunk.getIdentifier());
        //判断uploadFolder/identifier 路径是否存在，不存在则创建
        if (!Files.isWritable(Paths.get(sb.toString()))) {
            log.info("path not exist,create path: {}", sb.toString());
            try {
                Files.createDirectories(Paths.get(sb.toString()));
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        //文件夹地址/md5/文件名-1
        return sb.append("/").append(chunk.getFilename()).append("-").append(chunk.getChunkNumber()).toString();
    }

    /**
     * 文件合并
     *
     * @param targetFile 要形成的文件名
     * @param folder     要形成的文件夹地址
     * @param filename   文件的名称
     */
    public static void merge(String targetFile, String folder, String filename) {
        try {
            Files.createFile(Paths.get(targetFile));
            Files.list(Paths.get(folder)).filter(path -> !path.getFileName().toString().equals(filename)).sorted((o1, o2) -> {
                String p1 = o1.getFileName().toString();
                String p2 = o2.getFileName().toString();
                int i1 = p1.lastIndexOf("-");
                int i2 = p2.lastIndexOf("-");
                return Integer.valueOf(p2.substring(i2)).compareTo(Integer.valueOf(p1.substring(i1)));
            }).forEach(path -> {
                try {
                    //以追加的形式写入文件
                    Files.write(Paths.get(targetFile), Files.readAllBytes(path), StandardOpenOption.APPEND);
                    //合并后删除该块
                    Files.delete(path);
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            });
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
