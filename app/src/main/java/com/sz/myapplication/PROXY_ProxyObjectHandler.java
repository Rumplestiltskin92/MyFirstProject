package com.sz.myapplication;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * author：created by renlei on 2021/11/2
 * eMail :renlei@yitong.com.cn
 */
public class PROXY_ProxyObjectHandler implements InvocationHandler {
    private Object realObj;

    public Object proxyObjectHandler(Object realObj) {
        this.realObj=realObj;
        return Proxy.newProxyInstance(realObj.getClass().getClassLoader(),realObj.getClass().getInterfaces()
        ,this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {
        return method.invoke(realObj,objects);
    }
}
