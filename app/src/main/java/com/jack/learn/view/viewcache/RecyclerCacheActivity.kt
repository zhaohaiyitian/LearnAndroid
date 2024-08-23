package com.jack.learn.view.viewcache

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jack.learn.databinding.ActivityRecyclerCacheBinding

class RecyclerCacheActivity : AppCompatActivity() {

    private var mDatas: MutableList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityRecyclerCacheBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.apply {
            val linearLayoutManager = LinearLayoutManager(this@RecyclerCacheActivity, LinearLayoutManager.VERTICAL,false)
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.addItemDecoration(DividerItemDecoration(this@RecyclerCacheActivity,DividerItemDecoration.VERTICAL))
            initData()
            val mAdapter = MyCacheAdapter(mDatas)
            recyclerView.adapter = mAdapter
            // viewType类型为TYPE_SPECIAL时，设置四级缓存池RecyclerPool不存储对应类型的数据 因为需要开发者自行缓存
            recyclerView.recycledViewPool.setMaxRecycledViews(MyCacheAdapter.TYPE_SPECIAL,0)
            // 设置ViewCacheExtension缓存
            val cache = MyViewCacheExtension()
            cache.setAdapter(mAdapter)
            recyclerView.setViewCacheExtension(cache)
            recyclerView.setHasFixedSize(true)
        }
    }


    private fun initData() {
        for (i in 0..50) {
            if (i == 0) {
                mDatas.add("我是一条特殊的数据，我的位置固定、内容不会变");
            } else {
                mDatas.add("这是第" + (i + 1) + "条数据");
            }
        }
    }
}