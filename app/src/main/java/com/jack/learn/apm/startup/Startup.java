package com.jack.learn.apm.startup;

import android.content.Context;

import java.util.List;

public interface Startup<T> extends Dispatcher{

    /**
     * 给程序员做初始化工作
     */
    T create(Context context);

    /**
     * 本任务依赖哪些任务
     *
     */
    List<Class<? extends Startup<?>>> dependencies();

    /**
     * 入度数
     */
    int getDependenciesCount();
}
