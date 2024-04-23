package com.xiaosi.wx.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor(staticName = "build")
public class Context implements Serializable {
    private Integer id;
    private String title;
}
