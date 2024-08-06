package com.jack.learn.view.recycler

interface OnPagerListener {

    /**
     * 初始化完成
     */
    fun onInitComplete()

    /**
     * 释放的监听
     * @param isNext                    是否下一个，true表示下一个，false表示上一个
     * @param position                  索引
     */
    fun onPageRelease(isNext: Boolean, position: Int)

    /***
     * 选中的监听以及判断是否滑动到底部
     * @param position                  索引
     * @param isBottom                  是否到了底部
     */
    fun onPageSelected(position: Int, isBottom: Boolean)
}