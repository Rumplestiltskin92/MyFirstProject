package com.sz.myapplication;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author：created by renlei on 2021/12/3
 * eMail :renlei@yitong.com.cn
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface ASM_AsmAnno {
}
