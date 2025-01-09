package com.xiaosi.design.strategy;

public class DiscountStrategy implements PromotionStrategy{

    @Override
    public void applyPromotion() {
        System.out.println("1");
    }
}
