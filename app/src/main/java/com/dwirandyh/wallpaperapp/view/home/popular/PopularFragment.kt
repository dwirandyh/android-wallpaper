package com.dwirandyh.wallpaperapp.view.home.popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.dwirandyh.wallpaperapp.R
import com.dwirandyh.wallpaperapp.adapter.WallpaperAdapter
import com.dwirandyh.wallpaperapp.utils.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_popular.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class PopularFragment : Fragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: PopularFragmentViewModelFactory by instance()
    private lateinit var viewModel: PopularFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_popular, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PopularFragmentViewModel::class.java)
        viewModel.initPaging()

        swipeUpRefresh.setOnRefreshListener {
            viewModel.initPaging()
            showWallpaper()
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val layoutManager = GridLayoutManager(context, 3)

        rvWallpaper.layoutManager = layoutManager
        rvWallpaper.addItemDecoration(GridSpacingItemDecoration(3))

        showWallpaper()
    }

    private fun showWallpaper(){
        val wallpaperPagedAdapter = WallpaperAdapter()
        viewModel.getPopularWallpaper()?.observe(this, Observer {
            rvWallpaper.adapter = wallpaperPagedAdapter
            wallpaperPagedAdapter.submitList(it)
            swipeUpRefresh.isRefreshing = false
            layoutLoading.visibility = View.INVISIBLE
        })
    }
}
