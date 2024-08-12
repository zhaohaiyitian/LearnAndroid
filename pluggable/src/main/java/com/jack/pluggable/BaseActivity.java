package com.jack.pluggable;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ContextThemeWrapper;

import androidx.annotation.Nullable;

import java.lang.reflect.Field;

public class BaseActivity extends Activity {

    public Context mContext;
    @Override
    public Resources getResources() {
//        if (getApplication() != null && getApplication().getResources() != null) {
//            return getApplication().getResources();
//        }
//        return super.getResources();
        Resources resources = LoadUtil.getResources(getApplication(), "");
        return resources == null? super.getResources():resources;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 解决id冲突问题  因为context用到了宿主的上下文
        Resources resources = LoadUtil.getResources(getApplication(), "");

        mContext = new ContextThemeWrapper(getBaseContext(),0);
        // 替换了插件的    ContextImpl中的private @NonNull Resources mResources;
        Class<? extends Context> aClass = mContext.getClass();
        try {
            Field mResourcesField = aClass.getDeclaredField("mResources");
            mResourcesField.setAccessible(true);
            mResourcesField.set(mContext,resources);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
