package com.xiaosi.back.entity;

import jakarta.persistence.Entity;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class BankAccount extends BaseEntity{
    private String accountHolderName;
    private BigDecimal balance;
}
