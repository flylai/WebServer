package com.lkm.webserver.servlet;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface UrlMatch {
    String urlMatch() default "";
}
