package com.akash.weatherapp.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    @SuppressLint("SimpleDateFormat")
    fun getCurrentDateTime(dateFormat: String): String =
        SimpleDateFormat(dateFormat).format(Date())
}