package com.sz.myapplication;



import org.jetbrains.annotations.TestOnly;

import java.lang.reflect.Proxy;

/**
 * author：created by renlei on 2021/11/2
 * eMail :renlei@yitong.com.cn
 */
public class PROXY_ControlActivityTest {

    public void testName() {
        proxyObjectTest();
    }

    public void proxyObjectTest() {
        /*1.动态代理类实现了与目标类一样的接口，并实现了需要目标类对象需要调用的方法
        2.该方法的实现逻辑 = 调用父类 Proxy类的 h.invoke（）
        其中h参数 = 在创建动态代理实例中newProxyInstance(ClassLoader loader, Class<?>[]interfaces,InvocationHandler h)
        传入的第3个参数InvocationHandler对象
        3.在 InvocationHandler.invoke（） 中通过反射机制，从而调用目标类对象的方法 */
        PROXY_ProxyObjectHandler proxyObjectHandler = new PROXY_ProxyObjectHandler();
        PROXY_Animal animal = new PROXY_Animal();
        PROXY_Fly fly = (PROXY_Fly) proxyObjectHandler.proxyObjectHandler(animal);
        fly.fly();
    }

    public void proxyTest() {
        PROXY_Animal animal = new PROXY_Animal();
        ClassLoader classLoader = animal.getClass().getClassLoader();
        Class[] interfaces = animal.getClass().getInterfaces();
        PROXY_ProxyHandler proxyHandler = new PROXY_ProxyHandler(animal);

        /*

        Proxy.newProxyInstance(classLoader, interfaces, proxyHandler);

        classLoader:类加载器，我们手动写的都是java文件，需要编译成class文件，
        这个是遵循JVM规范的二进制文件，然后通过classLoader将class文件加载进内存，
        生成我们需要的class对象，这个class对象通过反射就可以拿到类的所有信息。
        在这边的作用其实就是将Java动态生成的class文件进行加载得到动态代理的class对象，以便后面其他操作.

        interfaces:这个就是接口，可以看出无论代理或者RealSubject都是实现同样的接口，
        Java替我们动态生成的class文件中的方法其实就是接口中的方法。
        这个其实也是Java动态代理的缺点，即使RealSubject中声明的方法，
        但是接口中没有声明该方法，那么在生成的代理中就没有，也就是动态生
        成的代理类中只有接口中的方法.

        proxyHandler:就是InvocationHandler的实现类，集成管理Proxy方法的调
        用映射到RealSubject中，主要就是在invoke中方法实现。在我们这个栗子就是实现
        将AnimalProxy方法调用映射到Animal对应的方法上


        * */
        Object newProxyInstance = Proxy.newProxyInstance(classLoader, interfaces, proxyHandler);
        PROXY_Fly fly = (PROXY_Fly) newProxyInstance;
        fly.fly();
        PROXY_Run run = (PROXY_Run) newProxyInstance;
        run.run();
    }
}