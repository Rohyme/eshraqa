package com.kheer.eshraqa.presentation.appUtils.languageUtils

import android.content.SharedPreferences
import com.google.gson.Gson
import javax.inject.Inject


class SharedPreferenceUtil @Inject constructor(private val sharedPreference: SharedPreferences, private val gson: Gson) {

    var language: String
        get() = this.getString("language", "ar") ?: "ar"
        set(currentLanguage) {
            putString("language", currentLanguage)
        }

    fun putString(key: String, value: String?) {
        val editor = sharedPreference.edit()
        editor.putString(key, value)
        editor.apply()
    }


    fun getString(key: String, defaultValue: String?): String? {
        return sharedPreference.getString(key, defaultValue)
    }

    val FAVOURITE = "FAVOURITE"
    fun putToFav(eshraqaId: String) {
        val favs = getString(FAVOURITE, "") ?: ""
        val favourites =
                if (favs.isEmpty()) {
                    ArrayList<String>()
                } else
                    ArrayList(favs.split(","))

        if (favourites.contains(eshraqaId)) {
            favourites.remove(eshraqaId)
        } else {
            favourites.add(eshraqaId)
        }

        val updatedFavs = when {
            favourites.size > 1 -> favourites.joinToString(",")
            favourites.size==1 -> favourites[0]
            else -> ""
        }

        putString(FAVOURITE, updatedFavs)
    }

    fun getFavsList(): List<String> {
        val favs = sharedPreference.getString(FAVOURITE, "") ?: ""
        return favs.split(",")
    }


    fun isCached(): Boolean {
        return sharedPreference.getBoolean(DATA_CACHED, false)
    }

    private val DATA_CACHED: String = "DATA_CACHED"


}






