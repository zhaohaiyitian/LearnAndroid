package com.jack.learn.apm.startup.tasks;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;

import androidx.annotation.Nullable;


import com.jack.learn.apm.startup.AndroidStartup;
import com.jack.learn.apm.startup.LogUtils;
import com.jack.learn.apm.startup.Startup;

import java.util.List;

public class Task1 extends AndroidStartup<String> {

    @Nullable
    @Override
    public String create(Context context) {
        String t = Looper.myLooper() == Looper.getMainLooper()
                ? "主线程: " : "子线程: ";
        LogUtils.log(t +" Task1：学习Java基础");
        SystemClock.sleep(1000);
        LogUtils.log(t +" Task1：掌握Java基础");
        return "Task1返回的数据";
    }

    //执行此任务需要依赖哪些任务
    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return super.dependencies();
    }






}
