package com.xiaosi.lock.repeat.redis.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RequestLock {

    String prefix() default "repeat";

    String delimiter() default ":";

    long expire() default 60;

    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
