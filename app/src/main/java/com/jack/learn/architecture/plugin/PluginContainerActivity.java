package com.jack.learn.architecture.plugin;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jack.learn.R;
import com.jack.shadowcore.ContextTheme;

/**
 * pluggable: 插件工程 生成apk 然后下发到app中
 */
public class PluginContainerActivity extends Activity {

    private String className;
    private ContextTheme contextTheme;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        className = getIntent().getStringExtra("classname");
        if (TextUtils.isEmpty(className)) {
            className = "com.jack.pluggable.shadow.ShadowActivity";
        }
        try {
            contextTheme = (ContextTheme) getClassLoader().loadClass(className).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        contextTheme.attach(this);
        Bundle bundle = new Bundle();
        bundle.putString("number","1888888888");
        contextTheme.onCreate(bundle);
    }

    @Override
    public ClassLoader getClassLoader() {
        return PluginManagerImpl.getInstance().getDexClassLoader();
    }

    @Override
    public Resources getResources() {
        return PluginManagerImpl.getInstance().getResources();
    }

    @Override
    public void startActivity(Intent intent) {
        String className1 = intent.getStringExtra("classname");
        Intent intent1 = new Intent(this, PluginContainerActivity.class);
        intent1.putExtra("classname",className1);
        super.startActivity(intent1);
    }
}
