package com.sz.myapplication;

/**
 * 绑定activity使用
 * author：created by renlei on 2021/11/25
 * eMail :renlei@yitong.com.cn
 */
public interface APT_IBinder<T> {
    void bind(T target);
}
