package com.jack.learn.apm.startup.task;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import com.jack.learn.apm.startup.AndroidStartUp;
import com.jack.learn.apm.startup.StartUp;

import java.util.List;

public class Task1 extends AndroidStartUp<String> {
    @Override
    public String create(Context context) {
        Log.d("Jack","学习Java基础");
        SystemClock.sleep(1000);
        return "Task返回的数据";
    }

    @Override
    public List<Class<? extends StartUp<?>>> dependencies() {
        return super.dependencies();
    }
}
