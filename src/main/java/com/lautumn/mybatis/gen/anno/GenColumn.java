package com.lautumn.mybatis.gen.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Lautumn
 * @Describe:
 * @Date: Create in 16:14 2019/4/25
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GenColumn {

    String columnDefinition() default "";
    String name() default "";
}
