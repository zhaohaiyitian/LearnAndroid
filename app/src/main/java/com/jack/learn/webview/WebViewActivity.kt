package com.jack.learn.webview

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
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
            webView.loadUrl("https://baidu.com") // java调用js
        }
    }

    private fun settings(webView: WebView) {
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                Log.d("wangjie","$title")
            }
        }
        webView.webViewClient = object : WebViewClient() {
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

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                Log.d("wangjie","onReceivedError")
            }
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            WebView.enableSlowWholeDocumentDraw()
//        }
        val mWebSettings = webView.settings
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