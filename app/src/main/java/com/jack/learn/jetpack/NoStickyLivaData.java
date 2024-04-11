package com.jack.learn.jetpack;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class NoStickyLivaData<T> extends LiveData {

    private boolean stickFlag = false;

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer observer) {
        super.observe(owner, observer);
        if (!stickFlag) {
            hook(observer);
            stickFlag = true;
        }
    }

    private void hook(Observer<? super T> observer) {
        //1.得到mLastVersion
        // 获取到LiveData的类的mObservers对象
        // SafeIterableMap<Observer<? super T>, ObserverWrapper> mObservers
        try {
            Class<LiveData> aClass = LiveData.class;
            Field mObservers = aClass.getDeclaredField("mObservers");
            mObservers.setAccessible(true);
            //获取到这个成员变量的对象 new SafeIterableMap<>()
            Object mObserversObject = mObservers.get(this);
            Class<?> mObserversClass = mObserversObject.getClass();
            Method get = mObserversClass.getDeclaredMethod("get", Object.class);
            get.setAccessible(true);
            //执行get方法   mObservers.get(observer)
            Object invokeEntry = get.invoke(mObserversObject,observer);
            Object observerWrapper = null;
            if (invokeEntry != null && invokeEntry instanceof Map.Entry) {
                observerWrapper = ((Map.Entry)invokeEntry).getValue();
            }
            if (observerWrapper == null) {
                throw new NullPointerException("observerWraper is null");
            }
            Class<?> superclass = observerWrapper.getClass().getSuperclass();
            Field mLastVersion = superclass.getDeclaredField("mLastVersion");
            mLastVersion.setAccessible(true);
            // 2.得到mVersion
            Field mVersion = aClass.getDeclaredField("mVersion");
            mVersion.setAccessible(true);
            //3.把mVersion的数据填入到mLastVersion中
            Object mVersionValue = mVersion.get(this);
            mLastVersion.set(observerWrapper,mVersionValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
