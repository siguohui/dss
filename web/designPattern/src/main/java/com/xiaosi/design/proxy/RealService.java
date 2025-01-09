package com.xiaosi.design.proxy;

public class RealService implements Service {
    @Override
    public void execute() {
        System.out.println("Executing real service...");
    }
}
