package com.xiaosi.spring.entity;

import org.springframework.stereotype.Component;

@Component
public class Blue extends Tint {
    @Override
    public String toString() {
        return "Blue{" + "label='" + label + '\'' + "}";
    }
}
