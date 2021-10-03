package com.example.data.persistance

import android.content.SharedPreferences
import javax.inject.Inject


class AppPreferences @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val MINIMUM_DATE_KEY = "minimum.request.key"
        const val MAXIMUM_DATE_KEY = "minimum.request.app.key"
    }

    var minimumRequestDate: Long
        get() = sharedPreferences.getLong(MINIMUM_DATE_KEY, 0)
        set(value) = sharedPreferences.edit().putLong(MINIMUM_DATE_KEY, value).apply()

    var maximumRequestDate: Long
        get() = sharedPreferences.getLong(MAXIMUM_DATE_KEY, 0)
        set(value) = sharedPreferences.edit().putLong(MAXIMUM_DATE_KEY, value).apply()
}