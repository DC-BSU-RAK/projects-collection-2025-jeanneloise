package com.example.smartphoneapp

import android.content.Context
import com.example.smartphoneapp.Model.ItemsModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object PrefsHelper {
    private const val PREFS_NAME = "user_prefs"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_PASSWORD = "user_password"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"

    fun saveUser(context: Context, name: String, password: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit()
            .putString(KEY_USER_NAME, name)
            .putString(KEY_PASSWORD, password)
            .putBoolean(KEY_IS_LOGGED_IN, false)
            .apply()
    }

    fun loginUser(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_IS_LOGGED_IN, true).apply()
    }

    fun logoutUser(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_IS_LOGGED_IN, false).apply()
    }

    fun isLoggedIn(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getUserName(context: Context): String? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_USER_NAME, null)
    }

    fun getPassword(context: Context): String? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_PASSWORD, null)
    }

    fun addToFavorites(context: Context, item: ItemsModel) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString("favorites", null)
        val listType = object : TypeToken<MutableList<ItemsModel>>() {}.type
        val list: MutableList<ItemsModel> = Gson().fromJson(json, listType) ?: mutableListOf()

        if (!list.any { it.title == item.title }) {
            list.add(item)
            prefs.edit().putString("favorites", Gson().toJson(list)).apply()
        }
    }

    fun getFavoriteList(context: Context): List<ItemsModel> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString("favorites", null)
        val listType = object : TypeToken<List<ItemsModel>>() {}.type
        return Gson().fromJson(json, listType) ?: listOf()
    }
    fun removeFavorite(context: Context, title: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString("favorites", null)

        if (json != null) {
            val list = Gson().fromJson<List<ItemsModel>>(json, object : TypeToken<List<ItemsModel>>() {}.type)
            val newList = list.filter { it.title != title }
            val updatedJson = Gson().toJson(newList)

            prefs.edit().putString("favorites", updatedJson).apply()
        }
    }

    fun saveFavoriteList(context: Context, list: List<ItemsModel>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val json = Gson().toJson(list)
        editor.putString("favorites", json)
        editor.apply()
    }
}



