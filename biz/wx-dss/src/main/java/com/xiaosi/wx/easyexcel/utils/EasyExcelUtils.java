package com.xiaosi.wx.easyexcel.utils;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EasyExcelUtils {
    /**
     * 导出excel
     *
     * @param response
     * @param fileName 文件名
     * @param data     List<数据集合>
     * @throws IOException
     */
    public static <T> void downloadExcel(HttpServletResponse response, String fileName, List<T> data, Class<?> clazz) throws IOException {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), clazz).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).autoCloseStream(Boolean.FALSE).sheet("sheet1")
                    .doWrite(data);
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = MapUtils.newHashMap();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }
    }

    /**
     * 读取excel 文件
     *
     * @param file     文件
     * @param cl       类对象
     * @param listener 参数校验监听器
     */
    public static <T> Map importExcel(MultipartFile file, Class<T> cl,BaseListener<T> listener) {
        try {
            EasyExcel.read(file.getInputStream(), cl, listener).sheet().doRead();
            return listener.getErrorMessageMap();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * excleDto 转为对应model实体类
     *
     * @param data
     * @param t
     * @param <T>
     * @return
     */
    public static <T> List<T> dto2model(Collection<?> data, T t) {
        return data.stream().map(e -> {
            T t1 = null;
            try {
                t1 = (T) t.getClass().newInstance();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            BeanUtils.copyProperties(e, t1);
            return t1;
        }).collect(Collectors.toList());
    }
}
