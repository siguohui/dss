package com.xiaosi.doc.lombok;

import lombok.Builder;

public class Mytest {

    public static void main(String[] args) {
//        Foo<Long> f = Foo.builder().t(1L).build();

//        Collect build = Collect.builder().value(2L).build();
//        System.out.println(build);
    }
}

@Builder
final class Foo<T> {
    private T t;
}

// error here!
