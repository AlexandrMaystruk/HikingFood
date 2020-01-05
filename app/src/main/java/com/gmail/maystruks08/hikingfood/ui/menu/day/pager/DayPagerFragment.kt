package com.gmail.maystruks08.hikingfood.ui.menu.day.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.gmail.maystruks08.hikingfood.*
import com.gmail.maystruks08.hikingfood.core.base.BaseFragment
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.DayView
import com.gmail.maystruks08.hikingfood.utils.extensions.toArrayList
import kotlinx.android.synthetic.main.fragment_day_tabs.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class DayPagerFragment : BaseFragment(){

    @Inject
    lateinit var router: Router

    private lateinit var dayFragmentPagerAdapter: DayFragmentPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        App.dayComponent?.inject(this)
        return inflater.inflate(R.layout.fragment_day_tabs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.run {
            val list = getParcelableArrayList<DayView>(DAYS) as List<DayView>
            val position = getInt(SELECTED_DAY_NUMBER)
            setupViewPager(list, position)
        }
    }

    override fun builder(): FragmentToolbar =
        FragmentToolbar.Builder()
            .withId(R.id.toolbar)
            .withTitle(R.string.day)
            .withNavigationIcon(R.drawable.ic_arrow_back) { router.exit() }
            .build()

    override fun initViews() {}

    private fun setupViewPager(days: List<DayView>, selectedPosition: Int) {
        dayFragmentPagerAdapter = DayFragmentPagerAdapter(childFragmentManager, days)
        dayViewPager?.adapter = dayFragmentPagerAdapter
        dayViewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                this@DayPagerFragment.toolbarManager?.changeToolbarTitle("День ${days[position].number}")
            }
        })
        dayViewPager?.currentItem = selectedPosition
        this@DayPagerFragment.toolbarManager?.changeToolbarTitle("День ${days[selectedPosition].number}")
    }

    override fun onDestroyView() {
        dayViewPager.adapter = null
        App.clearDayComponent()
        super.onDestroyView()
    }

    companion object {

        private const val DAYS = "DAYS"

        private const val SELECTED_DAY_NUMBER = "SELECTED_DAY_NUMBER"

        fun getInstance(days: List<DayView>, position: Int) =
            DayPagerFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(DAYS, days.toArrayList())
                    putInt(SELECTED_DAY_NUMBER, position)
                }
            }
    }
}

