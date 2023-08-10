package com.gb.preferredMallManage.config.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ToKenToAdminUser {

    /**
     * 当前用户在request中的名字
     */
    public String value()  default "adminUser";
}
