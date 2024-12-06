package com.jack.learn.apm.startup;

import android.os.Process;

import com.jack.learn.apm.startup.manage.ExecutorManager;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

public abstract class AndroidStartup<T> implements Startup<T> {

    private CountDownLatch mWaitCountDown = new CountDownLatch(getDependenciesCount());

    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return null;
    }

    @Override
    public int getDependenciesCount() {
        List<Class<? extends Startup<?>>> dependencies = dependencies();
        return dependencies == null ? 0 : dependencies.size();
    }

    @Override
    public void toWait() {
        try {
            mWaitCountDown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toNotify() {
        mWaitCountDown.countDown();
    }

    /**
     * 默认使用io线程沲
     */
    @Override
    public Executor executor() {
        return ExecutorManager.ioExecutor;
    }

    /**
     * 默认最高优先级
     */
    @Override
    public int getThreadPriority() {
        return Process.THREAD_PRIORITY_DEFAULT;
    }

    /**
     * 默认在子线程执行
     */
    @Override
    public boolean callCreateOnMainThread() {
        return false;
    }

    /**
     * 默认不需要主线程等待自己执行完成
     */
    @Override
    public boolean waitOnMainThread() {
        return false;
    }
}
