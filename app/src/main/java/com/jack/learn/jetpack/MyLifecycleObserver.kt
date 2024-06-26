package com.jack.learn.jetpack

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 *  ComponentActivity implements LifecycleOwner
 *  ReportFragment.injectIfNeededIn(this)
 *     -->dispatch
 *        -->handleLifecycleEvent
 *           -->ReflectiveGenericLifecycleObserver
 *  通过注解和反射
 */
class MyLifecycleObserver: LifecycleObserver {


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Log.d("wangjie","OnCreate()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        Log.d("wangjie","onDestroy()")
    }


}