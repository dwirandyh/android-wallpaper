package com.dwirandyh.wallpaperapp.view.category

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.dwirandyh.wallpaperapp.R
import com.dwirandyh.wallpaperapp.adapter.ViewPagerAdapter
import com.dwirandyh.wallpaperapp.adapter.WallpaperAdapter
import com.dwirandyh.wallpaperapp.utils.GridSpacingItemDecoration
import com.dwirandyh.wallpaperapp.view.home.MainActivity
import com.dwirandyh.wallpaperapp.view.home.popular.PopularFragmentViewModel
import com.dwirandyh.wallpaperapp.view.home.popular.PopularFragmentViewModelFactory
import kotlinx.android.synthetic.main.activity_category_wallpaper.*
import kotlinx.android.synthetic.main.activity_category_wallpaper.toolbar
import kotlinx.android.synthetic.main.activity_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory
import org.kodein.di.generic.instance
import java.lang.ClassCastException
import java.lang.Exception

class CategoryWallpaperActivity : AppCompatActivity(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory:
            ((Int) -> CategoryWallpaperViewModelFactory)  by factory()
    private lateinit var viewModel: CategoryWallpaperViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_wallpaper)


        val categoryId = intent.getIntExtra("categoryId", 0)
        val categoryName = intent.getStringExtra("categoryName")


        viewModel = ViewModelProviders.of(this, viewModelFactory(categoryId))
            .get(CategoryWallpaperViewModel::class.java)
        viewModel.initPaging()

        toolbar.title = "Category"
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        categoryName?.let {
            toolbar.title = it
        }

        swipeUpRefresh.setOnRefreshListener {
            viewModel.initPaging()
            showWallpaper()
        }

        initRecyclerView()
    }



    private fun initRecyclerView() {
        val layoutManager = GridLayoutManager(this, 3)

        rvWallpaper.layoutManager = layoutManager
        rvWallpaper.addItemDecoration(GridSpacingItemDecoration(3))

        showWallpaper()
    }

    private fun showWallpaper() {
        val wallpaperPagedAdapter = WallpaperAdapter()
        viewModel.getCategoryWallpaper()?.observe(this, Observer {
            rvWallpaper.adapter = wallpaperPagedAdapter
            wallpaperPagedAdapter.submitList(it)
            swipeUpRefresh.isRefreshing = false
            layoutLoading.visibility = View.INVISIBLE
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null && item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
