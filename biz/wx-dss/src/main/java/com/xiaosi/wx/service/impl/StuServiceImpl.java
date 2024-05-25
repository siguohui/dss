package com.xiaosi.wx.service.impl;

import cn.hutool.core.map.MapBuilder;
import com.alibaba.excel.EasyExcel;
import com.google.common.collect.Sets;
import com.xiaosi.wx.easyexcel.converter.LocalDateConverter;
import com.xiaosi.wx.easyexcel.listener.EasyExcelListener;
import com.xiaosi.wx.entity.Stu;
import com.xiaosi.wx.exception.ImportException;
import com.xiaosi.wx.mapper.StuMapper;
import com.xiaosi.wx.service.StuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sgh
 * @since 2024-05-11 06:07:12
 */
@Slf4j
@Service
public class StuServiceImpl extends ServiceImpl<StuMapper, Stu> implements StuService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upload(MultipartFile file) {
        if (file.isEmpty() || file.getSize() <= 0) {
            throw new ImportException("上传文件大小为空!!");
        }

        String suffix = org.springframework.util.StringUtils.getFilenameExtension(file.getOriginalFilename());
        if (!"xls".equals(suffix) && !"xlsx".equals(suffix)) {
            throw new ImportException("文件格式有误,请检查上传文件格式!!");
        }

        try (InputStream inputStream = file.getInputStream()) {
            List<Stu> dataList = EasyExcel.read(inputStream)
                    .registerReadListener(new EasyExcelListener<>(Sets.newHashSet("名称","等级")))
                    .head(Stu.class)
                    .sheet(0)
                    .headRowNumber(1)
                    .doReadSync();

            if (CollectionUtils.isEmpty(dataList)) {
                throw new ImportException("数据不能为空");
            }


//            List<String> l1 = dataList.stream().map(Stu::getLevel).map(m -> m.msg).filter(("L1")::equals).collect(Collectors.toList());
//
//            if (!"L1".equals(dataList.get(0).getLevel())) {
//                throw new ImportException("结构层级排列顺序不正确, 第一条数据应为L1级");
//            }
//
//            if (l1.size() > 1) {
//                throw new ImportException("结构层级列填写数据有误，不能存在多个L1");
//            }
            saveBatch(dataList);
        } catch (Exception e) {
            log.error("导入文件失败：{}", e.getMessage(), e);
            throw new ImportException(e.getMessage());
        }
    }
}
