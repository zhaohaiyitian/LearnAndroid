package com.jack.skin_core.bean;

import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jack.skin_core.SkinManager;

import java.util.List;

public class SkinView {

    //这个控件需要换肤的属性对象的集合
    List<SkinItem> skinItems;
    View view;

    public SkinView(List<SkinItem> skinItems, View view) {
        this.skinItems = skinItems;
        this.view = view;
    }

    public void apply() {
        for (SkinItem item : skinItems) {
            //判断这条属性是background吗？
            if (item.getName().equals("background")) {
                //1. 设置的是color颜色  2.设置的是图片
                if(item.getTypeName().equals("color")){
                    //将资源ID  传给ResouceManager  去进行资源匹配   如果匹配到了  就直接设置给控件
                    // 如果没有匹配到  就把之前的资源ID  设置控件
                    view.setBackgroundColor(SkinManager.getInstance().getColor(item.getResId()));
                }else if(item.getTypeName().equals("drawable") || item.getTypeName().equals("mipmap")){
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
                        view.setBackground(SkinManager.getInstance().getDrawable(item.getResId()));
                    }else{
                        view.setBackgroundDrawable(SkinManager.getInstance().getDrawable(item.getResId()));
                    }
                }
            } else if (item.getName().equals("src")) {
                if(item.getTypeName().equals("drawable") || item.getTypeName().equals("mipmap")){
                    //将资源ID  传给ResouceManager  去进行资源匹配   如果匹配到了  就直接设置给控件
                    // 如果没有匹配到  就把之前的资源ID  设置控件
                    ((ImageView)view).setImageDrawable(SkinManager.getInstance().getDrawable(item.getResId()));
                }else if(item.getTypeName().equals("color")){
//                    ((ImageView)view).setImageResource(SkinManager.getInstance().getColor(skinItem.getResId()));
                }
            } else if (item.getName().equals("textColor")) {
                ((TextView)view).setTextColor(SkinManager.getInstance().getColor(item.getResId()));
            }
        }
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public List<SkinItem> getSkinItems() {
        return skinItems;
    }

    public void setSkinItems(List<SkinItem> skinItems) {
        this.skinItems = skinItems;
    }
}
