package com.jack.learn.crash;

import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    /**
     * CrashHandler实例
     */
    private static CrashHandler INSTANCE;

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {

    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            synchronized (CrashHandler.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CrashHandler();
                }
            }
        }
        ArrayList list = new ArrayList();
        Collections.synchronizedList(list);
        return INSTANCE;
    }

    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        while(true) {
            try {
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                Looper.loop();
            } catch (Exception exception) {

            }
        }
    }
}
