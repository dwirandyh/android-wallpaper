package com.dwirandyh.wallpaperapp.view.category

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.android.synthetic.main.fragment_category_wallpaper.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory
import org.kodein.di.generic.instance
import java.lang.ClassCastException
import java.lang.Exception

class CategoryWallpaperFragment : Fragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory:
            ((Int) -> CategoryWallpaperViewModelFactory)  by factory()
    private lateinit var viewModel: CategoryWallpaperViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category_wallpaper, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val safeArgs = arguments?.let {
            CategoryWallpaperFragmentArgs.fromBundle(it)
        }

        val categoryId = safeArgs?.categoryId
        val categoryName = safeArgs?.categoryName

        categoryName?.let {
            changeTitle(categoryName)
        }

        viewModel = ViewModelProviders.of(this, categoryId?.let { viewModelFactory(it) })
            .get(CategoryWallpaperViewModel::class.java)
        viewModel.initPaging()

        swipeUpRefresh.setOnRefreshListener {
            viewModel.initPaging()
            showWallpaper()
        }

        initRecyclerView()
    }

    private fun changeTitle(title: String){
        try{
            (activity as MainActivity).supportActionBar?.title = title
        }catch (e: Exception){
            Log.e("CategoryWallpaper", e.message)
        }
    }

    private fun initRecyclerView() {
        val layoutManager = GridLayoutManager(context, 3)

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


    override fun onDestroy() {
        super.onDestroy()
        context?.getString(R.string.app_name)?.let { changeTitle(it) }
    }
}
