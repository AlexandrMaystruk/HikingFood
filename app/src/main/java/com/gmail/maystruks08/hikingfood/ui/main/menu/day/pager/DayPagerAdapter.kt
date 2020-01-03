package com.gmail.maystruks08.hikingfood.ui.main.menu.day.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gmail.maystruks08.hikingfood.ui.main.menu.day.DayFragment
import com.gmail.maystruks08.hikingfood.ui.viewmodels.DayView

class DayPagerAdapter(fm: FragmentManager, private val days: List<DayView>) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragments = mutableListOf<DayFragment>().apply {
        days.forEach { this.add(DayFragment.getInstance(it)) }
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}
