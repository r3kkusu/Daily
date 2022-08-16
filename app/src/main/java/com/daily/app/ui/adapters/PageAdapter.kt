package com.daily.app.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PageAdapter constructor(
    private val fragmentActivity: FragmentActivity,
    private val fragmentList: ArrayList<Fragment>
) : FragmentStateAdapter(fragmentActivity) {

    private var position: Int = 0

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }


}