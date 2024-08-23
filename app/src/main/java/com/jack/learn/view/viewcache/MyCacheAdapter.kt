package com.jack.learn.view.viewcache

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jack.learn.R

/**
 *  本质上缓存是为了减少重复绘制View和绑定数据的时间，从而提高了滑动时的性
 *
 *  tryGetViewHolderForPositionByDeadline()
 *
 *  从缓存池mRecyclerPool中尝试获取ViewHolder
 *  如果获取成功，会重置ViewHolder状态，所以需要重新执行Adapter#onBindViewHolder绑定数据
 *
 *  若以上缓存中都没有找到对应的ViewHolder，最终会调用Adapter中的onCreateViewHolder创建一个
 *
 *
 *
 *  通过mAttachedScrap、mCachedViews及mViewCacheExtension获取的ViewHolder不需要重新创建布局及绑定数据；
 *  通过缓存池mRecyclerPool获取的ViewHolder不需要重新创建布局，但是需要重新绑定数据；
 *  如果上述缓存中都没有获取到目标ViewHolder，那么就会回调Adapter#onCreateViewHolder创建布局，以及回调Adapter#onBindViewHolder来绑定数据。
 *
 *
 *
 *  实现ViewCacheExtension
 *  ViewCacheExtension适用场景：ViewHolder位置固定、内容固定、数量有限时使用
 */
class MyCacheAdapter(private val data: List<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        //viewType类型 TYPE_COMMON代表普通类型 TYPE_SPECIAL代表特殊类型(此处的View和数据一直不变)
        const val TYPE_COMMON = 1
        const val TYPE_SPECIAL = 101
    }

    public var caches = SparseArray<View>() // 开发者自行维护的缓存
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(R.id.tvName)
    }

    class SpecialViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(R.id.tvName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_SPECIAL) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_layout, parent, false)
            return SpecialViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_layout, parent, false)
            return ViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        if (holder is SpecialViewHolder) {
            holder.textView.text = item
            // 这里是重点，根据position将View放到自定义缓存中
            caches.put(position,holder.itemView)
        } else if (holder is ViewHolder) {
            holder.textView.text = item
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_SPECIAL//第一个位置View和数据固定
        } else {
            return TYPE_COMMON
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
    }
}