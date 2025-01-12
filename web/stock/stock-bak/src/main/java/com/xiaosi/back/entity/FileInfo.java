package com.xiaosi.back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(schema = "sgh",name = "file_info")
@SQLDelete(sql = "update file_info set deleted = 1 where id = ?")
@Where(clause = "deleted = 0")
@EntityListeners(AuditingEntityListener.class)
public class FileInfo extends BaseEntity{

    // 文件名
    private String fileName;

    // 文件类型
    private String path;
    // 文件路径
    private String url;
    // 文件url
    private long size;
    //  文件大小
    @Tolerate
    public FileInfo() {
    }

    @PreRemove
    public void deleteFileInfo() {
        this.setDeleted(1);
    }

}
