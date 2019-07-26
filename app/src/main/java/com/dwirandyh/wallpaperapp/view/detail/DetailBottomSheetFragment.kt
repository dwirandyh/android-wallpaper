package com.dwirandyh.wallpaperapp.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.dwirandyh.wallpaperapp.R
import com.dwirandyh.wallpaperapp.adapter.WallpaperAdapter
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper
import com.dwirandyh.wallpaperapp.databinding.FragmentBottomSheetDetailBinding
import com.dwirandyh.wallpaperapp.utils.GridSpacingItemDecoration
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DetailBottomSheetFragment(
    private val wallpaper: Wallpaper,
    private val similarWallpaper: LiveData<PagedList<Wallpaper>>?
) : BottomSheetDialogFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentBottomSheetDetailBinding.inflate(inflater)
        binding.wallpaper = wallpaper

        val layoutManager = GridLayoutManager(context, 3)
        binding.rvWallpaper.layoutManager = layoutManager
        binding.rvWallpaper.addItemDecoration(GridSpacingItemDecoration(3))

        val wallpaperPagedAdapter = WallpaperAdapter()
        similarWallpaper?.observe(this, Observer {
            binding.rvWallpaper.adapter = wallpaperPagedAdapter
            wallpaperPagedAdapter.submitList(it)
        })

        return binding.root
    }
}