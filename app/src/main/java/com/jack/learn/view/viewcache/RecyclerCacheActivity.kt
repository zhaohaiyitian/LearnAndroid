package com.jack.learn.view.viewcache

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.jack.learn.databinding.ActivityRecyclerCacheBinding
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener

class RecyclerCacheActivity : AppCompatActivity() {

    private var mDatas: MutableList<String> = ArrayList()

    private lateinit var adapter: MyAdapter
    private val itemList = mutableListOf<String>()
    private var page = 1
    private val PAGE_SIZE = 10
    private var viewBinding:ActivityRecyclerCacheBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityRecyclerCacheBinding.inflate(layoutInflater)
        setContentView(viewBinding?.root)
        viewBinding?.apply {
//            val linearLayoutManager = LinearLayoutManager(this@RecyclerCacheActivity, LinearLayoutManager.VERTICAL,false)
//            linearLayoutManager.isItemPrefetchEnabled = true // 设置预加载
//            recyclerView.layoutManager = linearLayoutManager
//            recyclerView.addItemDecoration(DividerItemDecoration(this@RecyclerCacheActivity,DividerItemDecoration.VERTICAL))
//            initData()
//            val mAdapter = MyCacheAdapter(mDatas)
//            recyclerView.adapter = mAdapter
            // viewType类型为TYPE_SPECIAL时，设置四级缓存池RecyclerPool不存储对应类型的数据 因为需要开发者自行缓存
//            recyclerView.recycledViewPool.setMaxRecycledViews(MyCacheAdapter.TYPE_SPECIAL,0)
            // 设置ViewCacheExtension缓存
//            val cache = MyViewCacheExtension()
//            cache.setAdapter(mAdapter)
//            recyclerView.setViewCacheExtension(cache)
//            recyclerView.setHasFixedSize(true)

            // 设置 RecyclerView 布局管理器和适配器
            recyclerView.layoutManager = LinearLayoutManager(this@RecyclerCacheActivity)
            adapter = MyAdapter(itemList)
            recyclerView.adapter = adapter

            // 初始化数据
            loadData(page)

            // 设置下拉刷新监听
            refreshLayout.setOnRefreshListener(object : OnRefreshListener {
                override fun onRefresh(refreshlayout: RefreshLayout) {
                    page = 1
                    itemList.clear()
                    loadData(page)
                    refreshlayout.finishRefresh(2000)
                }
            })

            // 设置上拉加载更多监听
            refreshLayout.setOnLoadMoreListener(object : OnLoadMoreListener {
                override fun onLoadMore(refreshlayout: RefreshLayout) {
                    page++
                    loadData(page)
                    refreshlayout.finishLoadMore(2000)
                    Log.d("wangjie","page: ${page}")
                }
            })
        }
    }

    private fun loadData(page: Int) {
        Handler(Looper.getMainLooper()).postDelayed({
            // 模拟网络请求
            for (i in 0 until PAGE_SIZE) {
                itemList.add("Item ${(page - 1) * PAGE_SIZE + i + 1}")
            }
            adapter.notifyDataSetChanged()
            if (page > 5) {
                viewBinding?.refreshLayout?.setNoMoreData(true)
            }
        }, 1000)
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