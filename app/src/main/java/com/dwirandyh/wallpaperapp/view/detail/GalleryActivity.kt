package com.dwirandyh.wallpaperapp.view.detail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.dwirandyh.wallpaperapp.R
import com.dwirandyh.wallpaperapp.data.local.entity.Wallpaper
import com.dwirandyh.wallpaperapp.databinding.ActivityGalleryBinding
import com.dwirandyh.wallpaperapp.utils.Constant
import com.dwirandyh.wallpaperapp.utils.IntentUtils
import com.dwirandyh.wallpaperapp.utils.ImageUtils
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_gallery.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class GalleryActivity : AppCompatActivity() {

    lateinit var wallpaper: Wallpaper
    lateinit var binding: ActivityGalleryBinding

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        wallpaper = intent.getParcelableExtra(Constant.EXTRA_WALLPEPR)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gallery)
        binding.wallpaper = wallpaper
        binding.handler = ClickHandler(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        btnMore.title = "More from `Category Name`"
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
        fun btnShareClick(view: View) {
            Dexter.withActivity(this@GalleryActivity)
                .withPermissions(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        report?.let {
                            val disposable = Observable.fromCallable {
                                val imageUrl = Constant.BASE_IMAGE_URL + wallpaper.fileName
                                return@fromCallable ImageUtils.saveIntoStorage(imageUrl)
                            }
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe {
                                    val message = "Download ${context.getString(R.string.app_name)} in Play Store https://play.google.com/store/apps/details?id=${context.packageName}"
                                    IntentUtils.shareImage(context, it, message)
                                }

                            compositeDisposable.add(disposable)
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        Log.e("GalleryActivity", permissions.toString())
                    }
                }).check()
        }

        fun btnMoreClick(view: View) {

        }

        fun btnSetAsClick(view: View) {
            Dexter.withActivity(this@GalleryActivity)
                .withPermissions(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        report?.let {
                            if (report.areAllPermissionsGranted()) {
                                // Set As
                                val disposable = Observable.fromCallable {
                                    val imageUrl = Constant.BASE_IMAGE_URL + wallpaper.fileName
                                    return@fromCallable ImageUtils.saveIntoStorage(imageUrl)
                                }.subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe {
                                        IntentUtils.setAs(context, it)
                                    }

                                compositeDisposable.add(disposable)
                            }
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                }).check()
        }

        fun btnSaveClick(view: View) {
            Dexter.withActivity(this@GalleryActivity)
                .withPermissions(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        report?.let {
                            if (report.areAllPermissionsGranted()) {
                                // Set As
                                val disposable = Observable.fromCallable {
                                    val imageUrl = Constant.BASE_IMAGE_URL + wallpaper.fileName
                                    return@fromCallable ImageUtils.saveIntoStorage(imageUrl)
                                }.subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe {
                                        Toast.makeText(context, "Wallpaper saved successfully", Toast.LENGTH_SHORT).show()
                                    }

                                compositeDisposable.add(disposable)
                            }
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                }).check()
        }

        fun btnInfo(view: View) {

        }

        fun btnFavorite(view: View) {

        }
    }
}
