package com.jack.learn.view.sticky

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jack.learn.R

class MyAdapter(private val data: List<UserBean>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(R.id.tvName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        Log.d("wangjie","onCreateViewHolder")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.name
        Log.d("wangjie","onBindViewHolder")
    }

    override fun getItemCount(): Int {
        return data.size
    }

    public fun isGroupHead(position: Int):Boolean {
        if (position == 0) {
            return true
        } else {
            val currentGroupName = getGroupName(position)
            val preGroupName = getGroupName(position - 1)
            return currentGroupName != preGroupName
        }
    }

    public fun getGroupName(position: Int): String {
        return data[position].groupName
    }
}