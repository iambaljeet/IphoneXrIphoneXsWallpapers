package com.app.iphonexriphonexswallpapers.adapters

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.iphonexriphonexswallpapers.R
import com.app.iphonexriphonexswallpapers.models.WallpaperModel
import com.app.iphonexriphonexswallpapers.activities.WallpaperDetailActivity
import com.app.iphonexriphonexswallpapers.helpers.Constants
import kotlinx.android.synthetic.main.wallpaper_list_item.view.*

class WallpaperAdapter(val context: Context, val wallpaperList: MutableList<WallpaperModel>): RecyclerView.Adapter<WallpaperAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.wallpaper_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return wallpaperList?.size ?: 0
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, position: Int) {
        myViewHolder.wallpaperImageView.setImageResource(wallpaperList.get(position).wallpaperThumbId)

        myViewHolder.wallpaperImageView.setOnClickListener { v -> openWallpaperDetail(wallpaperList.get(position)) }
    }

    private fun openWallpaperDetail(wallpaperModel: WallpaperModel) {
        val intent = Intent(context, WallpaperDetailActivity::class.java)
        intent.putExtra(Constants.wallpaper, wallpaperModel.wallpaperId)
        intent.putExtra(Constants.wallpaperName, wallpaperModel.wallpaperName)
        context.startActivity(intent)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wallpaperImageView = itemView.wallpaper_imageView
    }
}