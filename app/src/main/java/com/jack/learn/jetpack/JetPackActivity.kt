package com.jack.learn.jetpack

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jack.learn.R
import com.jack.learn.click
import com.jack.learn.databinding.ActivityJetpackBinding
import com.jack.learn.jetpack.navigation.NavigationActivity
import com.jack.learn.jetpack.paging.PagingActivity
import com.jack.learn.jetpack.workmanager.WorkManagerActivity

class JetPackActivity : AppCompatActivity(),OnDataPassListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityJetpackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            paging.click {
                startActivity(Intent(this@JetPackActivity, PagingActivity::class.java))
            }
            work.click {
                startActivity(Intent(this@JetPackActivity, WorkManagerActivity::class.java))
            }
            navigation.click {
                startActivity(Intent(this@JetPackActivity, NavigationActivity::class.java))
            }
        }
        val tvContent = findViewById<TextView>(R.id.tvContent)
        // 这行代码 会去ViewModelStore获取对象，如果没有 会创建一个
        val mViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
        ViewModelProvider(this).get(UserViewModel::class.java)
        mViewModel.text.observe(this) {
            Log.d("wangjie",it)
            tvContent.text = it
        }
        mViewModel.setText(1)

        // 只在活跃状态下才能收到数据变更
        mViewModel.updateContent().observe(this) {
//            tvContent.text = it
        }
        // 不区分是否活跃都能收到数据变更
//        mViewModel.updateContent().observeForever {
//
//        }
        mViewModel.mergerTest()
        val fragmentA = FragmentA.getInstance()
        val fragmentB = FragmentB.getInstance()
        supportFragmentManager.beginTransaction().replace(R.id.container_id1,fragmentA).commitAllowingStateLoss()
        supportFragmentManager.beginTransaction().replace(R.id.container_id2,fragmentB).commitAllowingStateLoss()


//        fragmentA.setOnDataPassListener(object : OnDataPassListener {
//            override fun onPassData(data: String) {
//                Log.d("wangjie",data)
//            }
//
//        })
        Handler().postDelayed({
            val fragment2 = supportFragmentManager.findFragmentById(R.id.container_id1) as FragmentA
            fragment2.setOnDataPassListener(this)
        },3000)

        // lifecycle
        lifecycle.addObserver(MyLifecycleObserver())
    }

    override fun onPassData(data: String) {
        val fragment1 = supportFragmentManager.findFragmentById(R.id.container_id2) as FragmentB
        if (fragment1 != null) {
            fragment1.receiveData(data)
        }
    }

}