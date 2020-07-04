package com.sopt.transformer

import android.view.View
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class SampleTransformer : ViewPager.PageTransformer{
    override fun transformPage(page: View, position: Float) {
        val r = 1- abs(position)
        page.scaleY = (0.85f + r * 0.2f)
    }
}