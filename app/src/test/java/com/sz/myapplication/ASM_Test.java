package com.sz.myapplication;

import org.junit.Test;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * author：created by renlei on 2021/12/2
 * eMail :renlei@yitong.com.cn
 */
public class ASM_Test {

    /**
     * 通过asm修改class
     *
     * @author renlei
     * @data 2021/12/3 10:10
     */
    @Test
    public void testAsm() {
        try {
            FileInputStream fis = new FileInputStream(new File("src/test/java/com/sz/myapplication/ASM_AsmChange.class"));
            //class的分析器
            ClassReader classReader = new ClassReader(fis);
            //自动计算栈帧和局部变量表的大小
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            //执行分析
            classReader.accept(new MyClassVisitor(Opcodes.ASM7, classWriter), ClassReader.EXPAND_FRAMES);
            //执行了插桩的字节码数据
            byte[] bytes = classWriter.toByteArray();
            FileOutputStream fileOutputStream = new FileOutputStream("src/test/java/com/sz/myapplication/ASM_AsmChange.class");
            fileOutputStream.write(bytes);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class MyClassVisitor extends ClassVisitor {

        public MyClassVisitor(int api, ClassVisitor classVisitor) {
            super(api, classVisitor);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
            return new MyMethodVisitor(api, methodVisitor, access, name, descriptor);
        }

    }

    static class MyMethodVisitor extends AdviceAdapter {

        /**
         * Constructs a new {@link AdviceAdapter}.
         *
         * @param api           the ASM API version implemented by this visitor. Must be one of {@link
         *                      Opcodes#ASM4}, {@link Opcodes#ASM5}, {@link Opcodes#ASM6} or {@link Opcodes#ASM7}.
         * @param methodVisitor the method visitor to which this adapter delegates calls.
         * @param access        the method's access flags (see {@link Opcodes}).
         * @param name          the method's name.
         * @param descriptor    the method's descriptor (see {@link Type Type}).
         */
        protected MyMethodVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
            super(api, methodVisitor, access, name, descriptor);
        }

        @Override
        public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
            System.out.println(descriptor);
            return super.visitAnnotation(descriptor, visible);
        }

        int s;

        @Override
        protected void onMethodEnter() {
            super.onMethodEnter();

            //插入 System.currentTimeMillis();
            invokeStatic(Type.getType("Ljava/lang/System;"), new Method("currentTimeMillis", "()J"));
            //创建一个long类型索引
            s = newLocal(Type.LONG_TYPE);
            //用一个本地变量接受上一步执行结果
            // long l =System.currentTimeMillis();
            storeLocal(s);
        }

        @Override
        protected void onMethodExit(int opcode) {
            super.onMethodExit(opcode);
            //插入 System.currentTimeMillis();
            invokeStatic(Type.getType("Ljava/lang/System;"), new Method("currentTimeMillis", "()J"));
            //创建一个long类型索引
            int a = newLocal(Type.LONG_TYPE);
            //用一个本地变量接受上一步执行结果
            // long l =System.currentTimeMillis();
            storeLocal(a);

            getStatic(Type.getType("Ljava/lang/System;"), "out", Type.getType("Ljava/io/PrintStream;"));

            newInstance(Type.getType("Ljava/lang/StringBuilder;"));
            dup();
            invokeConstructor(Type.getType("Ljava/lang/StringBuilder;"), new Method("<init>", "()V"));
            visitLdcInsn("\u65b9\u6cd5\u8017\u65f6:--");
            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), new Method("append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;"));
            loadLocal(a);
            loadLocal(s);
            math(SUB, Type.LONG_TYPE);
            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), new Method("append", "(J)Ljava/lang/StringBuilder;"));
            visitLdcInsn("\u6beb\u79d2");
            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), new Method("append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;"));
            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), new Method("toString", "()Ljava/lang/String"));
            invokeVirtual(Type.getType("Ljava/io/PrintStream.print;"), new Method("append", "(Ljava/lang/String;)V"));
        }

    }
}
