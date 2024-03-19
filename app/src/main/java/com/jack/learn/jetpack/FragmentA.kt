package com.jack.learn.jetpack

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jack.learn.R

/**
 * 如何通过ViewModel 在两个fragment中传值
 * val mViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(CommonViewModel::class.java)
 * 两个fragment共用同一个ViewModel实例
 */
class FragmentA: Fragment() {


    private var dtaPassListener: OnDataPassListener? = null

    fun setOnDataPassListener(dataPassListener: OnDataPassListener) {
        this.dtaPassListener = dataPassListener
    }
    companion object {
        fun getInstance():FragmentA {
            return FragmentA()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(CommonViewModel::class.java)
//        mViewModel.commonData.observe(viewLifecycleOwner) {
//            Log.d("wangie","FragmentA: "+it)
//        }

        view.findViewById<Button>(R.id.send).setOnClickListener {
//            if (dtaPassListener != null) {
//                dtaPassListener?.onPassData("第一个Fragment传递过来的数据")
//            }
            mViewModel.updateContent("FragmentA")
        }
    }

}