package com.xiaosi.lock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Stu {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String stuName;
    private Integer age;
}
