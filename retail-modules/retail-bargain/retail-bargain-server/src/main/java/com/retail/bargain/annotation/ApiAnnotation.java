package com.retail.bargain.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface ApiAnnotation {
    String  value() default "";
}
