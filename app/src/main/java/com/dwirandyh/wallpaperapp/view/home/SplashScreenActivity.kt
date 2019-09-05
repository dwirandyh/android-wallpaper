package com.dwirandyh.wallpaperapp.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.dwirandyh.wallpaperapp.R

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            finish()
        }, 3000)
    }
}
