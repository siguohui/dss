package com.xiaosi.wx.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.google.common.collect.Lists;
import com.xiaosi.wx.utils.tree.ExcelCountVo;
import com.xiaosi.wx.utils.tree.ModuleType;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MyFile {

    @Test
    public void count() throws FileNotFoundException {
       export();
    }

    public void export(){
        ExcelWriter excelWriter = EasyExcel.write("D:\\excel\\data.xlsx").build();
        int i =0;
        for (ModuleType value : ModuleType.values()) {
            List<ExcelCountVo> List = getList(value.getPath(), value.getModule());
            WriteSheet sheet = EasyExcel.writerSheet(i, value.getModule()).head(ExcelCountVo.class)
                    .build();
            excelWriter.write(List, sheet);
            i++;
        }
        excelWriter.finish();
    }

    public List<ExcelCountVo> getList(String path, String moduel){
        List<File> fileList = getFileList(path);
        List<ExcelCountVo> excelList = Lists.newArrayList();
        int sum = 0;
        excelList.add(ExcelCountVo.build(moduel,sum));
        for (File file : fileList) {
            int count = getCount(file);
            sum=sum+count;
            excelList.add(ExcelCountVo.build(file.getName(), count));
        }
        ExcelCountVo excelCountVo = excelList.get(0);
        excelCountVo.setCount(sum);
        return excelList;
    }

    public List<File> getFileList(String path) {
        List<File> fList = Lists.newArrayList();
        File dir = new File(path);
        File[] files = dir.listFiles();
        for (File file : files) {
            if(file.isFile()){
                fList.add(file);
            }else {
                File[] fileList = file.listFiles();
                for (File f : fileList) {
                    fList.add(f);
                }
            }
        }
        return fList;
    }

    public Integer getCount(File file){
        int res = 0;
        ArrayList<String> readUtf8Lines = FileUtil.readLines(file, CharsetUtil.UTF_8, new ArrayList<>());
        for(String readUtf8Line :readUtf8Lines){
            if(readUtf8Line.contains("<select id")){
                res++;
            }
        }
        return res;
    }
}
