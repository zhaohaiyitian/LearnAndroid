package com.jack.learn.view.pager2

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.jack.learn.R


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class FourFragment2 : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var tvContent: TextView? = null

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FourFragment2().apply {
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



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("wangjie", "FourFragment2--onCreateView")
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("wangjie", "FourFragment2--onViewCreated")
        tvContent = view.findViewById<TextView>(R.id.tvContent)
        tvContent?.text = param1
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("wangjie", "FourFragment2--onAttach")
    }

    override fun onResume() {
        super.onResume()
        Log.d("wangjie", "FourFragment2--onResume")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("wangjie", "FourFragment2--onDestroyView")
    }

}