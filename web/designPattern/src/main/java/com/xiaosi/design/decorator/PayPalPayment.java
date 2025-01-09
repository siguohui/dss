package com.xiaosi.design.decorator;

public class PayPalPayment implements Payment {

    @Override

    public void makePayment() {

        System.out.println("Processing payment through PayPal.");

    }

}
