package com.xiaosi.rd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
}
