package com.jack.learn.jetpack;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public class NonStickyMutableLiveData<T> extends MutableLiveData {

    private boolean stickFlag=false;

    @Override
    public void observe( LifecycleOwner owner,  Observer observer) {
        super.observe(owner, observer);
        if(!stickFlag) {
            hook(observer);
            stickFlag=true;
        }
    }

    //在这里去改变onChange的流程
    private void hook(Observer<? super T> observer) {
        try {
            //1.得到mLastVersion
            //获取到LiveData的类中的mObservers对象
            //SafeIterableMap<Observer<? super T>, ObserverWrapper> mObservers
            Class<LiveData> liveDataClass = LiveData.class;
            Field mObserversField = liveDataClass.getDeclaredField("mObservers");
            mObserversField.setAccessible(true);


            //获取到这个成员变量的对象
            Object mObserversObject = mObserversField.get(this);
            //得到map对应的class对象
            Class<?> mObserversClass = mObserversObject.getClass();
            //获取到mObservers对象的get方法   entry
            Method get = mObserversClass.getDeclaredMethod("get", Object.class);
            get.setAccessible(true);
            //执行get方法   mObservers.get(observer)
            Object invokeEntry=get.invoke(mObserversObject,observer);
            //定义一个空的对象
            Object observerWraper=null;
            if(invokeEntry!=null && invokeEntry instanceof Map.Entry){
                observerWraper=((Map.Entry)invokeEntry).getValue();//ObserverWrapper
            }
            if(observerWraper==null){
                throw new NullPointerException("observerWraper is null");
            }
            //得到ObserverWrapper的类对象  编译擦除问题会引起多态冲突所以用getSuperclass
            //TODO:getClass()返回对应的当前正在运行时的类所对应的对象
            Class<?> superclass = observerWraper.getClass().getSuperclass();//mLastVersion
            Field mLastVersion = superclass.getDeclaredField("mLastVersion");
            mLastVersion.setAccessible(true);
            //2.得到mVersion
            Field mVersion = liveDataClass.getDeclaredField("mVersion");
            mVersion.setAccessible(true);
            //3.把mVersion的数据填入到mLastVersion中
            Object mVersionValue=mVersion.get(this);
            mLastVersion.set(observerWraper,mVersionValue);



        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
