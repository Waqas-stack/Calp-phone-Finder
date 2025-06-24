package com.o9tech.clapphonefinder.Locale

import android.content.Context
import android.content.res.Configuration
import androidx.core.content.edit
import java.util.Locale

//object LocaleHelper {
//    fun setLocale(context: Context, langCode: String): Context {
//        val locale = Locale(langCode)
//        Locale.setDefault(locale)
//
//        val config = Configuration(context.resources.configuration)
//        config.setLocale(locale)
//        return context.createConfigurationContext(config)
//    }
//}


object LocaleManager {
    fun setLocale(context: Context, languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        prefs.edit() { putString("language", languageCode) }
        return context.createConfigurationContext(config)
    }
    fun getSavedLanguage(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        return sharedPreferences.getString("language", "en") ?: "en"
    }
}