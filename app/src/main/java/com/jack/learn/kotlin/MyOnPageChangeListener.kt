package com.jack.learn.kotlin

import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener


private typealias pageSelected = (p0: Int)->Unit
class MyOnPageChangeListener: OnPageChangeListener {

    private var pageSelected:((p0: Int)->Unit)? = null
    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
    }

    override fun onPageSelected(p0: Int) {
        pageSelected?.invoke(p0)
    }

    override fun onPageScrollStateChanged(p0: Int) {
    }

    fun pageSelected(pageSelected: pageSelected) {
        this.pageSelected = pageSelected
    }
}



fun ViewPager.pageChange(myOnPageChangeListener: MyOnPageChangeListener.()->Unit) {
    this.addOnPageChangeListener(MyOnPageChangeListener().apply(myOnPageChangeListener))
}