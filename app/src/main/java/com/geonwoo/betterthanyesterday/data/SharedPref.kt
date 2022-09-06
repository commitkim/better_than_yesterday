package com.geonwoo.betterthanyesterday.data

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPref @Inject constructor(
    @ApplicationContext context: Context
) {
    private val sharedPref: SharedPreferences by lazy {
        context.getSharedPreferences(
            PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )
    }

    fun setSharedPreferences(key: String, value: String) {
        val edit = sharedPref.edit()
        edit.putString(key, value)
        edit.apply()
    }

    fun setSharedPreferences(key: String, value: Long) {
        val edit = sharedPref.edit()
        edit.putLong(key, value)
        edit.apply()
    }

    fun removeSharedPreferences(key: String) {
        val edit = sharedPref.edit()
        edit.remove(key)
        edit.apply()
    }

    fun getSharedPreferences(key: String, defaultValue: Long): Long =
        sharedPref.getLong(key, defaultValue)

    fun getSharedPreferences(key: String, defaultValue: String): String =
        sharedPref.getString(key, defaultValue) ?: ""

    companion object {
        const val PREFERENCE_NAME = "PREF"
    }
}