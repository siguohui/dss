package com.xiaosi.easyexcel.service;

import com.alibaba.excel.EasyExcel;
import com.xiaosi.easyexcel.converter.LocalDateConverter;
import com.xiaosi.easyexcel.entity.Stu;
import com.xiaosi.easyexcel.exception.ImportException;
import com.xiaosi.easyexcel.listener.ConfigListener;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class StuService {
    public void saveConfig(List<Stu> list, Map<String, String> errMap) {
    }

    @Transactional(rollbackFor = Exception.class)
    public void upload(MultipartFile file) {
        if (file.isEmpty() || file.getSize() <= 0) {
            throw new ImportException("上传文件大小为空!!");
        }

        String suffix = StringUtils.getFilenameExtension(file.getOriginalFilename());
        if (!"xls".equals(suffix) && !"xlsx".equals(suffix)) {
            throw new ImportException("文件格式有误,请检查上传文件格式!!");
        }

        /*InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream, 0);
        if (reader.getRowCount() <= 1) {
            throw new RuntimeException("导入的为空模板,请检查后重新导入!!");
        }*/

        List<Stu> dataList;
        try(InputStream inputStream = file.getInputStream()) {
            dataList = EasyExcel.read(inputStream)
                    .registerConverter(new LocalDateConverter())
                    .registerReadListener(new ConfigListener<>(this))
                    .head(Stu.class)
                    .sheet()
                    .headRowNumber(1)
                    .doReadSync();
        } catch (Exception e) {
            log.error("导入文件失败：{}", e.getMessage(), e);
            throw new ImportException("导入文件失败", e.getMessage());
        }

        System.out.println(dataList);
    }

    /*public static void exportError(List<ExcelErrorMessage> errorList) {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("错误信息导出", "UTF-8")
                    .replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), ExcelErrorMessage.class)
                    .sheet("错误内容").doWrite(errorList);
        } catch (IOException e) {
            log.info("下载excel错误信息异常:{}", e.getMessage());
        }
    }*/
}
