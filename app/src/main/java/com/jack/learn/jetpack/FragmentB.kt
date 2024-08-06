package com.jack.learn.jetpack

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.jack.learn.R
import kotlinx.coroutines.launch

class FragmentB: Fragment() {

    companion object {
        fun getInstance():FragmentB {
            return FragmentB()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_b, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(CommonViewModel::class.java)
        mViewModel.commonData.observe(viewLifecycleOwner) {
            Log.d("wangie","FragmentB: "+it)
        }

        lifecycleScope.launch { // 不推荐使用

        }
        viewLifecycleOwner.lifecycleScope.launch { // 推荐使用

        }
    }

    fun receiveData(data: String) {
        Log.d("wangie",data)
    }


}