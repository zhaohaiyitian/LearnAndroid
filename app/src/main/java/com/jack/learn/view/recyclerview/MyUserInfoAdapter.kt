package com.jack.learn.view.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jack.learn.R
import com.jack.learn.thirdlib.UserInfo

/**
 * Payload局部刷新更适合于交互场景下的局部更新 其中某些UI元素需要根据用户的操作进行更新‌  比如item中的点赞 评论 人数
 * 而DiffUtil则更适合于数据集发生较大变化时的局部刷新
 */
class MyUserInfoAdapter(private val data: List<UserInfo>) :
    RecyclerView.Adapter<MyUserInfoAdapter.UserViewHolder>() {

    private var mOldList: List<UserInfo>? = null
    private var mNewList: List<UserInfo>? = null
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(R.id.tvName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.name
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun updateList(newList: List<UserInfo>) {
        //DiffUtil 的差异计算可能会比较耗时，如果在 UI 线程中进行计算，就会导致应用的卡顿，影响用户体验。
        // 所以，在使用 DiffUtil 时，最好将差异计算放在一个异步任务中进行，或者使用其他方式来避免阻塞 UI 线程。
        val diffResult = DiffUtil.calculateDiff(UserDiffCallBack(mOldList,newList),false)
        // 更新数据集
        mOldList = mNewList
        mNewList = newList
        diffResult.dispatchUpdatesTo(this)
    }
}