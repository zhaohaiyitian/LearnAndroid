package com.jack.learn.apm;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

public class Utils {


    /**
     * 当前面捕获到SIGQUIT 信号后 通过jni调用java层的方法，通知java层对ANR进行二次确认
     * @param context
     */
    void onANRDumpTrace(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.ProcessErrorStateInfo> errorList = am.getProcessesInErrorState();
            if (errorList != null && !errorList.isEmpty()) {
                for (ActivityManager.ProcessErrorStateInfo info : errorList) {
                    if (info.condition == ActivityManager.ProcessErrorStateInfo.NOT_RESPONDING) {
                        // 二次确认ANR 用于ANR的记录和上报
                    }
                }
            }
        }

    }
}
