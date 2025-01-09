package com.xiaosi.back.utils;

import com.xiaosi.back.config.SysConfig;
import com.xiaosi.back.entity.FileInfo;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class FileUtils {

    public static String getPrefixUri(String type) {
        String prefix = ApplicationContextUtils.getBean(SysConfig.class).getPrefix();
        return prefix + type + "/";
    }

    public static String getAbsolutePath(String type) {
        String resource = FileUtils.class.getName().replace('.', '/') + ".class";
        ClassLoader classLoader = FileUtils.class.getClassLoader();
        String pathAll = Objects.requireNonNull(classLoader.getResource(resource)).getPath();
        String path = pathAll.split("target")[0];
//        return path + "src/main/resources/static/" + type + "/";
        return path + "target/classes/static/" + type + "/";
    }

    public static File getFile(String path){
        File pfile = new File(path);
        if (!pfile.exists()) {
            pfile.mkdirs();
        }
        return pfile;
    }

    public static File getImageFileParent(String type){
        String absolutePath = getAbsolutePath(type);
        return getFile(absolutePath);
    }

    public static String getTempFile(String fileName){
        String ext = StringUtils.getFilenameExtension(fileName);
        String temp = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        return temp +"." + ext;
    }

    public static File getNewFile(String fileName, String type){
        String tempFile = FileUtils.getTempFile(fileName);
        File pfile = FileUtils.getImageFileParent(type);
        return new File(pfile, tempFile);
    }

    @SneakyThrows
    public static FileInfo upload(MultipartFile file, String type) {
        long size = file.getSize();
        String fileName = file.getOriginalFilename();
        File newFile = FileUtils.getNewFile(fileName, type);
        String path = newFile.getPath();
        String url = getPrefixUri(type) + newFile.getName();
        file.transferTo(newFile);
        return FileInfo.builder()
                .fileName(fileName)
                .path(path)
                .url(url)
                .size(size)
                .build();
    }

    public static FileInfo uploadImage(MultipartFile file){
        String type = "image";
        return upload(file, type);
    }
}
