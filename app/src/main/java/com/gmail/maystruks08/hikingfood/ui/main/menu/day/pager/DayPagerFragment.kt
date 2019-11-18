package com.gmail.maystruks08.hikingfood.ui.main.menu.day.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.gmail.maystruks08.hikingfood.*
import com.gmail.maystruks08.hikingfood.core.base.BaseFragment
import com.gmail.maystruks08.hikingfood.ui.viewmodel.DayView
import com.gmail.maystruks08.hikingfood.utils.extensions.toArrayList
import kotlinx.android.synthetic.main.fragment_day_tabs.*
import javax.inject.Inject

class DayPagerFragment : BaseFragment(), DayPagerContract.View {

    @Inject
    lateinit var presenter: DayPagerContract.Presenter

    private lateinit var dayPagerAdapter: DayPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        App.dayComponent?.inject(this)
        return inflater.inflate(R.layout.fragment_day_tabs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.run {
            val list = getParcelableArrayList<DayView>(DAYS) as List<DayView>
            val position = getInt(SELECTED_DAY_NUMBER)
            presenter.bindView(this@DayPagerFragment, list, position)
        }
    }

    override fun builder(): FragmentToolbar {
        return FragmentToolbar.Builder()
            .withId(R.id.toolbar)
            .withTitle( R.string.fragment_create_menu_name)
            .withNavigationIcon(R.drawable.ic_arrow_back) { presenter.onBackClicked() }
            .build()
    }

    override fun initViews() {}

    override fun setupViewPager(days: List<DayView>, selectedPosition: Int) {
        dayPagerAdapter = DayPagerAdapter(childFragmentManager, days)
        dayViewPager?.adapter = dayPagerAdapter
        dayViewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                this@DayPagerFragment.toolbarManager.changeToolbarTitle("День ${days[position].number}")
            }
        })
        dayViewPager?.currentItem = selectedPosition
        this@DayPagerFragment.toolbarManager.changeToolbarTitle("День ${days[selectedPosition].number}")
    }

    override fun onDestroyView() {
        presenter.end()
        dayViewPager.adapter = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        App.clearDayComponent()
        super.onDestroy()
    }

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showError(t: Throwable) {}

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

