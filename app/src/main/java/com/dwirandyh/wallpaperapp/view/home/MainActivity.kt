package com.dwirandyh.wallpaperapp.view.home

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dwirandyh.wallpaperapp.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import com.dwirandyh.wallpaperapp.utils.app.IntentUtils


class MainActivity : AppCompatActivity() {


    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        openSplashScreen()

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toolbar.title = "Wallpaper Apps"

        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_icon)

        // setup action bar
        val navController = findNavController(R.id.nav_host_fragment)
        // handle action button still use menu icon instead back icon
        val topLevelDestinations = setOf(R.id.mainFragment, R.id.aboutFragment)
        appBarConfiguration =
            AppBarConfiguration.Builder(topLevelDestinations).setDrawerLayout(drawer_layout).build()

        // Set up actionbar
        setupActionBarWithNavController(navController, appBarConfiguration)

        navigationView.setupWithNavController(navController)
        setupCustomNavigationClick(navigationView)
    }

    private fun setupCustomNavigationClick(navigationView: NavigationView) {
        val rateItem = navigationView.menu.findItem(R.id.nav_rate_app)
        rateItem.setOnMenuItemClickListener {
            openRateApp()
            true
        }

        val feedbackItem = navigationView.menu.findItem(R.id.nav_feedback)
        feedbackItem.setOnMenuItemClickListener {
            sendFeedback()
            true
        }

        val shareItem = navigationView.menu.findItem(R.id.nav_share_app)
        shareItem.setOnMenuItemClickListener {
            shareApp()
            true
        }

        val disclaimerItem = navigationView.menu.findItem(R.id.nav_disclaimer)
        disclaimerItem.setOnMenuItemClickListener {
            openDisclaimer()
            true
        }
    }

    private fun sendFeedback() {
        IntentUtils.sendEmail(
            this,
            "Feedback for ${getString(R.string.app_name)}",
            "Send feedback via"
        )
    }

    private fun openDisclaimer() {
        AlertDialog.Builder(this)
            .setTitle("Disclaimer")
            .setMessage(getString(R.string.disclaimer_text))
            .show()
    }

    private fun openRateApp() {
        val uri = Uri.parse("market://details?id=$packageName")
        val gotoMarketIntent = Intent(Intent.ACTION_VIEW, uri)

        gotoMarketIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        gotoMarketIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
        gotoMarketIntent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)

        try {
            startActivity(gotoMarketIntent)
        } catch (e: Exception) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
                )
            )
        }
    }

    private fun shareApp() {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(
            Intent.EXTRA_TEXT,
            "${getString(R.string.share_app)}$packageName"
        )
        shareIntent.type ="text/plain"
        startActivity(shareIntent)
    }

    private fun openSplashScreen(){
        val intent = Intent(this, SplashScreenActivity::class.java)
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.nav_host_fragment),
            drawer_layout
        )
    }
}
