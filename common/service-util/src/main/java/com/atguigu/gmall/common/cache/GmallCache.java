package com.atguigu.gmall.common.cache;


import java.lang.annotation.*;

//没事儿，这个代码需要重新敲，cache下的类
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GmallCache {
    String prefix() default "cache";
}
