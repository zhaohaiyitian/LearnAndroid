package com.jack.learn.juc;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {


    public void newThread() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 4, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(new ThreadGroup(""),r,"thread-1",512);
                return thread;
            }
        },new ThreadPoolExecutor.AbortPolicy());
    }
}
