package com.snaker.framework.config.annotation;


import java.lang.annotation.*;

// 元注解，表明该注解只能用于方法。
@Target({ElementType.METHOD})
// 表明注解将会被载入到虚拟机中，可以由反射代码获取到注解内容
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodLog {

    /**
     * 方法描述
     * @return
     */
    String desc() default "无描述信息";


    /**
     * 接口描述
     * @return
     */
    String master() default "无描述信息";
}
