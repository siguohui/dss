package com.xiaosi.back.utils;

import com.xiaosi.back.entity.FileInfo;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class FileUtils {

    private static final String uri = "http://localhost:5000/image/";

    public static String getAbsolutePath(String type) {
        String resource = FileUtils.class.getName().replace('.', '/') + ".class";
        ClassLoader classLoader = FileUtils.class.getClassLoader();
        String pathAll = Objects.requireNonNull(classLoader.getResource(resource)).getPath();
        String path = pathAll.split("target")[0];
        return path + "src/main/resources/static/" + type;
    }

    public static File getFile(String path){
        File pfile = new File(path);
        if (!pfile.exists()) {
            pfile.mkdirs();
        }
        return pfile;
    }

    public static File getImageFileParent(){
        String absolutePath = getAbsolutePath("image");
        return getFile(absolutePath);
    }

    public static String getTempFile(String fileName){
        String ext = StringUtils.getFilenameExtension(fileName);
        String temp = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        return temp + ext;
    }

    public static File getNewFile(String fileName){
        String tempFile = FileUtils.getTempFile(fileName);
        File pfile = FileUtils.getImageFileParent();
        return new File(pfile, tempFile);
    }

    public static FileInfo upload(MultipartFile file) throws IOException {
        long size = file.getSize();
        String fileName = file.getOriginalFilename();
        File newFile = FileUtils.getNewFile(fileName);
        String path = newFile.getPath();
        String url = uri + newFile.getName();
        file.transferTo(newFile);
        return FileInfo.builder()
                .fileName(fileName)
                .path(path)
                .url(url)
                .size(size)
                .build();
    }
}
