package com.sz.annotation_compiler;

import com.google.auto.service.AutoService;
import com.sz.annotation_process.APT_BindView;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

/**
 * 注解处理器，用来生成java代码
 * 使用前需要注册
 * *
 */
@AutoService(Processor.class)
public class APT_AnnotationCompiler extends AbstractProcessor {
    //使用前需要初始化3个动作

    //1.当前支持的java源文件版本
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    //2.当前APT能用来处理哪些注解
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(APT_BindView.class.getCanonicalName());
        return types;
    }

    //3.需要一个用来生成文件的对象(可以生成java源文件，class文件)
    Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    /**
     * 注解的处理在这完成
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //类 TypeElement
        //方法  ExecutableElement
        //属性  VariableElement

        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(APT_BindView.class);
        //String 类(如Activity1,Activity2)   List<VariableElement>当前类的属性
        Map<String, List<VariableElement>> map = new HashMap<>();
        //开始分类存放，得到所有不同类的map集合
        for (Element element : elementsAnnotatedWith) {
            VariableElement variableElement = (VariableElement) element;
            //得到类名（如Activity1）
            String className = variableElement.getEnclosingElement()/*获取当前属性的被包裹的类*/.getSimpleName().toString();
            //根据类名获取属性集合
            List<VariableElement> variableElementList = map.get(className);
            //若属性集合为空就创建，并且添加到map中
            if (variableElementList == null) {
                variableElementList = new ArrayList<>();
                map.put(className, variableElementList);
            }
            //属性集合不为空，将属性添加到集合中
            variableElementList.add(variableElement);
        }


        //开始生成文件
        if (map.size() > 0) {
            Writer writer = null;
            Iterator<String> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                String typeElementName = iterator.next();
                List<VariableElement> variableElementList = map.get(typeElementName);
                //获取包名
                TypeElement typeElement /*获取类*/ = (TypeElement) variableElementList.get(0).getEnclosingElement();
                String packageName = processingEnv.getElementUtils().getPackageOf(typeElement).toString();
                try {
                    JavaFileObject javaFileObject = filer.createSourceFile(packageName + "." + typeElementName+"_ViewBind");
                    writer = javaFileObject.openWriter();
                    //package com.sz.javaclass;
                    //import com.sz.javaclass.IBinder;
                    //import android.widget.Button;
                    //import com.sz.myapplication.R;
                    //
                    ///**
                    // * 此类由编译时生成
                    // * author：created by renlei on 2021/11/25
                    // * eMail :renlei@yitong.com.cn
                    // */
                    //public class ControlActivity_ViewBind implements IBinder<com.sz.javaclass.ControlActivity> {
                    //
                    //    @Override
                    //    public void bind(com.sz.javaclass.ControlActivity target) {
                    //        target.btnTest = (Button) target.findViewById(R.id.btn);
                    //    }
                    //}
                    writer.write("package "+packageName+";\n");
                    writer.write("import "+packageName+".APT_IBinder;\n");
                    writer.write("public class "+typeElementName+"_ViewBind implements APT_IBinder<"+packageName+"."+typeElementName+"> {\n");
                    writer.write("@Override\n");
                    writer.write("public void bind("+packageName+"."+typeElementName+" target) {\n");
                    for (VariableElement v :variableElementList) {
                        //获取变量名--btnTest
                        String variableElementName = v.getSimpleName().toString();
                        //获取变量类型--Button
                        TypeMirror typeMirror=v.asType();
                        //获取变量id--R.id.btn
                        int variableElementId = v.getAnnotation(APT_BindView.class).value();
                        writer.write("target."+variableElementName+" = ("+typeMirror+") target.findViewById("+variableElementId+");\n");
                        writer.write("}\n}\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if (writer!=null){
                        try {
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }

        return false;
    }
}