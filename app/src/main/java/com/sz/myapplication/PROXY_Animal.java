package com.sz.myapplication;

/**
 * author：created by renlei on 2021/11/2
 * eMail :renlei@yitong.com.cn
 */
public class PROXY_Animal implements PROXY_Fly, PROXY_Run {

    @Override
    public void fly() {
        System.out.println("测试fly");
    }

    @Override
    public void run() {
       System.out.println("测试run");
    }
}
