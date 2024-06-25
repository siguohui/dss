package com.xiaosi.doc.lombok;

import lombok.Builder;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder(toBuilder = true)
public abstract class BaseEntity<T> {

    private T value;

    @Builder.Default
    private String name = "zhangsan";

    @Singular("skill")
    private List<String> skillList;
}
