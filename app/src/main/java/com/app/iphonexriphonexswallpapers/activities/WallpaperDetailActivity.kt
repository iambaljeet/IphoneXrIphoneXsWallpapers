package com.app.iphonexriphonexswallpapers.activities

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.app.iphonexriphonexswallpapers.helpers.Constants
import com.app.iphonexriphonexswallpapers.R
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.activity_wallpaper_detail.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class WallpaperDetailActivity: AppCompatActivity() {

    var wallpaperId: Int? = null
    var wallpaperName: String? = null
    var mInterstitialAd_download:InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpaper_detail)
        initAds()
        initWallpaper()
    }

    private fun initAds() {
        val extras = Bundle()
        extras.putString("max_ad_content_rating", "G")

        mInterstitialAd_download = InterstitialAd(this)
        mInterstitialAd_download?.adUnitId = getString(R.string.wallpaper_interstitial)
        mInterstitialAd_download?.loadAd(AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter::class.java, extras).build())

        mInterstitialAd_download?.adListener = object : AdListener() {
            override fun onAdFailedToLoad(errorCode: Int) {
                mInterstitialAd_download?.loadAd(AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter::class.java, extras).build())
            }
            override fun onAdLeftApplication() {
                mInterstitialAd_download?.loadAd(AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter::class.java, extras).build())
            }

            override fun onAdClosed() {
                mInterstitialAd_download?.loadAd(AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter::class.java, extras).build())
            }
        }
    }

    private fun initWallpaper() {
        wallpaperId = intent.getIntExtra(Constants.wallpaper, 0)
        wallpaperName = intent.getStringExtra(Constants.wallpaperName)
        val wallpaper_id = wallpaperId;

        if (wallpaperId != null) {
            if (wallpaper_id != null) {
                wallpaper_imageView.setImageResource(wallpaper_id)
            }
        }

        set_wallpaper.setOnClickListener { v: View? -> setWallpaper() }
    }

    public fun setWallpaper() {
        val wallpaperManager = WallpaperManager.getInstance(this@WallpaperDetailActivity)
        val wallpaper_id = wallpaperId;

        if (wallpaperId != null) {
            if (wallpaper_id != null) {
                async(UI) {
                    val bitmap = bg { getBitmapFromResource(wallpaper_id) }.await()
                    wallpaperManager.setBitmap(bitmap)

                    showInterstitialAd()
                }
            }
        }
    }

    fun showInterstitialAd() {
        if (mInterstitialAd_download?.isLoaded!!) {
            mInterstitialAd_download?.show()
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.")
        }
    }

    fun getBitmapFromResource(wallpaper_id: Int): Bitmap {
        var outputStream: OutputStream;

        val  bitmap: Bitmap = BitmapFactory.decodeResource(resources, wallpaper_id)

        var extStorageDirectory: String = Environment.getExternalStorageDirectory().toString()

        if (extStorageDirectory?.isNullOrBlank()) {
            extStorageDirectory = Environment.getRootDirectory().toString()
        }

        val file = File(extStorageDirectory, wallpaperName)

        try {
            outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }
}