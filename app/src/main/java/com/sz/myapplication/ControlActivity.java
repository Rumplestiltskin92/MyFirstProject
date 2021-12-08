package com.sz.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.sz.annotation_process.APT_BindView;

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
 * author：created by renlei on 2021/11/2
 * eMail :renlei@yitong.com.cn
 */
public class ControlActivity extends Activity {

    @APT_BindView(R.id.btn)
    Button btnTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control_activity);
        APT_RLButterKnife.bind(this);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnTest.setText("修改后的Hello");
                REFLECT_ReflectTest.newReflectInstance();
                REFLECT_ReflectTest.reflectPrivateConstructor();
                REFLECT_ReflectTest.reflectPrivateMethod();
                REFLECT_ReflectTest.reflectPrivateField();
            }
        });

    }






}
