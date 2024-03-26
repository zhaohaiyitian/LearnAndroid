package com.jack.learn.viewpager

import android.content.Context
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
class ThirdFragment : BaseLazyFragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var tvContent: TextView? = null

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ThirdFragment().apply {
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("wangjie","ThirdFragment--onAttach")
    }

    override fun onResume() {
        super.onResume()
        Log.d("wangjie","ThirdFragment--onResume")
    }

    override fun initView(rootView: View?) {
        tvContent = rootView?.findViewById<TextView>(R.id.tvContent)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_third
    }

    override fun onLoadFragmentStart() {
        tvContent?.text = param1
        Log.d("wangjie","ThirdFragment--onLoadFragmentStart")
    }

    override fun onLoadFragmentStop() {
        Log.d("wangjie","ThirdFragment--onLoadFragmentStop")
    }
}