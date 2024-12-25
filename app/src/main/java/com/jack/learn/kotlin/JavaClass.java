package com.jack.learn.kotlin;

import com.jack.learn.ExtUtilsKt;

public class JavaClass {

    public static void main(String[] args) {
        KotlinClass kotlinClass = new KotlinClass();
        Integer length = kotlinClass.nullableStringLength("");
        if (length != null){
            // TODO
        }
        kotlinClass.greet("feornf");
        ExtUtilsKt.addSuffix("","");
    }
}
