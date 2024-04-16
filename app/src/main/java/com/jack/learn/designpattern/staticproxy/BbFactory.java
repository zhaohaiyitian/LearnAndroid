package com.jack.learn.designpattern.staticproxy;

import android.util.Log;

public class BbFactory implements WomanToolsFactory {
    @Override
    public void saleWomanTools(int length) {
        Log.d("wangjie","根据你的需求为你提供了"+length+"的工具");
    }
}
