package com.jack.learn.apm.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 用来提供子线程inflate view的功能，避免某个view层级太深太复杂，主线程inflate会耗时很长，
 */
public class AsyncInflateManager {

    private static volatile AsyncInflateManager inflateManager;
    private ConcurrentHashMap<String,AsyncInflateItem> mInflateMap; // //保存inflateKey以及InflateItem，里面包含所有要进行inflate的任务
    private ConcurrentHashMap<String, CountDownLatch> mInflateLatchMap;
    private ExecutorService mThreadPool; //用来进行inflate工作的线程池
    private AsyncInflateManager() {
        mThreadPool = new ThreadPoolExecutor(4,4,0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        mInflateMap = new ConcurrentHashMap<>();
        mInflateLatchMap = new ConcurrentHashMap<>();
    }

    public AsyncInflateManager getInstance() {
        if (inflateManager == null) {
            synchronized (this) {
                if (inflateManager == null) {
                    inflateManager = new AsyncInflateManager();
                }
            }
        }
        return inflateManager;
    }

    /**
     *用来获得异步inflate出来的view
     * @param context
     * @param layoutResId 需要拿的layoutId
     * @param parent container
     * @param inflateKey 每一个View会对应一个inflateKey，因为可能许多地方用的同一个 layout，但是需要inflate多个，用InflateKey进行区分
     * @param inflater
     * @return 最后inflate出来的view
     */
    public View getInflatedView(Context context, int layoutResId, ViewGroup parent, String inflateKey, LayoutInflater inflater) {
        if (!TextUtils.isEmpty(inflateKey) && mInflateMap.containsKey(inflateKey)) {
            AsyncInflateItem item = mInflateMap.get(inflateKey);
            CountDownLatch latch = mInflateLatchMap.get(inflateKey);
            if (item != null) {
                View resultView = item.inflatedView;

            }
        }
        return inflater.inflate(layoutResId,parent,false);
    }

}
