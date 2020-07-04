package com.sopt.transformer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BannerAdapter (val context : Context) : RecyclerView.Adapter<BannerVH>(){

    var data = arrayListOf<BannerData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerVH {
        val view = LayoutInflater.from(context).inflate(R.layout.vp_item,parent,false)
        return BannerVH(view)

    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: BannerVH, position: Int) {
        holder.onBind(data[position])
    }

    fun onAddItem(item : BannerData){
        data.add(item)
    }

}