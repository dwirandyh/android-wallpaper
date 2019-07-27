package com.dwirandyh.wallpaperapp

import android.app.Application
import com.dwirandyh.wallpaperapp.data.local.LocalDataSource
import com.dwirandyh.wallpaperapp.data.local.LocalDataSourceImpl
import com.dwirandyh.wallpaperapp.data.local.WallpaperDatabase
import com.dwirandyh.wallpaperapp.data.remote.RemoteDataSource
import com.dwirandyh.wallpaperapp.data.remote.RemoteDataSourceImpl
import com.dwirandyh.wallpaperapp.data.remote.RetrofitInstance
import com.dwirandyh.wallpaperapp.data.repository.*
import com.dwirandyh.wallpaperapp.view.category.CategoryWallpaperDataSource
import com.dwirandyh.wallpaperapp.view.category.CategoryWallpaperDataSourceFactory
import com.dwirandyh.wallpaperapp.view.category.CategoryWallpaperViewModelFactory
import com.dwirandyh.wallpaperapp.view.detail.DetailActivityViewModelFactory
import com.dwirandyh.wallpaperapp.view.favorite.FavoriteViewModelFactory
import com.dwirandyh.wallpaperapp.view.home.category.CategoryListDataSource
import com.dwirandyh.wallpaperapp.view.home.category.CategoryListDataSourceFactory
import com.dwirandyh.wallpaperapp.view.home.category.CategoryListViewModel
import com.dwirandyh.wallpaperapp.view.home.category.CategoryListViewModelFactory
import com.dwirandyh.wallpaperapp.view.home.latest.LatestFragmentViewModelFactory
import com.dwirandyh.wallpaperapp.view.home.latest.LatestWallpaperDataSource
import com.dwirandyh.wallpaperapp.view.home.latest.LatestWallpaperDataSourceFactory
import com.dwirandyh.wallpaperapp.view.home.popular.PopularFragmentViewModelFactory
import com.dwirandyh.wallpaperapp.view.home.popular.PopularWallpaperDataSource
import com.dwirandyh.wallpaperapp.view.home.popular.PopularWallpaperDataSourceFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*

class MyApp : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@MyApp))

        bind() from singleton { WallpaperDatabase(instance()) }
        bind() from singleton { instance<WallpaperDatabase>().wallpaperDao() }
        bind() from singleton { instance<WallpaperDatabase>().favoriteDao() }

        bind() from singleton { RetrofitInstance() }

        bind<LocalDataSource>() with  provider { LocalDataSourceImpl(instance()) }
        bind<RemoteDataSource>() with provider { RemoteDataSourceImpl(instance(), instance()) }

        bind<CategoryRepository>() with singleton { CategoryRepositoryImpl(instance()) }
        bind<WallpaperRepository>() with singleton { WallpaperRepositoryImpl(instance(), instance()) }
        bind<FavoriteRepository>() with singleton { FavoriteRepositoryImpl(instance()) }

        bind() from provider { LatestWallpaperDataSource(instance()) }
        bind() from provider { LatestWallpaperDataSourceFactory(instance()) }
        bind() from provider { LatestFragmentViewModelFactory(instance()) }
        bind() from provider { PopularWallpaperDataSource(instance()) }
        bind() from provider { PopularWallpaperDataSourceFactory(instance()) }
        bind() from provider { PopularFragmentViewModelFactory(instance()) }
        bind() from provider { CategoryListDataSource(instance(), instance()) }
        bind() from provider { CategoryListDataSourceFactory(instance()) }
        bind() from provider { CategoryListViewModelFactory(instance()) }

        bind() from provider { CategoryWallpaperDataSource(instance()) }
        bind() from provider { CategoryWallpaperDataSourceFactory(instance()) }
        bind() from factory { categoryId: Int -> CategoryWallpaperViewModelFactory(categoryId, instance()) }

        bind() from provider { DetailActivityViewModelFactory(instance(), instance(), instance(), instance()) }

        bind() from provider { FavoriteViewModelFactory(instance()) }
    }
}