package com.jack.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//编译时技术
@Target(ElementType.TYPE)   //声明注解的作用域  放在什么上面
@Retention(RetentionPolicy.CLASS)   //源码期  <  编译期  <  运行期 决定了注解的存在周期
public @interface BindPath {
    //key
    String value();
}
