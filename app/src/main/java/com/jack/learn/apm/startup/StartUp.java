package com.jack.learn.apm.startup;

import android.content.Context;

import java.util.List;

public interface StartUp<T> extends Dispatcher {


    /**
     * 给程序员做初始化工作
     * @param context
     * @return
     */
    T create(Context context);
    /**
     * 本任务依赖那些任务
     */
    List<Class<? extends StartUp<?>>> dependencies();
    /**
     * 获取入度数
     * @return
     */
    int getDependenciesCount();
}
