package com.xiaosi.design.strategy;

/**
 * JDK 中的应用：java.util.Comparator 是典型的策略模式。
 * Spring 中的应用：事务管理（TransactionManager），支持编程式和声明式事务。
 */

public class PromotionContext {

    private PromotionStrategy promotionStrategy;

    public PromotionContext(PromotionStrategy promotionStrategy) {
        this.promotionStrategy = promotionStrategy;
    }

    public void executePromotion() {
        promotionStrategy.applyPromotion();
    }
}
