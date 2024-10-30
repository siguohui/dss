package com.xiaosi.back.entity;

import lombok.Data;

@Data
public class BackFileList {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 唯一标识,MD5
     */
    private String identifier;

    /**
     * 链接
     */
    private String url;

    /**
     * 本地地址
     */
    private String location;

    /**
     * 文件总大小
     */
    private Long totalSize;
}
