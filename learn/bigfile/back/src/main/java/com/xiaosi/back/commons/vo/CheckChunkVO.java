package com.xiaosi.back.commons.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class CheckChunkVO implements Serializable {
    private boolean skipUpload = false;
    private String url;
    private List<Integer> uploaded = new ArrayList<>();
    private boolean needMerge = true;
    private boolean result = true;
}
