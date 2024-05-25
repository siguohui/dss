package com.xiaosi.wx.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class LocalDateConverter implements Converter<LocalDate> {

    @Override
    public Class<LocalDate> supportJavaTypeKey() {
        return LocalDate.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDate convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                       GlobalConfiguration globalConfiguration) throws Exception {
        String format = getDateTimeFormat(cellData.getStringValue());
        return LocalDate.parse(cellData.getStringValue(), DateTimeFormatter.ofPattern(format));
    }

    @Override
    public WriteCellData<String> convertToExcelData(LocalDate value, ExcelContentProperty contentProperty,
                                                    GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData<>(value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    public String getDateTimeFormat(String dateFormat){
        if(dateFormat.contains("/")){
            return "yyyy/MM/dd";
        }
        return "yyyy-MM-dd";
    }
}
