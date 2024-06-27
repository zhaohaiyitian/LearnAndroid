package com.jack.learn.apm;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;

import java.util.concurrent.ConcurrentHashMap;


public class ANRWatchDog extends Thread {

    private static final String TAG = "ANRWatchDog";
    private int timeout = 3000;
    private boolean ignoreDebugger = true;

    static ANRWatchDog sWatchdog;

    private Handler mainHandler = new Handler(Looper.getMainLooper());


    private class ANRChecker implements Runnable {

        private boolean mCompleted;
        private long mStartTime;
        private long executeTime = SystemClock.uptimeMillis();

        @Override
        public void run() { // 由主线程进行执行
            synchronized (ANRWatchDog.this) {
                mCompleted = true;
                executeTime = SystemClock.uptimeMillis();
            }
        }

        void schedule() {
            mCompleted = false;
            mStartTime = SystemClock.uptimeMillis();
            mainHandler.postAtFrontOfQueue(this); // 执行Runnable
        }

        boolean isBlocked() {
            return !mCompleted || executeTime - mStartTime >= 5000;
        }
    }

    public interface ANRListener {
        void onAnrHappened(String stackTraceInfo);
    }

    private ANRChecker anrChecker = new ANRChecker();

    private ANRListener anrListener;

    public ANRWatchDog addANRListener(ANRListener listener){
        this.anrListener = listener;
        return this;
    }

    public static ANRWatchDog getInstance(){
        if(sWatchdog == null){
            sWatchdog = new ANRWatchDog();
        }
        return sWatchdog;
    }

    private ANRWatchDog(){
        super("ANR-WatchDog-Thread");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND); // 设置为后台线程
        while(true){ // 死循环
            while (!isInterrupted()) {
                synchronized (this) {
                    anrChecker.schedule();
                    long waitTime = timeout;
                    long start = SystemClock.uptimeMillis();
                    while (waitTime > 0) {//避免提前唤醒，保证5S检测一次
                        try {
                            wait(waitTime);
                        } catch (InterruptedException e) {
                            Log.w(TAG, e.toString());
                        }
                        waitTime = timeout - (SystemClock.uptimeMillis() - start);
                    }
                    if (!anrChecker.isBlocked()) {
                        continue;
                    }
                }
                if (!ignoreDebugger && Debug.isDebuggerConnected()) {
                    continue;
                }
                String stackTraceInfo = getStackTraceInfo();
                if (anrListener != null) {
                    anrListener.onAnrHappened(stackTraceInfo);
                }
            }
            anrListener = null;
        }
    }

    private String getStackTraceInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        for (StackTraceElement stackTraceElement : Looper.getMainLooper().getThread().getStackTrace()) {
            stringBuilder
                    .append(stackTraceElement.toString())
                    .append("\r\n");
        }
        return stringBuilder.toString();
    }
}
