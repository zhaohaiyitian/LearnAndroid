package com.jack.skin_core;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import java.lang.reflect.Method;

/**
 * 加载sd卡中的资源包，然后能够从里面获取我们想要的资源
 */
public class SkinManager {

    public static volatile SkinManager instance;

    private Context mContext;
    private Resources mResources;
    private String mSkinPackageName;
    private SkinManager() {

    }

    public static SkinManager getInstance() {
        if (instance == null) {
            synchronized (SkinManager.class) {
                if (instance == null) {
                    instance = new SkinManager();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        mContext = context;
    }

    /**
     * 根据资源包的存储路径 去加载资源
     * @param path
     */
    public void loadSkinApk(String path) {
        // 真正的目的是获得资源包的资源对象
        try {
            PackageManager packageManager = mContext.getPackageManager();
            PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
            mSkinPackageName = packageArchiveInfo.packageName;

            AssetManager assetManager = AssetManager.class.newInstance();
            Method method = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            method.setAccessible(true);
            method.invoke(assetManager,path);
            // 创建一个资源对象，管理资源包里面的资源
            mResources = new Resources(assetManager,mContext.getResources().getDisplayMetrics(),mContext.getResources().getConfiguration());
            // 如何让assetManager于皮肤包关联
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 写匹配各种资源的方法
     */
    public int getColor(int resId) {
        if (resourceIsNull()) {
            return resId;
        }
        //获取到资源名字和资源类型
        String resourceEntryName = mContext.getResources().getResourceEntryName(resId);
        String resourceTypeName = mContext.getResources().getResourceTypeName(resId);
        //去资源包的资源对象中去匹配
        int identifier = mResources.getIdentifier(resourceEntryName, resourceTypeName, mSkinPackageName);
        if (identifier == 0) {
            return resId;
        }
        return mResources.getColor(identifier);
    }


    /**
     * 从资源包中拿到drawable的资源id
     */
    public Drawable getDrawable(int id){
        if(resourceIsNull()){
            //获取到这个id在当前应用中的Drawable对象
            return ContextCompat.getDrawable(mContext,id);
        }
        //获取到资源id的类型
        String resourceTypeName = mContext.getResources().getResourceTypeName(id);
        //获取到的就是资源id的名字
        String resourceEntryName = mContext.getResources().getResourceEntryName(id);
        //就是colorAccent这个资源在外置APK中的id
        int identifier = mResources.getIdentifier(resourceEntryName, resourceTypeName, mSkinPackageName);
        if(identifier == 0){
            return ContextCompat.getDrawable(mContext,id);
        }
        return mResources.getDrawable(identifier);
    }

    /**
     * 从资源包中拿到drawable的资源id
     */
    public int getDrawableId(int id){
        if(resourceIsNull()){
            return id;
        }
        //获取到资源id的类型
        String resourceTypeName = mContext.getResources().getResourceTypeName(id);
        //获取到的就是资源id的名字
        String resourceEntryName = mContext.getResources().getResourceEntryName(id);
        //就是colorAccent这个资源在外置APK中的id
        int identifier = mResources.getIdentifier(resourceEntryName, resourceTypeName, mSkinPackageName);
        if(identifier == 0){
            return id;
        }
        return identifier;
    }

    public boolean resourceIsNull() {
        if (mResources == null) {
            return true;
        }
        return false;
    }

}
