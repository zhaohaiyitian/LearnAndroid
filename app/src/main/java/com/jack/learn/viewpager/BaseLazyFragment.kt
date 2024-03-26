package com.jack.learn.viewpager

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


open abstract class BaseLazyFragment : Fragment() {

    private var rootView: View? = null
    private var isViewCreated = false
    private var isVisibleStateUp = false // 解决重复加载更新和重复暂停更新的问题

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutRes(), container, false)
        }
        initView(rootView)
        isViewCreated = true
        // 解决第一个 Fragment 状态不更新的问题，所以需要我们在 onCreateView 中根据当前 Fragment 可见的时候，手动分发下状态
        if (userVisibleHint) {
            userVisibleHint = true
        }
        return rootView
    }

    abstract fun initView(rootView: View?)
    abstract fun getLayoutRes(): Int

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isViewCreated) {
            if (isVisibleToUser && !isVisibleStateUp) {
                // 当前可见，但是上一次不可见
                dispatchVisibleHint(true)
            } else if (!isVisibleToUser && isVisibleStateUp) {
                // 当前不可见，但是上一次可见
                dispatchVisibleHint(false)
            }
        }
    }

    private fun dispatchVisibleHint(isVisibleToUser: Boolean) {
        isVisibleStateUp = isVisibleToUser
        if (isVisibleToUser) {
            onLoadFragmentStart()
        } else {
            onLoadFragmentStop()
        }
    }

    /**
     * onResume,onStop处理 解决 Fragment 跳转到一个新的 Activity setUserVisibleHint 没有执行的问题
     * 因为它的执行是在 ViewPager 中通过 adapter 调用的时候才执行，也就是我们在二跳的时候并没有触发 adapter 的调用
     * populate -> mAdapter.setPrimaryItem -> setUserVisibleHint 调用的，跟生命周期没有任何关系；
     * 想分发这个状态，就要和生命周期管理起来，那么我们就需要在 Fragment 的 onResume 和 onPasue 增加逻辑处理
     */
    override fun onResume() {
        super.onResume()
        if (userVisibleHint && !isVisibleStateUp) {
            // 当前可见，并且上一次不可见的时候 分发
            dispatchVisibleHint(true)
        }
    }

    override fun onStop() {
        super.onStop()
        if (!userVisibleHint && isVisibleStateUp) {
            dispatchVisibleHint(false)
        }
    }

    abstract fun onLoadFragmentStart()
    abstract fun onLoadFragmentStop()

}