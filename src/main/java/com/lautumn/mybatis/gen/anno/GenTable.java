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
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface GenTable {

    /**
     * 表名称
     * @return
     */
    String name() default "";

    /**
     * 引擎
     * @return
     */
    String engine() default "InnoDB";

    /**
     * 是否创建表
     * @return
     */
    boolean create() default true;

    String comment() default "";

}
