package com.xiaosi.wx.easyexcel.converter;

import com.xiaosi.wx.enums.LevelEnum;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LevelEnumConverter extends EnumBaseConverter<LevelEnum>{
    @Override
    public LevelEnum getIResult(String label) {
        return LevelEnum.getIResult1(label);
    }
}
