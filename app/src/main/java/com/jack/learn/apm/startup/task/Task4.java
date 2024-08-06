package com.jack.learn.apm.startup.task;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import com.jack.learn.apm.startup.AndroidStartUp;
import com.jack.learn.apm.startup.StartUp;

import java.util.ArrayList;
import java.util.List;

public class Task4 extends AndroidStartUp<Void> {
    static List<Class<? extends StartUp<?>>> depends;
    static {
        depends = new ArrayList<>();
        depends.add(Task2.class);
    }
    @Override
    public Void create(Context context) {
        Log.d("Jack","学习Http");
        SystemClock.sleep(1000);
        return null;
    }

    //执行此任务需要依赖哪些任务
    @Override
    public List<Class<? extends StartUp<?>>> dependencies() {
        return depends;
    }
}
