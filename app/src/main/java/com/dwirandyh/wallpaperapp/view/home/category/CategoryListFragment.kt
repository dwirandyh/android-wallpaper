package com.dwirandyh.wallpaperapp.view.home.category

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwirandyh.wallpaperapp.R
import com.dwirandyh.wallpaperapp.adapter.CategoryAdapter
import com.dwirandyh.wallpaperapp.data.local.entity.Category
import com.dwirandyh.wallpaperapp.view.category.CategoryWallpaperActivity
import kotlinx.android.synthetic.main.fragment_category_list.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CategoryListFragment : Fragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: CategoryListViewModelFactory by instance()
    private lateinit var viewModel: CategoryListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CategoryListViewModel::class.java)
        viewModel.initPaging()


        swipeUpRefresh.setOnRefreshListener {
            viewModel.initPaging()
            showCategories()
        }

        initRecylcerView()
    }

    private fun initRecylcerView() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        rvCategory.layoutManager = layoutManager

        showCategories()
    }

    private fun showCategories() {
        val categoryAdapter = CategoryAdapter()
        categoryAdapter.setOnClickListener { view, category ->
            showCategoryWallpaper(view, category)
        }
        viewModel.getCategories()?.observe(this, Observer {
            rvCategory.adapter = categoryAdapter
            categoryAdapter.submitList(it)
            swipeUpRefresh.isRefreshing = false
            layoutLoading.visibility = View.INVISIBLE
        })
    }

    private fun showCategoryWallpaper(view: View, category: Category) {

        val intent = Intent(context, CategoryWallpaperActivity::class.java)
        intent.putExtra("categoryId", category.id)
        intent.putExtra("categoryName", category.name)
        startActivity(intent)
    }
}
