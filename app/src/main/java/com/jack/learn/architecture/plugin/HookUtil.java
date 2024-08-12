package com.jack.learn.architecture.plugin;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class HookUtil {
    public static final String TARGET_INTENT = "target_intent";

      // 找 好替换intent的地方 带有intent的方法
    public static void hookAMS() {
        try {
            // 目的：是为了获取 IActivityManager对象    private IActivityManager mInstance;
            // singleton对象
            Class<?> clazz = Class.forName("android.app.ActivityManager");
            Field iActivityTaskManagerSingletonField = clazz.getDeclaredField("IActivityTaskManagerSingleton");
            iActivityTaskManagerSingletonField.setAccessible(true);
            Object singleton = iActivityTaskManagerSingletonField.get(null);

            // mInstance对象 -- IActivityManager对象
            Class<?> singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            Object mInstance = mInstanceField.get(singleton);

            Class<?> iActivityManagerClass = Class.forName("android.app.IActivityManager");
            Object mInstanceProxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{iActivityManagerClass}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if ("startActivity".equals(method.getName())) {
                        // 修改intent
                        int index = 0;
                        for (int i = 0; i < args.length; i++) {
                            if (args[i] instanceof Intent) {
                                index = i;
                                break;
                            }
                        }
                        Intent intent = (Intent) args[index]; // 启动插件的intent
                        // 改成启动代理intent
                        Intent intentProxy = new Intent();
                        intentProxy.setClassName("com.jack.learn","com.jack.learn.architecture.plugin.PluginActivity");

                        intentProxy.putExtra(TARGET_INTENT,intent);
                        args[index] = intentProxy;
                    }
                    return method.invoke(mInstance,args);
                }
            });

            // 用代理对象替换系统的IActivityManager对象
            // mInstance = mInstanceProxy;
            mInstanceField.set(singleton,mInstanceProxy);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Callback.msg----->handleMessage(msg)--->msg.obj---->获取ActivityClientRecord对象--->Intent

    public static void hookHandler()  {


        try {
            // sCurrentActivityThread
            Class<?> clazz = Class.forName("android.app.ActivityThread");
            Field sCurrentActivityThreadField = clazz.getDeclaredField("sCurrentActivityThread");
            sCurrentActivityThreadField.setAccessible(true);
            Object activityThread = sCurrentActivityThreadField.get(null);
            // mH
            Field mHField = clazz.getDeclaredField("mH");
            mHField.setAccessible(true);
            Handler mH = (Handler) mHField.get(activityThread);

            Class<?> aClass = Class.forName("android.os.Handler");
            Field mCallBackField = aClass.getDeclaredField("mCallBack");
            mCallBackField.setAccessible(true);
            Handler.Callback callback = new Handler.Callback() {
                @Override
                public boolean handleMessage(@NonNull Message msg) {
                    switch (msg.what) {
                        case 100:
                            // 获取ActivityClientRecord对象 -- msg.obj
                            try {
                                Field intentProxyField = msg.obj.getClass().getDeclaredField("intent");
                                intentProxyField.setAccessible(true);
                                Intent intentProxy = (Intent) intentProxyField.get(msg.obj);
                                Parcelable intent = intentProxy.getParcelableExtra(TARGET_INTENT);
                                if (intent != null) {
                                    intentProxyField.set(msg.obj, intent);
                                }
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case 159:
                            // final ClientTransaction transaction = (ClientTransaction) msg.obj;
                            Class<?> transactionClass = msg.obj.getClass();
                            try {
                                Field mActivityCallbacksField = transactionClass.getDeclaredField("mActivityCallbacks");
                                mActivityCallbacksField.setAccessible(true);
                                List list = (List) mActivityCallbacksField.get(msg.obj);
                                for (int i = 0; i < list.size(); i++) {
                                    // LaunchActivityItem对象
                                    if (list.get(i).getClass().getName().equals("android.app.servertransaction.LaunchActivityItem")) {
                                        Object launchActivityItem = list.get(i);
                                        Field mIntentProxyField = launchActivityItem.getClass().getDeclaredField("mIntent");
                                        mIntentProxyField.setAccessible(true);
                                        Intent intentProxy = (Intent) mIntentProxyField.get(launchActivityItem);
                                        Parcelable intent = intentProxy.getParcelableExtra(TARGET_INTENT);
                                        if (intent != null) {
                                            mIntentProxyField.set(launchActivityItem, intent);
                                        }
                                    }
                                }

                            } catch (Exception e) {
                            }
                            break;
                    }
                    return false;
                }
            };
            // msg.obj.callback = callback
            mCallBackField.set(mH,callback);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 用我们创建的CallBack对象替换系统的callback对象

    }

    public static Resources loadResource(Context context,String path) {
        AssetManager assetManager = null; // 专门加载插件资源 减少冲突
        try {
            assetManager = AssetManager.class.newInstance();
            Method method = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class); // Hook点
            method.setAccessible(true);
            method.invoke(assetManager,path);
            Resources resources = context.getResources();
            return new Resources(assetManager,resources.getDisplayMetrics(),resources.getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
