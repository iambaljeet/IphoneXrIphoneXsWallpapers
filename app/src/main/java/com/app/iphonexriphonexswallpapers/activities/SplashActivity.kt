package com.app.iphonexriphonexswallpapers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.iphonexriphonexswallpapers.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = WallpaperActivity.newIntent(this)
        startActivity(intent)
    }
}