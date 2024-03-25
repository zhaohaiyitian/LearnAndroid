package com.jack.learn.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.jack.learn.R
import com.jack.learn.view.sticky.CustomNestedScrollView
import com.jack.learn.view.sticky.MyAdapter
import com.jack.learn.view.sticky.NBItemDecoration
import com.jack.learn.view.sticky.UserBean

class CustomViewActivity : AppCompatActivity() {

    private var mData: MutableList<UserBean> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
//        recyclerView.addItemDecoration(NBItemDecoration())
        getData()
        recyclerView.adapter = MyAdapter(mData)

        val nestScrollView = findViewById<CustomNestedScrollView>(R.id.nestScrollView)
        val tabLayout = findViewById<LinearLayout>(R.id.llTabLayout)
        val tvContent = findViewById<TextView>(R.id.tvContent)
        tabLayout.post {
            tabLayout.layoutParams.height = nestScrollView.measuredHeight
            tabLayout.requestLayout()
        }
    }

    private fun getData() {
        for (i in 0..10) {
            mData.add(UserBean("AAA$i", "A"))
        }
        for (i in 0..10) {
            mData.add(UserBean("BBB$i", "B"))
        }
        for (i in 0..10) {
            mData.add(UserBean("CCC$i", "C"))
        }
        for (i in 0..10) {
            mData.add(UserBean("DDD$i", "D"))
        }
        for (i in 0..10) {
            mData.add(UserBean("EEE$i", "E"))
        }
    }
}