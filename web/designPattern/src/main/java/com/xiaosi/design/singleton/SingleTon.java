package com.xiaosi.design.singleton;

public class SingleTon {

    private static volatile SingleTon singleTon;

    public SingleTon() {
    }

    public static SingleTon getSingleTon(){
        if(singleTon == null){
            synchronized (SingleTon.class){
                if(singleTon == null){
                    singleTon = new SingleTon();
                }
            }
        }
        return singleTon;
    }
}
