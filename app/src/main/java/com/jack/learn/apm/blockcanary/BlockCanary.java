package com.jack.learn.apm.blockcanary;

import android.os.Looper;


/**
 * 适用于耗时代码检测
 */
public class BlockCanary {
    public static void install() {
        LogMonitor logMonitor = new LogMonitor();
        Looper.getMainLooper().setMessageLogging(logMonitor);
    }
}
