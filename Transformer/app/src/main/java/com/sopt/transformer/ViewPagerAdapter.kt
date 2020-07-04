package com.sopt.transformer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter

class ViewPagerAdapter(val context : Context) : PagerAdapter(){
    var img_resource = listOf<Int>()

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        var inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var v = inflater.inflate(R.layout.vp_item,container,false)

        var image = v.findViewById<ImageView>(R.id.img_banner)

        image.setImageResource(img_resource[position])

        container.addView(v)
        return v
    }

    override fun getCount(): Int {
        return img_resource.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view ==  `object`
    }
}