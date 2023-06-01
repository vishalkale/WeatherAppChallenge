package com.akash.weatherapp.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

object GlideUtils {
    fun setWeatherIcon(image: ImageView, url: String) {
        Glide.with(image).load(url)
            .thumbnail(0.5f)
            .into(image)
    }
}