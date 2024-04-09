package com.jack.learn.view.sticky

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jack.learn.R
import com.jack.learn.view.CustomView

/**
 * Activity.attach()
 *    --mWindow = new PhoneWindow(this, window, activityConfigCallback)
 * PhoneWindow 中有一个 DecorView，在 setContentView 中会将 layoutId生成的View 填充到此 DecorView 中。
 */
class NestedScrollViewActivity : AppCompatActivity() {

    private var mData: MutableList<UserBean> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_scroll_view)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
//        recyclerView.addItemDecoration(NBItemDecoration())
        getData()
        recyclerView.adapter = MyAdapter(mData)

        val nestScrollView = findViewById<CustomNestedScrollView>(R.id.nestScrollView)
        val llTabLayout = findViewById<LinearLayout>(R.id.llTabLayout)
        val tvContent = findViewById<TextView>(R.id.tvContent)
        llTabLayout.post {
            llTabLayout.layoutParams.height = nestScrollView.measuredHeight
            llTabLayout.requestLayout()
        }
        val customView = findViewById<CustomView>(R.id.customView)
        customView.post {
            Log.d("wangjie","${customView.measuredWidth} ${customView.width}")
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