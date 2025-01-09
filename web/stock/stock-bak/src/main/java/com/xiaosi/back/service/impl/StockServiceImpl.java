package com.xiaosi.back.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaosi.back.entity.Stock;
import com.xiaosi.back.mapper.StockMapper;
import com.xiaosi.back.service.StockService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl extends ServiceImpl<StockMapper,Stock> implements  StockService{

    @Override
    public void saveList(List<Stock> list) {
        super.saveBatch(list);
    }
}
