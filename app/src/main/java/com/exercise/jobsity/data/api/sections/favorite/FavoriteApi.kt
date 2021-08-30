package com.exercise.jobsity.data.api.sections.favorite

import android.content.SharedPreferences
import com.exercise.jobsity.domain.model.Show
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class FavoriteApi @Inject constructor(private val sharedPreferences: SharedPreferences) {

    private val gson = Gson()

    fun setFavorite(show: Show) {
        val showList : MutableList<Show> = getFavoriteList() as MutableList<Show>
        showList.add(show)
        val showListJson = gson.toJson(showList)
        sharedPreferences.edit().putString(FAVORITE_SHOW_LIST, showListJson).apply()
    }

    fun removeFavorite(show : Show) {
        val showList : MutableList<Show> = getFavoriteList() as MutableList<Show>
        showList.remove(show)
        val showListJson = gson.toJson(showList)
        sharedPreferences.edit().putString(FAVORITE_SHOW_LIST, showListJson).apply()
    }

    fun getFavoriteList() : List<Show> {
        var showListJson = sharedPreferences.getString(FAVORITE_SHOW_LIST, null)
        var showList : MutableList<Show> = mutableListOf<Show>()
        showListJson?.let {
            showList = gson.fromJson(it, object : TypeToken<MutableList<Show>>() {}.type)
            return showList
        }
        return showList
    }

    fun getIfShowIsFavorite(show: Show): Boolean {
        val showList : MutableList<Show> = getFavoriteList() as MutableList<Show>
        return showList.contains(show)
    }

    companion object {
        private const val FAVORITE_SHOW_LIST = "FAVORITE_SHOW_LIST"
    }
}