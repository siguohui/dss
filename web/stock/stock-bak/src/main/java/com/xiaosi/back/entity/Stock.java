package com.xiaosi.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.Tolerate;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 股票实体类
 */
@Data
@Entity
@Builder
@Accessors(chain = true)
@Table(schema = "sgh",name = "stock")
public class Stock implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 名字
     */
    private String name;

    /**
     * 代码
     */
    private String code;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 涨跌幅
     */
    private String riseFallRange;

    /**
     * 涨跌额
     */
    private String riseFallAmount;

    /**
     * 成交量
     */
    private String turnOver;

    /**
     * 成交额
     */
    private String turnVolume;

    @Tolerate
    public Stock() {
    }
}
