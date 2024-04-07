package com.jack.learn.view.viewcache

import android.view.View
import androidx.recyclerview.widget.RecyclerView

//实现自定义缓存ViewCacheExtension
class MyViewCacheExtension: RecyclerView.ViewCacheExtension() {
    private var mAdapter: MyCacheAdapter? = null
    override fun getViewForPositionAndType(
        recycler: RecyclerView.Recycler,
        position: Int,
        viewType: Int
    ): View? {
        //如果viewType为TYPE_SPECIAL,使用自己缓存的View去构建ViewHolder
        //否则返回null，会使用系统RecyclerPool缓存或者重新通过onCreateViewHolder构建View及ViewHolder
        return if (viewType == MyCacheAdapter.TYPE_SPECIAL) {
            mAdapter?.caches?.get(position)
        } else {
            null
        }
    }

    fun setAdapter(adapter: MyCacheAdapter) {
        mAdapter = adapter
    }

}