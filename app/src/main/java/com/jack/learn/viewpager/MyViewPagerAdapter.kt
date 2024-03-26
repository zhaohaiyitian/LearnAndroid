package com.jack.learn.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MyViewPagerAdapter(fm: FragmentManager,var fragments: List<Fragment>) : FragmentStatePagerAdapter(fm, BEHAVIOR_SET_USER_VISIBLE_HINT) {
    private var mTitles = mutableListOf("tab1","tab2","tab3","tab4","tab5")
    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTitles[position]
    }
}