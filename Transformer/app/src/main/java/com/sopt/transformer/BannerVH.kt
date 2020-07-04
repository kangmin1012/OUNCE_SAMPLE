package com.sopt.transformer

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class BannerVH (view : View) : RecyclerView.ViewHolder(view){

    var img : ImageView = view.findViewById(R.id.img_banner)
    fun onBind(data : BannerData){

        img.setImageResource(data.img)
    }
}