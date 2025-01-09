package com.xiaosi.design.decorator;

public class CreditCardPayment implements Payment {

    @Override

    public void makePayment() {

        System.out.println("Processing payment through Credit Card.");

    }

}
