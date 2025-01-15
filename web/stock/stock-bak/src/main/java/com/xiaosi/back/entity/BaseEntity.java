package com.xiaosi.back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.xiaosi.back.common.CommonConstant;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SoftDelete;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data //提供类所有属性的 getting 和 setting 方法，此外还提供了equals、canEqual、hashCode、toString 方法
@MappedSuperclass  //自己本身不会映射到数据库表，但是它的属性会映射到子类的数据库字段中
@EntityListeners(AuditingEntityListener.class)
//@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedBy
    @Column(name = "create_by", nullable = false, updatable = false)
    @JsonIgnore
    private Long createBy; //创建人

    @CreatedDate
    @Column(name = "create_time", nullable = false, updatable = false)
    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;  //创建时间

    @LastModifiedBy
    @Column(name = "update_by", nullable = false)
    @JsonIgnore
    private Long updateBy;  //更新人

    @LastModifiedDate
    @Column(name = "update_time", nullable = false)
    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;  //更新时间

    /**
     * 逻辑删除标志位，0 未删除，1 已删除
     */
    @JsonIgnore
    @Column(name = "deleted")  //逻辑删除标志位，0 未删除，1 已删除
//    @SoftDelete
    private Integer deleted = CommonConstant.STATUS_NORMAL;

    // 版本号（用于乐观锁， 默认为 1）
    @Basic
    @Column(name = "version")
    protected Integer version;
}
