package com.xiaosi.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaosi.back.entity.Stock;

import java.util.List;

public interface StockService extends IService<Stock> {
    void saveList(List<Stock> list);
}
