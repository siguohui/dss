package com.xiaosi.design.decorator;

public class PaymentSystem {

    public static void main(String[] args) {

        // Basic Credit Card Payment

        Payment creditCardPayment = new CreditCardPayment();

        System.out.println("Basic Credit Card Payment:");

        creditCardPayment.makePayment();

        System.out.println();

        // Credit Card Payment with Fraud Detection

        Payment fraudProtectedPayment = new FraudDetectionDecorator(creditCardPayment);

        System.out.println("Credit Card Payment with Fraud Detection:");

        fraudProtectedPayment.makePayment();

        System.out.println();

        // PayPal Payment with Fraud Detection and Logging

        Payment payPalPayment = new PayPalPayment();

        Payment payPalWithFeatures = new LoggingDecorator(new FraudDetectionDecorator(payPalPayment));

        System.out.println("PayPal Payment with Fraud Detection and Logging:");

        payPalWithFeatures.makePayment();

    }

}
