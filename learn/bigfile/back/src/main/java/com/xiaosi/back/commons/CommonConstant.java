package com.xiaosi.back.commons;

public interface CommonConstant {
    /**
     * 更新或新增是否成功 0为失败 1为成功
     * 当要增加的信息已存在时,返回为-1
     */
    Integer UPDATE_FAIL = 0;
    Integer UPDATE_EXISTS = -1;
}
