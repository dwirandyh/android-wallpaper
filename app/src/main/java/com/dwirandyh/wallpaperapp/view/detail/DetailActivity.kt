package com.dwirandyh.wallpaperapp.view.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dwirandyh.wallpaperapp.R
import com.dwirandyh.wallpaperapp.data.local.entity.Category
import com.dwirandyh.wallpaperapp.data.local.entity.Favorite
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper
import com.dwirandyh.wallpaperapp.databinding.ActivityDetailBinding
import com.dwirandyh.wallpaperapp.utils.app.AppUtils
import com.dwirandyh.wallpaperapp.utils.Constant
import com.dwirandyh.wallpaperapp.utils.app.IntentUtils
import com.dwirandyh.wallpaperapp.utils.app.ImageUtils
import com.dwirandyh.wallpaperapp.view.category.CategoryWallpaperActivity
import kotlinx.android.synthetic.main.activity_detail.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance


class DetailActivity : AppCompatActivity(), KodeinAware {

    private val TAG: String = "DetailActivity"

    override val kodein by closestKodein()
    private val viewModelFactory: DetailActivityViewModelFactory by instance()

    var category: Category? = null
    lateinit var wallpaper: Wallpaper
    lateinit var binding: ActivityDetailBinding

    lateinit var viewModel: DetailActivityViewModel

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        wallpaper = intent.getParcelableExtra(Constant.EXTRA_WALLPAPER)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.wallpaper = wallpaper
        binding.handler = ClickHandler(this)

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(DetailActivityViewModel::class.java)
        viewModel.getCategory(wallpaper.categoryId).observe(this, Observer {
            category = it
            getSimilarWallpaper(it.id)

            btnMore.title = "More from `${category?.name}`"
        })

        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        checkFavorite()
    }

    private fun checkFavorite() {
        viewModel.checkFavorite(wallpaper.id)
        viewModel.isFavorite.observe(this, Observer {
            if (it) {
                btnFavorite.setImageResource(R.drawable.ic_favorite)
            } else {
                btnFavorite.setImageResource(R.drawable.ic_favorite_border)
            }
        })
    }

    private fun getSimilarWallpaper(categoryId: Int) {
        viewModel.getSimilarWallpaper(categoryId)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null && item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }


    inner class ClickHandler(
        private val context: Context
    ) {
        fun btnMoreClick(view: View) {
            category?.let {
                val intent = Intent(this@DetailActivity, CategoryWallpaperActivity::class.java)
                intent.putExtra("categoryId", it.id)
                intent.putExtra("categoryName", it.name)
                startActivity(intent)
            }
        }

        fun btnShareClick(view: View) {
            AppUtils.writeExternalPermission(this@DetailActivity) {
                val disposable = Observable.fromCallable {
                    val imageUrl = Constant.BASE_IMAGE_URL + wallpaper.fileName
                    return@fromCallable ImageUtils.saveIntoStorage(imageUrl)
                }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            val message =
                                "Download ${context.getString(R.string.app_name)} in Play Store https://play.google.com/store/apps/details?id=${context.packageName}"
                            IntentUtils.shareImage(context, it, message)
                        },
                        {
                            Log.e(TAG, it.message)
                        }
                    )

                compositeDisposable.add(disposable)
            }
        }

        fun btnSetAsClick(view: View) {
            AppUtils.writeExternalPermission(this@DetailActivity) {
                // Set As
                val disposable = Observable.fromCallable {
                    val imageUrl = Constant.BASE_IMAGE_URL + wallpaper.fileName
                    return@fromCallable ImageUtils.saveIntoStorage(imageUrl)
                }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            IntentUtils.setAs(context, it)
                        },
                        {
                            Log.e(TAG, it.message)
                        }
                    )

                compositeDisposable.add(disposable)
            }
        }

        fun btnSaveClick(view: View) {
            AppUtils.writeExternalPermission(this@DetailActivity) {
                val disposable = Observable.fromCallable {
                    val imageUrl = Constant.BASE_IMAGE_URL + wallpaper.fileName
                    return@fromCallable ImageUtils.saveIntoStorage(imageUrl)
                }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            Toast.makeText(
                                context,
                                "Wallpaper saved successfully",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        },
                        {
                            Log.e(TAG, it.message)
                        }
                    )

                compositeDisposable.add(disposable)
            }
        }

        fun btnInfo(view: View) {
            val infoBottomSheet =
                DetailBottomSheetFragment(wallpaper, viewModel.similaWallpaperLiveData)
            infoBottomSheet.show(supportFragmentManager, infoBottomSheet.tag)
        }

        fun btnFavorite(view: View) {
            viewModel.isFavorite.value?.let {
                val favorite = Favorite(
                    null,
                    wallpaper.id,
                    wallpaper.categoryId,
                    wallpaper.createdAt,
                    wallpaper.downloads,
                    wallpaper.fileName,
                    wallpaper.fileSize,
                    wallpaper.resolution,
                    wallpaper.views
                )

                if (it) {
                    viewModel.removeFavorite(favorite)
                } else {
                    viewModel.addFavorite(favorite)
                }
            }
        }
    }
}
