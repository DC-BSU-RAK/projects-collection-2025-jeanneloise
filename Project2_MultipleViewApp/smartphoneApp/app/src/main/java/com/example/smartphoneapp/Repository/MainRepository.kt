package com.example.smartphoneapp.Repository

import android.content.Context
import com.example.smartphoneapp.Model.ItemsModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainRepository(private val context: Context) {

    fun getItemsFromAssets(): List<ItemsModel> {
        return try {
            val jsonString = context.assets.open("items.json")
                .bufferedReader()
                .use { it.readText() }

            // Parse the JSON object and extract the "Items" array
            val jsonObject = Gson().fromJson(jsonString, Map::class.java)
            val itemsJson = Gson().toJson(jsonObject["Items"])

            val itemType = object : TypeToken<List<ItemsModel>>() {}.type
            Gson().fromJson(itemsJson, itemType)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
    fun getItemsByCategory(categoryId: String): List<ItemsModel> {
        return getItemsFromAssets().filter { it.categoryId == categoryId }
    }
}
