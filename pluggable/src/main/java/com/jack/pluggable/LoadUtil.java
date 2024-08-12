package com.jack.pluggable;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.lang.reflect.Method;

/**
 * 转移到插件中 避免宿主中的resources有影响
 */
public class LoadUtil {


    private static Resources mResources;

    private LoadUtil() {

    }

    public static Resources getResources(Context context, String path) {
        if (mResources == null) {
            mResources = loadResource(context, path);
        }
        return mResources;
    }

    public static Resources loadResource(Context context, String path) {
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
