package com.jack.learn

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.jack.learn.NBApplication.Companion.CACHED_ENGINE_ID
import com.jack.learn.apm.APMActivity
import com.jack.learn.database.DataBaseActivity
import com.jack.learn.databinding.ActivityMainBinding
import com.jack.learn.designpattern.DesignPatternActivity
import com.jack.learn.flutter.FlutterAppActivity
import com.jack.learn.jetpack.MVVMActivity
import com.jack.learn.jni.JNIActivity
import com.jack.learn.juc.ConcurrentActivity
import com.jack.learn.kotlin.KotlinActivity
import com.jack.learn.thirdlib.koom.OOMMonitorInitTask
import com.jack.learn.thirdlib.ThirdLibActivity
import com.jack.learn.view.CustomViewActivity
import com.jack.learn.view.sticky.NestedScrollViewActivity
import com.jack.learn.view.viewcache.RecyclerCacheActivity
import com.jack.learn.viewpager.ViewPagerActivity
import com.jack.learn.webview.WebViewActivity
import com.kwai.koom.javaoom.monitor.OOMMonitor
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

/**
 * 消息驱动模型
 * Activity启动流程
 * AMS-->Binder-->ActivityThread##ApplicationThread-->Handler--发送消息调用各个生命周期的方法
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initKOOM()
        viewBinding.apply {
            jni.click {
                startActivity(Intent(this@MainActivity,JNIActivity::class.java))
            }
            jetpack.click {
                startActivity(Intent(this@MainActivity,MVVMActivity::class.java))
            }
            jetpack.click {
                startActivity(Intent(this@MainActivity,MVVMActivity::class.java))
            }
            btnKotlin.click {
                startActivity(Intent(this@MainActivity,KotlinActivity::class.java))
            }
            pattern.click {
                startActivity(Intent(this@MainActivity,DesignPatternActivity::class.java))
            }
            scroll.click {
                startActivity(Intent(this@MainActivity, NestedScrollViewActivity::class.java))
            }
            apm.click {
                startActivity(Intent(this@MainActivity,APMActivity::class.java))
            }
            viewPager.click {
                startActivity(Intent(this@MainActivity,ViewPagerActivity::class.java))
            }
            thirdLib.click {
                startActivity(Intent(this@MainActivity, ThirdLibActivity::class.java))
            }
            cache.click {
                startActivity(Intent(this@MainActivity, RecyclerCacheActivity::class.java))
            }
            webView.click {
                startActivity(Intent(this@MainActivity, WebViewActivity::class.java))
            }
            customView.click {
                startActivity(Intent(this@MainActivity, CustomViewActivity::class.java))
            }
            flutter.click {
                FlutterAppActivity.start(this@MainActivity,"111")
//                startActivity(FlutterActivity.withNewEngine().initialRoute("/").build(this@MainActivity))
//                startActivity(FlutterActivity.createDefaultIntent(this@MainActivity))
            }
            concurrent.click {
                startActivity(Intent(this@MainActivity, ConcurrentActivity::class.java))
            }
            dataBase.click {
                startActivity(Intent(this@MainActivity, DataBaseActivity::class.java))
            }
            viewStub.inflate()
            showFlutterView(viewBinding.flFlutter)
        }
    }

    private fun showFlutterView(layout: FrameLayout) {
        val flutterView = FlutterView(this)
        val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        layout.addView(flutterView,lp)
        // 关键代码 将Flutter页面添加到FlutterView上
        FlutterEngineCache.getInstance().get(CACHED_ENGINE_ID)?.let {
            flutterView.attachToFlutterEngine(it)
        }
    }

    private fun initKOOM() {
        OOMMonitorInitTask().init(application)
        OOMMonitor.startLoop(true,false,5000L)
    }


}
