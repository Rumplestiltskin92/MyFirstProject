package com.sz.myapplication;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * author：created by renlei on 2021/11/2
 * eMail :renlei@yitong.com.cn
 */
public class PROXY_ProxyHandler implements InvocationHandler {
    private PROXY_Animal animal;

    public PROXY_ProxyHandler(PROXY_Animal animal) {
        this.animal = animal;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {
        return method.invoke(animal,objects);
    }
}
