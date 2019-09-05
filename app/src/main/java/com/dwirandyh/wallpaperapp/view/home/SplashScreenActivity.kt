package com.dwirandyh.wallpaperapp.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.dwirandyh.wallpaperapp.R
import com.dwirandyh.wallpaperapp.utils.AdsUtils
import com.dwirandyh.wallpaperapp.utils.Constant
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.factory
import org.kodein.di.generic.instance
import kotlin.math.min

class SplashScreenActivity : AppCompatActivity(), KodeinAware {

    override val kodein by closestKodein()
    private val adsUtils: AdsUtils by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        adsUtils.showInterstitialSplash{
            finish()
        }
    }
}
