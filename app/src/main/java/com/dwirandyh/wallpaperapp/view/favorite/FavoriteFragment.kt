package com.dwirandyh.wallpaperapp.view.favorite

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager

import com.dwirandyh.wallpaperapp.R
import com.dwirandyh.wallpaperapp.adapter.FavoriteAdapter
import com.dwirandyh.wallpaperapp.adapter.WallpaperAdapter
import com.dwirandyh.wallpaperapp.utils.GridSpacingItemDecoration
import com.dwirandyh.wallpaperapp.view.home.category.CategoryListViewModelFactory
import kotlinx.android.synthetic.main.fragment_latest.*
import kotlinx.android.synthetic.main.fragment_latest.layoutLoading
import kotlinx.android.synthetic.main.fragment_latest.rvWallpaper
import kotlinx.android.synthetic.main.fragment_latest.swipeUpRefresh
import kotlinx.android.synthetic.main.fragment_popular.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class FavoriteFragment : Fragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: FavoriteViewModelFactory by instance()
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FavoriteViewModel::class.java)
        viewModel.getFavorites()

        swipeUpRefresh.setOnRefreshListener {
            viewModel.getFavorites()
            showWallpaper()
        }

        initRecyclerView()
        showWallpaper()
    }

    private fun initRecyclerView() {
        val layoutManager = GridLayoutManager(context, 3)

        rvWallpaper.layoutManager = layoutManager
        rvWallpaper.addItemDecoration(GridSpacingItemDecoration(3))

        showWallpaper()
    }

    private fun showWallpaper(){
        val wallpaperPagedAdapter = FavoriteAdapter()
        viewModel.pagedListFavorites.observe(this, Observer {
            rvWallpaper.adapter = wallpaperPagedAdapter
            wallpaperPagedAdapter.submitList(it)
            swipeUpRefresh.isRefreshing = false
            layoutLoading.visibility = View.INVISIBLE
        })
    }
}
