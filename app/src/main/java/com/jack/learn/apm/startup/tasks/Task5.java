package com.jack.learn.apm.startup.tasks;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;

import com.jack.learn.apm.startup.AndroidStartup;
import com.jack.learn.apm.startup.LogUtils;
import com.jack.learn.apm.startup.Startup;

import java.util.ArrayList;
import java.util.List;

public class Task5 extends AndroidStartup<Void> {

    static List<Class<? extends Startup<?>>> depends;

    static {
        depends = new ArrayList<>();
        depends.add(Task3.class);
        depends.add(Task4.class);
    }

    @Override
    public Void create(Context context) {
        String t = Looper.myLooper() == Looper.getMainLooper()
                ? "主线程: " : "子线程: ";
        LogUtils.log(t + " Task5：学习OkHttp");
        SystemClock.sleep(1000);
        LogUtils.log(t + " Task5：掌握OkHttp");
        return null;
    }


    //执行此任务需要依赖哪些任务
    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return depends;
    }

}
