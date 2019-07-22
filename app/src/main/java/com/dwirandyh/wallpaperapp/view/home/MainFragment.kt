package com.dwirandyh.wallpaperapp.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.dwirandyh.wallpaperapp.R
import com.dwirandyh.wallpaperapp.adapter.ViewPagerAdapter
import com.dwirandyh.wallpaperapp.view.home.category.CategoryListFragment
import com.dwirandyh.wallpaperapp.view.home.latest.LatestFragment
import com.dwirandyh.wallpaperapp.view.home.popular.PopularFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val viewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        viewPagerAdapter.addFragment(LatestFragment(), "Latest")
        viewPagerAdapter.addFragment(CategoryListFragment(), "Category")
        viewPagerAdapter.addFragment(PopularFragment(), "Popular")

        // Prevent viewpager recreate fragment when swipe the page
        viewPager.offscreenPageLimit = 3
        viewPager.adapter = viewPagerAdapter
    }
}
