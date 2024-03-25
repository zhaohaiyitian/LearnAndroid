package com.jack.learn.apm.choreographer;

import android.os.Build;
import android.util.Log;
import android.view.Choreographer;

/**
 * 细化帧数
 * 适用于快速定位帧率监控
 */
public class ChoreographerHelper {
    private static final String TAG = "ChoreographerHelper";
    static long lastFrameTimeNanos = 0;

    public static void start() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
                @Override
                public void doFrame(long frameTimeNanos) {
                    //上次回调时间
                    if (lastFrameTimeNanos == 0) {
                        lastFrameTimeNanos = frameTimeNanos;
                        Choreographer.getInstance().postFrameCallback(this);
                        return;
                    }
                    long diff = (frameTimeNanos - lastFrameTimeNanos) / 1_000_000;
                    if (diff > 16.6f) {

                        //掉帧数
                        int droppedCount = (int) (diff / 16.6);
                        if (droppedCount > 2) {
                            Log.w(TAG, "UI线程超时(超过16ms)当前:" + diff + "ms" + " , 丢帧:" + droppedCount);
                        }
                    }
                    lastFrameTimeNanos = frameTimeNanos;
                    Choreographer.getInstance().postFrameCallback(this);
                }
            });
        }
    }
}
