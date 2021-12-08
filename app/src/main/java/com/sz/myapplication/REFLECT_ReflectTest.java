package com.sz.myapplication;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * author：created by renlei on 2021/12/7
 * eMail :renlei@yitong.com.cn
 *
 * //https://www.jianshu.com/p/9be58ee20dee
 */
public class REFLECT_ReflectTest {
    public static String TAG = "REFLECT_ReflectTest";

    /**
     * 创建反射对象
     *
     * @author renlei
     * @data 2021/12/7 17:24
     */
    public static void newReflectInstance() {
        //获得类相关的方法
        //asSubclass(Class<U> clazz)	把传递的类的对象转换成代表其子类的对象
        //cast	                        把对象转换成代表类或是接口的对象
        //getClassLoader()             	获得类的加载器
        //getClasses()	                返回一个数组，数组中包含该类中所有公共类和接口类的对象
        //getDeclaredClasses()      	返回一个数组，数组中包含该类中所有类和接口类的对象
        //forName(String className) 	根据类名返回类的对象
        //getName()                 	获得类的完整路径名字
        //newInstance()             	创建类的实例
        //getPackage()              	获得类的包
        //getSimpleName()            	获得类的名字
        //getSuperclass()           	获得当前类继承的父类的名字
        //getInterfaces()           	获得当前类实现的类或是接口
        try {
            Class cls = Class.forName("com.sz.myapplication.REFLECT_ReflectClass");
            Object o = cls.newInstance();
            REFLECT_ReflectClass reflectClass = (REFLECT_ReflectClass) o;
            reflectClass.setTestId("test_reflect_id");
            Log.d(TAG, reflectClass.getTestId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 访问私有构造器
     *
     * @author renlei
     * @data 2021/12/7 17:42
     */
    public static void reflectPrivateConstructor() {
        try {
            Class cls = Class.forName("com.sz.myapplication.REFLECT_ReflectClass");
            Constructor<?> constructor = cls.getDeclaredConstructor(String.class,String.class,int.class);
            //JDK API中的解释
            //引用
            //AccessibleObject 类是 Field、Method 和 Constructor 对象的基类。
            // 它提供了将反射的对象标记为在使用时取消默认 Java 语言访问控制检查的能力。
            // 对于公共成员、默认（打包）访问成员、受保护成员和私有成员，
            // 在分别使用 Field、Method 或 Constructor 对象来设置或获得字段、调用方法，
            // 或者创建和初始化类的新实例的时候，会执行访问检查。
            //在反射对象中设置 accessible 标志允许具有足够特权的复杂应用程序
            // 比如 Java Object Serialization 或其他持久性机制）以某种通常禁止使用的方式来操作对象。
            //public void setAccessible(boolean flag) throws SecurityException {
            //       }
            //将此对象的 accessible 标志设置为指示的布尔值。值为 true
            // 则指示反射的对象在使用时应该取消 Java 语言访问检查。值为 false 则指示反射的对象应该实施 Java 语言访问检查。
            //实际上setAccessible是启用和禁用访问安全检查的开关,并不是为true就能访问为false就不能访问
            constructor.setAccessible(true);
            REFLECT_ReflectClass reflectClass = (REFLECT_ReflectClass) constructor.newInstance("","",23);
            reflectClass.setTestId("test_reflect_id");
            Log.d(TAG, reflectClass.getTestId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 反射私有属性
     *
     * @author renlei
     * @data 2021/12/7 17:48
     */
    public static void reflectPrivateField() {
        try {
            Class cls = Class.forName("com.sz.myapplication.REFLECT_ReflectClass");
            Object o = cls.newInstance();
            Field testSizeField = cls.getDeclaredField("testSize");
            testSizeField.setAccessible(true);
            int testSize = (int) testSizeField.get(o);
            Log.d(TAG, "testSize" + testSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 反射私有方法
     *
     * @author renlei
     * @data 2021/12/8 14:32
     */
    public static void reflectPrivateMethod() {
        try {
            Class cls = Class.forName("com.sz.myapplication.REFLECT_ReflectClass");
            Object o = cls.newInstance();
            Method tst = cls.getDeclaredMethod("tst", int.class);
            tst.setAccessible(true);
            String tstStr = (String) tst.invoke(o, 123);
            Log.d(TAG, tstStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
