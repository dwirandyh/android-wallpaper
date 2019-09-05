package com.dwirandyh.wallpaperapp.utils

import android.content.Context
import android.util.Log
import androidx.print.PrintHelper
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds

class AdsUtils(val context: Context) {

    private lateinit var mInterstitialAd: InterstitialAd
    private val TAG = "AdsUtils"

    init {
        MobileAds.initialize(context) {
            Log.i(TAG, "Initialize Mobile Ads SDK")
        }

        initInterstitial()
    }

    private fun initInterstitial(){
        mInterstitialAd = InterstitialAd(context)
        mInterstitialAd.adUnitId = Constant.INTERSTITIAL_ID
        mInterstitialAd.adListener = object: AdListener(){
            override fun onAdFailedToLoad(p0: Int) {
                super.onAdFailedToLoad(p0)
                initInterstitial()
            }

            override fun onAdOpened() {
                super.onAdOpened()
                initInterstitial()
            }
        }

        mInterstitialAd.loadAd(AdRequest.Builder().build())
    }

    fun showInterstitialSplash(finishCallback:() -> Unit){
        mInterstitialAd.adListener = object: AdListener(){
            override fun onAdLoaded() {
                mInterstitialAd.show()
            }

            override fun onAdFailedToLoad(p0: Int) {
                super.onAdFailedToLoad(p0)
                initInterstitial()
                finishCallback()
            }

            override fun onAdOpened() {
                super.onAdOpened()
                initInterstitial()
                finishCallback()
            }
        }
    }
}