package com.jack.learn.view.pager2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyFragmentStateAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FirstFragment2.newInstance("11111111","")
            1 -> SecondFragment2.newInstance("222222222","")
            2 -> ThirdFragment2.newInstance("3333333333","")
            3 -> FourFragment2.newInstance("4444444444","")
            else -> FiveFragment2.newInstance("5555555555","")
        }
    }
}