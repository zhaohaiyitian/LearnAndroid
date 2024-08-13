package com.jack.learn.architecture.plugin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.bumptech.glide.load.engine.Resource;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class PluginManagerImpl {

    private DexClassLoader dexClassLoader;
    private Resources resources;

    private Context context;
    private static PluginManagerImpl pluginManager = new PluginManagerImpl();

    public void setContext(Context context) {
        this.context = context.getApplicationContext();
    }

    public static PluginManagerImpl getInstance() {
        return pluginManager;
    }

    public DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }

    public void loadPath() {
        File odexFile = context.getDir("odex",Context.MODE_PRIVATE);
        String apkPath = context.getExternalFilesDir("").toString()+"/pluggable-debug.apk";
        dexClassLoader = new DexClassLoader(apkPath,odexFile.getAbsolutePath(),null,context.getClassLoader());


        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method method = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class); // Hook点
            method.setAccessible(true);
            method.invoke(assetManager,apkPath);
            Resources res = context.getResources();
            resources = new Resources(assetManager,res.getDisplayMetrics(),res.getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Resources getResources() {
        return resources;
    }

}
