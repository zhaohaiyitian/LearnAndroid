package com.jack.learn.architecture.plugin;

import android.content.Context;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.DexClassLoader;

public class LoadUtil {

    public static void loadClass(Context context) {


        try {
            Class<?> dexPathListClass = Class.forName("dalvik.system.DexPathList");
            Field dexElementsField = dexPathListClass.getDeclaredField("dexElements");
            dexElementsField.setAccessible(true);

            Class<?> classLoaderClass = Class.forName("dalvik.system.BaseDexClassLoader");
            Field pathListField = classLoaderClass.getDeclaredField("pathList");
            pathListField.setAccessible(true);

            // 获取宿主的类加载器
            ClassLoader pathClassLoader = context.getClassLoader();
            Object hostPathList = pathListField.get(pathClassLoader);
            Object[] hostDexElements = (Object[]) dexElementsField.get(hostPathList);

            ClassLoader pluginClassLoader = new DexClassLoader(context.getExternalFilesDir("").toString()+"/pluggable-debug.apk",context.getCacheDir().getAbsolutePath(),
                    null,pathClassLoader);
            Object pluginPathList = pathListField.get(pluginClassLoader);
            Object[] pluginDexElements = (Object[]) dexElementsField.get(pluginPathList);
            Object[] newElements  = (Object[]) Array.newInstance(hostDexElements.getClass().getComponentType(), hostDexElements.length + pluginDexElements.length);

            System.arraycopy(hostDexElements,0,newElements,0,hostDexElements.length);
            System.arraycopy(pluginDexElements,0,newElements,hostDexElements.length,pluginDexElements.length);

            // 赋值到宿主的dexElements中
            // hostDexElements = newElements
            dexElementsField.set(hostPathList,newElements);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
