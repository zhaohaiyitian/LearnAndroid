package com.jack.learn.viewpager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jack.learn.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FiveFragment : BaseLazyFragment() {
    private var param1: String? = null
    private var param2: String? = null

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FiveFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun initView(rootView: View?) {
        val tvContent = rootView?.findViewById<TextView>(R.id.tvContent)
        tvContent?.text = param1
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_five
    }

    override fun onLoadFragmentStart() {
        Log.d("wangjie","FiveFragment--onLoadFragmentStart")
    }

    override fun onLoadFragmentStop() {
        Log.d("wangjie","FiveFragment--onLoadFragmentStop")
    }

}