package com.jack.learn.apm.startup.manage;

import android.content.Context;
import android.os.Looper;

import com.jack.learn.apm.startup.Startup;
import com.jack.learn.apm.startup.StartupSortStore;
import com.jack.learn.apm.startup.run.StartupRunnable;
import com.jack.learn.apm.startup.sort.TopologySort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class StartupManager {

    private CountDownLatch awaitCountDownLatch;

    private Context context;
    private List<Startup<?>> startupList;
    private StartupSortStore startupSortStore;

    public StartupManager(Context context, List<Startup<?>> startupList, CountDownLatch awaitCountDownLatch) {
        this.context = context;
        this.startupList = startupList;
        this.awaitCountDownLatch = awaitCountDownLatch;
    }

    public StartupManager start() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("请在主线程调用！");
        }
        startupSortStore = TopologySort.sort(startupList);
        for (Startup<?> startup : startupSortStore.getResult()) {
            StartupRunnable startupRunnable = new StartupRunnable(context, startup, this);
            if (startup.callCreateOnMainThread()) {
                startupRunnable.run();
            } else {
                startup.executor().execute(startupRunnable);
            }
        }
        return this;
    }


    public void notifyChildren(Startup<?> startup) {
        if (!startup.callCreateOnMainThread() &&
                startup.waitOnMainThread()) {
            awaitCountDownLatch.countDown();
        }
        //获得已经完成的当前任务的所有子任务
        if (startupSortStore
                .getStartupChildrenMap().containsKey(startup.getClass())) {
            List<Class<? extends Startup>> childStartupCls = startupSortStore
                    .getStartupChildrenMap().get(startup.getClass());
            for (Class<? extends Startup> cls : childStartupCls) {
                //通知子任务 startup父任务已完成
                Startup<?> childStartup = startupSortStore.getStartupMap().get(cls);
                childStartup.toNotify();
            }
        }
    }

    public void await() {
        try {
            awaitCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //基于分阶段处理任务，不能用单例
    public static class Builder {
        private List<Startup<?>> startupList = new ArrayList<>();

        public Builder addStartup(Startup<?> startup) {
            startupList.add(startup);
            return this;
        }

        public Builder addAllStartup(List<Startup<?>> startups) {
            startupList.addAll(startups);
            return this;
        }


        public StartupManager build(Context context) {
            //记录有多少个在子线程执行，又需要主线程等待的任务
            AtomicInteger needAwaitCount = new AtomicInteger();
            for (Startup<?> startup : startupList) {
                //记录有多少个在子线程执行，又需要主线程等待的任务
                if (!startup.callCreateOnMainThread() &&
                        startup.waitOnMainThread()) {
                    needAwaitCount.incrementAndGet();//i++
                }
            }
            //根据任务数新建一个闭锁
            CountDownLatch awaitCountDownLatch = new CountDownLatch(needAwaitCount.get());
            return new StartupManager(context, startupList, awaitCountDownLatch);
        }
    }

}
