package com.jack.learn.apm.startup.tasks;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;

import androidx.annotation.Nullable;

import com.jack.learn.apm.startup.AndroidStartup;
import com.jack.learn.apm.startup.LogUtils;
import com.jack.learn.apm.startup.Startup;

import java.util.ArrayList;
import java.util.List;

public class Task2 extends AndroidStartup<Void> {
    static List<Class<? extends Startup<?>>> depends;

    static {
        depends = new ArrayList<>();
        depends.add(Task1.class);
    }

    @Nullable
    @Override
    public Void create(Context context) {
        String t = Looper.myLooper() == Looper.getMainLooper()
                ? "主线程: " : "子线程: ";
        LogUtils.log(t +" Task2：学习Socket");
        SystemClock.sleep(1000);
        LogUtils.log(t +" Task2：掌握Socket");
        return null;
    }
    /**
     * 默认不需要主线程等待自己执行完成
     */
    @Override
    public boolean waitOnMainThread() {
        return true;
    }

    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return depends;
    }

}
