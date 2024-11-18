package com.jack.learn.webview;

import android.util.Log;
import android.webkit.JavascriptInterface;

public class JsInterface {



    // js 调用 java 的方法不是在主线程中调用的，而是在 webview 自己线程中调用的，所以在编写某些涉及到 UI 的操作时需要先切换至主线程。
    // 这个方法由 JS 调用
    @JavascriptInterface
    public void callAndroid(String value) {
        Log.d("wangjie",value);
    }
}
