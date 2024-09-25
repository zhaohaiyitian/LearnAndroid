package com.jack.annotation_compiler;

import com.google.auto.service.AutoService;
import com.jack.annotation.BindPath;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import sun.jvm.hotspot.oops.ExceptionTableElement;

@AutoService(Processor.class) //让javac知道 标识是注解处理器
public class AnnotationCompiler extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        Filer filer = processingEnvironment.getFiler();
        Messager messager = processingEnvironment.getMessager();
        System.out.println("----moduleName--------");
    }
    //jdk版本
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(BindPath.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        // java结构化
        // TypeElement 类
        // ExecutableElement 方法
        // VariableElement 成员变量
        // 拿到应用中被BindPath标记的节点
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindPath.class);

        return false;
    }
}