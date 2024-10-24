package org.xiaosi.stomp.utils;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileDfsUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileDfsUtil.class);

    @Resource
    private FastFileStorageClient storageClient;

    /**
     * 上传文件指定分组名称
     */
    public String uploadAndGroupName(MultipartFile multipartFile, String groupName) throws Exception {
        //如果分布为空的话,进行默认上传
        if (groupName == null) {
            return upload(multipartFile);
        }
        //自定义分组名称
        try {
            String fileExName = getFileExName(multipartFile);
            StorePath storePath = storageClient.uploadFile(groupName, multipartFile.getInputStream(),
                    multipartFile.getSize(), fileExName);
            return storePath.getFullPath();
        } catch (Exception e) {
            throw new RuntimeException("上传失败");
        }
    }

    /**
     * 上传文件
     */
    public String upload(MultipartFile multipartFile) {
        try {
            String fileExName = getFileExName(multipartFile);
            StorePath storePath = this.storageClient.uploadFile(
                    multipartFile.getInputStream(),
                    multipartFile.getSize(), fileExName, null);
            return storePath.getFullPath();
        } catch (Exception e) {
            throw new RuntimeException("上传失败：" + e.getMessage());
        }
    }

    /**
     * 上传缩略图
     */
    public String uploadThumbImage(MultipartFile multipartFile) {

        try {
            String fileExName = getFileExName(multipartFile);
            fileExName = fileExName.toLowerCase();
            if (!"jpg".equals(fileExName) && !"png".equals(fileExName)) {
                return "图片格式错误";
            }
            StorePath storePath = this.storageClient.uploadImageAndCrtThumbImage(
                    multipartFile.getInputStream(),
                    multipartFile.getSize(), fileExName, null);
            return storePath.getFullPath();
        } catch (Exception e) {
            throw new RuntimeException("上传失败");
        }
    }
    /**
     * 删除文件
     */
    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            LOGGER.info("fileUrl == >>文件路径为空...");
            return;
        }
        try {
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            throw new RuntimeException("删除失败失败");
        }
    }

    private String getFileExName(MultipartFile multipartFile) {
        return StringUtils.substringAfter(multipartFile.getOriginalFilename(), ".");
    }
}
