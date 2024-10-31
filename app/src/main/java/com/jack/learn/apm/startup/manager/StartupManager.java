package com.jack.learn.apm.startup.manager;

import android.content.Context;
import android.os.Looper;

import com.jack.learn.apm.startup.StartUp;
import com.jack.learn.apm.startup.StartupSortStore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class StartupManager {

    private CountDownLatch awaitCountDownLatch;

    private Context context;
    private List<StartUp<?>> startupList;
    private StartupSortStore startupSortStore;

    public StartupManager(Context context, List<StartUp<?>> startupList, CountDownLatch awaitCountDownLatch) {
        this.context = context;
        this.startupList = startupList;
        this.awaitCountDownLatch = awaitCountDownLatch;
    }

    public StartupManager start() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("请在主线程调用！");
        }
//        startupSortStore = TopologySort.sort(startupList);
//        for (StartUp<?> startup : startupSortStore.getResult()) {
//            StartupRunnable startupRunnable = new StartupRunnable(context, startup, this);
//            if (startup.callCreateOnMainThread()) {
//                startupRunnable.run();
//            } else {
//                startup.executor().execute(startupRunnable);
//            }
//        }
        return this;
    }


    public void notifyChildren(StartUp<?> startup) {
        if (!startup.callCreateOnMainThread() &&
                startup.waitOnMainThread()) {
            awaitCountDownLatch.countDown();
        }
        //获得已经完成的当前任务的所有子任务
        if (startupSortStore
                .getStartupChildrenMap().containsKey(startup.getClass())) {
            List<Class<? extends StartUp>> childStartupCls = startupSortStore
                    .getStartupChildrenMap().get(startup.getClass());
            for (Class<? extends StartUp> cls : childStartupCls) {
                //通知子任务 startup父任务已完成
                StartUp<?> childStartup = startupSortStore.getStartupMap().get(cls);
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
        private List<StartUp<?>> startupList = new ArrayList<>();

        public Builder addStartup(StartUp<?> startup) {
            startupList.add(startup);
            return this;
        }

        public Builder addAllStartup(List<StartUp<?>> startups) {
            startupList.addAll(startups);
            return this;
        }


        public StartupManager build(Context context) {
            //记录有多少个在子线程执行，又需要主线程等待的任务
            AtomicInteger needAwaitCount = new AtomicInteger();
            for (StartUp<?> startup : startupList) {
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
