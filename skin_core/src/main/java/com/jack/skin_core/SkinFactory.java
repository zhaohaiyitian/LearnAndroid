package com.jack.skin_core;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import com.jack.skin_core.bean.SkinItem;
import com.jack.skin_core.bean.SkinView;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SkinFactory implements LayoutInflater.Factory2 {

    public AppCompatDelegate delegate;
    public String[] prefixList = {
            "android.widget.",
            "android.view.",
            "android.webkit."
    };

    // 缓存构造方法的map
    public HashMap<String, Constructor<? extends View>> mConstructorMap = new HashMap<>();

    private Class<?>[] mConstructorSignature = new Class[]{Context.class, AttributeSet.class};

    List<SkinView> skinViews = new ArrayList<>();
    public SkinFactory(AppCompatDelegate delegate) {
        this.delegate = delegate;
    }
    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        //实例化每个控件
        View currentView = null;
        if (delegate != null) {
            currentView = delegate.createView(parent, name, context, attrs);
        }
        if (currentView == null){
            // 如果是Activity的情况下，通过反射去实例化控件
            if (name.contains(".")) {
                onCreateView(name, context, attrs);
            } else {
                for (String s : prefixList) {
                    String className = s+name;
                    currentView = onCreateView(className,context,attrs);
                    if (currentView != null) {
                        break;
                    }
                }
            }
        }

        if (currentView !=null) {
            parserView(context,currentView,attrs);
        }
        return currentView;
    }

    public void apply() {
        for (SkinView skinView : skinViews) {
            skinView.apply();
        }
    }
    // 收集需要换肤的控件
    private void parserView(Context context, View parent, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Skin);
        boolean isSkin = typedArray.getBoolean(R.styleable.Skin_is_skin, false);
        if (!isSkin) {
            return;
        }
        //再去找到这个控件需要换肤的属性
        List<SkinItem> skinItems =  new ArrayList<>();
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            //属性的名字  类似 textColor  src
            String attributeName = attrs.getAttributeName(i);
            //如果说符合条件 句证明这条属性是需要换肤的
            if(attributeName.contains("textColor") || attributeName.contains("src")
                    || attributeName.contains("background")){
                //属性的名字  ID  类型
                String resIdStr = attrs.getAttributeValue(i);
                int resId = Integer.parseInt(resIdStr.substring(1));
                String resourceTypeName = context.getResources().getResourceTypeName(resId);
                String resourceEntryName = context.getResources().getResourceEntryName(resId);
                SkinItem skinItem = new SkinItem(attributeName,resourceTypeName,
                        resourceEntryName,resId);
                skinItems.add(skinItem);
            }
        }
        if (skinItems.size() > 0) {
            SkinView skinView = new SkinView(skinItems,parent);
            skinViews.add(skinView);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        View view = null;
        try {
            // 获取到控件的class对象
            Class aClass = context.getClassLoader().loadClass(name);
            Constructor<? extends View> constructor;
            constructor = mConstructorMap.get(name);
            if (constructor == null) {
                constructor = aClass.getConstructor(mConstructorSignature);
                mConstructorMap.put(name,constructor);
            }
            view = constructor.newInstance(context,attrs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return view;
    }

    public List<SkinView> getSkinViews() {
        return skinViews;
    }
}
