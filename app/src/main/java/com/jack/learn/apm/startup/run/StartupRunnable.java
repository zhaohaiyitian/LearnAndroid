package com.jack.learn.apm.startup.run;

import android.content.Context;
import android.os.Process;

import com.jack.learn.apm.startup.Result;
import com.jack.learn.apm.startup.Startup;
import com.jack.learn.apm.startup.manage.StartupCacheManager;
import com.jack.learn.apm.startup.manage.StartupManager;

public class StartupRunnable implements Runnable {
    private StartupManager startupManager;
    private Startup<?> startup;
    private Context context;

    public StartupRunnable(Context context, Startup<?> startup,
                           StartupManager startupManager) {
        this.context = context;
        this.startup = startup;
        this.startupManager = startupManager;
    }

    @Override
    public void run() {
        Process.setThreadPriority(startup.getThreadPriority());
        startup.toWait();
        Object result = startup.create(context);
        StartupCacheManager.getInstance().saveInitializedComponent(startup.getClass(),
                new Result(result));

        //当前任务执行完成后，调用后序任务的toNotify()
        startupManager.notifyChildren(startup);

    }
}
