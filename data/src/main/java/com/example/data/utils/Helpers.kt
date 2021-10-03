package com.example.data.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

fun getMillisFromStringDate(date: String, dateFormat: String): Long {
    return SimpleDateFormat(dateFormat).let { sdf ->
        sdf.parse(date).time
    }
}