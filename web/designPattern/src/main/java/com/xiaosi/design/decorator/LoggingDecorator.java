package com.xiaosi.design.decorator;

public class LoggingDecorator extends PaymentDecorator {

    public LoggingDecorator(Payment decoratedPayment) {

        super(decoratedPayment);

    }


    @Override

    public void makePayment() {

        logPayment();

        super.makePayment();

    }


    private void logPayment() {

        System.out.println("Logging payment details.");

    }

}
