package com.sopt.transformer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.eftimoff.viewpagertransformers.DrawFromBackTransformer
import com.eftimoff.viewpagertransformers.ZoomOutSlideTransformer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBanner()
    }

    private fun initBanner(){

        var banner = BannerAdapter(this)
        banner.apply {
            onAddItem(BannerData(R.drawable.bird))
            onAddItem(BannerData(R.drawable.eye))
            onAddItem(BannerData(R.drawable.lion))
        }

        var banner2 = ViewPagerAdapter(this)
        banner2.img_resource = listOf(R.drawable.bird, R.drawable.eye, R.drawable.lion)

        main_vp.adapter = banner2
        main_vp.clipToPadding = false
        main_vp.clipChildren = false
        main_vp.offscreenPageLimit = 3
//        main_vp.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

//        var com = CompositePageTransformer()
//        com.addTransformer(MarginPageTransformer(30))
//        com.addTransformer(SampleTransformer())
        main_vp.setPageTransformer(true,SampleTransformer())

        val dpValue = 30
        val d = resources.displayMetrics.density
        val margin = dpValue*d.toInt()

        main_vp.setPadding(margin,0,margin,0)
        main_vp.pageMargin = margin/2

    }
}