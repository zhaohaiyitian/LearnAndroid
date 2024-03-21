package com.jack.learn.jetpack

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jack.learn.R

class MVVMActivity : AppCompatActivity(),OnDataPassListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvm)
        val tvContent = findViewById<TextView>(R.id.tvContent)
        // 这行代码 会去ViewModelStore获取对象，如果没有 会创建一个
        val mViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
        ViewModelProvider(this).get(UserViewModel::class.java)
        mViewModel.text.observe(this) {
            tvContent.text = it
        }
        tvContent.run {

        }
        mViewModel.setText(1)

        mViewModel.updateContent().observe(this) {
//            tvContent.text = it
        }
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