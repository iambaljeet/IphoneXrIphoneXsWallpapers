package com.app.iphonexriphonexswallpapers.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import android.view.View
import com.app.iphonexriphonexswallpapers.R
import com.app.iphonexriphonexswallpapers.adapters.WallpaperAdapter
import com.app.iphonexriphonexswallpapers.models.WallpaperModel
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.activity_wallpaper.*

class WallpaperActivity: AppCompatActivity() {

    var wallpaperList: MutableList<WallpaperModel> = ArrayList<WallpaperModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper)

        initAds()
        initList()
    }
    private fun initAds() {
        val bundle = Bundle()
        bundle.putString("max_ad_content_rating", "G")

        val adRequest = AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter::class.java, bundle).build()
        adView.loadAd(adRequest)

        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                adView.visibility = View.VISIBLE
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                adView.visibility = View.GONE
            }
        }
    }

    private fun initList() {
        var wallpaperModel = WallpaperModel(R.drawable.thumb_xs1, R.drawable.walls_xs1, getString(R.string.xs_wallpaper))
        wallpaperList.add(wallpaperModel)
        wallpaperModel = WallpaperModel(R.drawable.thumb_xs2, R.drawable.walls_xs2, getString(R.string.xs_wallpaper))
        wallpaperList.add(wallpaperModel)
        wallpaperModel = WallpaperModel(R.drawable.thumb_xr1, R.drawable.walls_xr1, getString(R.string.xr_wallpaper))
        wallpaperList.add(wallpaperModel)
        wallpaperModel = WallpaperModel(R.drawable.thumb_xr2, R.drawable.walls_xr2, getString(R.string.xr_wallpaper))
        wallpaperList.add(wallpaperModel)
        wallpaperModel = WallpaperModel(R.drawable.thumb_xr3, R.drawable.walls_xr3, getString(R.string.xr_wallpaper))
        wallpaperList.add(wallpaperModel)
        wallpaperModel = WallpaperModel(R.drawable.thumb_xr4, R.drawable.walls_xr4, getString(R.string.xr_wallpaper))
        wallpaperList.add(wallpaperModel)
        wallpaperModel = WallpaperModel(R.drawable.thumb_xr5, R.drawable.walls_xr5, getString(R.string.xr_wallpaper))
        wallpaperList.add(wallpaperModel)
        wallpaperModel = WallpaperModel(R.drawable.thumb_xr6, R.drawable.walls_xr6, getString(R.string.xr_wallpaper))
        wallpaperList.add(wallpaperModel)

        val wallpaperAdapter = WallpaperAdapter(this, wallpaperList)
        val gridLayoutManager = GridLayoutManager(this, 2)
        wallpaper_recyclerView.layoutManager = gridLayoutManager
        wallpaper_recyclerView.adapter = wallpaperAdapter
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, WallpaperActivity::class.java)
        }
    }
}