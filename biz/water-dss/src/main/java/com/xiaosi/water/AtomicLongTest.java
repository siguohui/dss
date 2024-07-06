package com.xiaosi.water;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class AtomicLongTest {

    public static void main(String[] args) {
        AtomicLong atomicLong = new AtomicLong(1);

        System.out.println(atomicLong.getAndIncrement());
        System.out.println(atomicLong.get());
        System.out.println(atomicLong.incrementAndGet());

        LongAdder longAdder = new LongAdder();
        System.out.println(longAdder.longValue());
        longAdder.increment();
        System.out.println(longAdder.longValue());
        longAdder.increment();
        System.out.println(longAdder.longValue());
    }
}
