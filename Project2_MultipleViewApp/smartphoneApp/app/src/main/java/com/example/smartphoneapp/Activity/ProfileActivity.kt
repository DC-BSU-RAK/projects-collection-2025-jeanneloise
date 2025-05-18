package com.example.smartphoneapp.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartphoneapp.Adapter.FavoriteAdapter
import com.example.smartphoneapp.Model.ItemsModel
import com.example.smartphoneapp.PrefsHelper
import com.example.smartphoneapp.R

class ProfileActivity : AppCompatActivity() {

    private lateinit var favoritesAdapter: FavoriteAdapter
    private lateinit var recyclerView: RecyclerView
    private var favoriteList = mutableListOf<ItemsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val userNameText = findViewById<TextView>(R.id.userNameText)
        val logoutBtn = findViewById<Button>(R.id.logoutBtn)
        val backBtn = findViewById<ImageView>(R.id.backBtn)
        recyclerView = findViewById(R.id.favoritesRecyclerView)

        val name = PrefsHelper.getUserName(this)
        userNameText.text = name ?: "Guest"

        // Back to main screen
        backBtn.setOnClickListener {
            finish()
        }

        // Logout
        logoutBtn.setOnClickListener {
            PrefsHelper.logoutUser(this)
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }

        // Load favorite items
        favoriteList = PrefsHelper.getFavoriteList(this).toMutableList()

// Set up adapter with delete handler
        favoritesAdapter = FavoriteAdapter(favoriteList) { removedItem ->
            // 1. Remove from the in-memory list
            favoriteList.remove(removedItem)

            // 2. Save updated list to SharedPreferences
            PrefsHelper.saveFavoriteList(this, favoriteList)

            // 3. Notify adapter
            favoritesAdapter.notifyDataSetChanged()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = favoritesAdapter
    }
}

