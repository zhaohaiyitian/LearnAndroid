package com.jack.learn

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jack.learn.apm.APMActivity
import com.jack.learn.databinding.ActivityMainBinding
import com.jack.learn.designpattern.DesignPatternActivity
import com.jack.learn.jetpack.MVVMActivity
import com.jack.learn.jni.JNIActivity
import com.jack.learn.kotlin.KotlinActivity
import com.jack.learn.thirdlib.ThirdLibActivity
import com.jack.learn.view.sticky.CustomViewActivity
import com.jack.learn.view.viewcache.RecyclerCacheActivity
import com.jack.learn.viewpager.ViewPagerActivity
import com.jack.learn.webview.WebViewActivity

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
            custom.click {
                startActivity(Intent(this@MainActivity, CustomViewActivity::class.java))
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
            viewStub.inflate()
        }
    }


}
