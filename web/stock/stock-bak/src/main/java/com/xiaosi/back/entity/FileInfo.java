package com.xiaosi.back.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
@Entity
@Table(schema = "sgh",name = "file_info")
public class FileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String path;
    private String url;
    private long size;

    @Tolerate
    public FileInfo() {
    }
}
