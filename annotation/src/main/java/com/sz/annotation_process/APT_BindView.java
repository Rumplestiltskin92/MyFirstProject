package com.sz.annotation_process;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



@Target(ElementType.FIELD)
//生命周期保留时间  java<SOURCE>--->class<CLASS>--->runtime<RUNTIME>
@Retention(RetentionPolicy.SOURCE)
public @interface APT_BindView {
    int value();
}