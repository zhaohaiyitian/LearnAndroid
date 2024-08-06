package com.jack.learn.apm.startup;

import android.os.Process;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

public abstract class AndroidStartUp<T> implements StartUp<T> {

    private CountDownLatch mWaitCountDown = new CountDownLatch(getDependenciesCount());
    @Override
    public List<Class<? extends StartUp<?>>> dependencies() {
        return null;
    }

    @Override
    public int getDependenciesCount() {
        List<Class<? extends StartUp<?>>> dependencies = dependencies();
        return dependencies == null ? 0 : dependencies().size();
    }

    @Override
    public void toWait() {
        try {
            mWaitCountDown.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void toNotify() {
        mWaitCountDown.countDown();
    }

    /**
     * 默认使用io线程池
     * @return
     */
    @Override
    public Executor executor() {
        return null;
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
