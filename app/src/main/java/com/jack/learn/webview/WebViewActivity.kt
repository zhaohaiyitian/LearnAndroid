package com.jack.learn.webview

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.jack.learn.BuildConfig
import com.jack.learn.R
import com.jack.learn.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.apply {
            settings(webView)
            webView.loadUrl("file:///android_asset/aidl.html") // java调用js

            // 通过注入JavaScript代码检查DOM元素的存在或内容，可以帮助确认页面是否正确渲染。
            // script就是我们要执行的 js 代码，而 resultCallback 是执行结果回调，返回结果是 String 类型。
            webView.evaluateJavascript("alert('hello, my fish, my monkey');", object : ValueCallback<String> {
                override fun onReceiveValue(value: String?) {

                }

            })
            // JsInterface()  js 可以调用该对象中添加了 @JavascriptInterface 注解的公开方法
            webView.addJavascriptInterface(JsInterface(),"launcher") // // 此处的 launcher 可以自定义，最终是 JS 中要使用的对象
        }
    }

    private fun settings(webView: WebView) {
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                Log.d("wangjie","title: $title")
            }
        }
        webView.webViewClient = object : WebViewClient() {

            // WebView每次请求资源时调用，允许应用拦截、修改或替换资源请求。
            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ): WebResourceResponse? {
                // 监控资源加载 记录加载失败的资源
//                Log.d("wangjie","shouldInterceptRequest")
                return super.shouldInterceptRequest(view, request)
            }
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest
            ): Boolean {
                Log.d("wangjie","shouldOverrideUrlLoading: ${request.url}---${request.isRedirect}")
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Log.d("wangjie","onPageStarted")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d("wangjie","onPageFinished ${url}")
            }

            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
//                Log.d("wangjie","onLoadResource ${url}")
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                // 处理加载错误
                Log.d("wangjie","onReceivedError")
            }

            override fun onReceivedHttpError(
                view: WebView?,
                request: WebResourceRequest?,
                errorResponse: WebResourceResponse?
            ) {
                super.onReceivedHttpError(view, request, errorResponse)
                // 处理http错误
            }

            // 当页面即将可见时
            override fun onPageCommitVisible(view: WebView?, url: String?) {
                super.onPageCommitVisible(view, url)
                Log.d("wangjie","onPageCommitVisible")
            }
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            WebView.enableSlowWholeDocumentDraw()
//        }
        val mWebSettings = webView.settings
        // 允许 WebView 加载 JS
        mWebSettings.setJavaScriptEnabled(true)
//        mWebSettings.setSupportZoom(true)
//        mWebSettings.setBuiltInZoomControls(false)
//        mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW)
//        }

        // 硬件加速兼容性问题有点多
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }
//        mWebSettings.setTextZoom(100)
//        mWebSettings.setDatabaseEnabled(true)
//        mWebSettings.setAppCacheEnabled(true)
//        mWebSettings.setLoadsImagesAutomatically(true)
//        mWebSettings.setSupportMultipleWindows(false)
//        mWebSettings.setBlockNetworkImage(false) //是否阻塞加载网络图片  协议http or https
//        mWebSettings.setAllowFileAccess(true) //允许加载本地文件html  file协议
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            mWebSettings.setAllowFileAccessFromFileURLs(false) //通过 file url 加载的 Javascript 读取其他的本地文件 .建议关闭
//            mWebSettings.setAllowUniversalAccessFromFileURLs(false) //允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源
//        }
//        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN)
//        } else {
//            mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL)
//        }
//        mWebSettings.setSavePassword(false)
//        mWebSettings.setSaveFormData(false)
//        mWebSettings.setLoadWithOverviewMode(true)
//        mWebSettings.setUseWideViewPort(true)
//        mWebSettings.setDomStorageEnabled(true)
//        mWebSettings.setNeedInitialFocus(true)
//        mWebSettings.setDefaultTextEncodingName("utf-8") //设置编码格式
//        mWebSettings.setDefaultFontSize(16)
//        mWebSettings.setMinimumFontSize(10) //设置 WebView 支持的最小字体大小，默认为 8
//        mWebSettings.setGeolocationEnabled(true)
//        val appCacheDir = webView.context.getDir("cache", MODE_PRIVATE).path
//        Log.i("WebSetting", "WebView cache dir: $appCacheDir")
//        mWebSettings.setDatabasePath(appCacheDir)
//        mWebSettings.setAppCachePath(appCacheDir)
//        mWebSettings.setAppCacheMaxSize((1024 * 1024 * 80).toLong())
//
//        // 用户可以自己设置useragent
//        mWebSettings.setUserAgentString("webprogress/build you agent info")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)
//        }
    }
}