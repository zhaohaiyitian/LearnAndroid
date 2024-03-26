package com.jack.learn.viewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.jack.learn.R

/**
 * 参考：https://juejin.cn/post/7326985574463438886
 */
class ViewPagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)
        val fragments = mutableListOf<Fragment>()
         val firstFragment = FirstFragment.newInstance("first","first")
         val secondFragment = SecondFragment.newInstance("second","second")
         val thirdFragment = ThirdFragment.newInstance("third","third")
         val fourFragment = FourFragment.newInstance("four","four")
         val fiveFragment = FiveFragment.newInstance("five","five")
        fragments.add(firstFragment)
        fragments.add(secondFragment)
        fragments.add(thirdFragment)
        fragments.add(fourFragment)
        fragments.add(fiveFragment)
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        val pagerAdapter = MyViewPagerAdapter(supportFragmentManager,fragments)
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)

    }
}