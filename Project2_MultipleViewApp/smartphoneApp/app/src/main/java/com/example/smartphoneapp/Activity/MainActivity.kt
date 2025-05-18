package com.example.smartphoneapp.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.smartphoneapp.PrefsHelper
import com.example.smartphoneapp.R
import com.example.smartphoneapp.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Profile Icon Button - This should be outside the logout button click listener
        val profileIcon = findViewById<ImageView>(R.id.profileIcon)
        profileIcon.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        // Welcome Text
        val welcomeText = findViewById<TextView>(R.id.welcomeText)
        val userName = PrefsHelper.getUserName(this)

        if (PrefsHelper.isLoggedIn(this)) {
            welcomeText.text = "Welcome back, $userName"
        } else {
            welcomeText.text = "Welcome to Cha Haus, $userName"
        }

        setVariable()
    }

    private fun setVariable() {
        binding.apply{
            btn1.setOnClickListener{ startListActivity("1","Milk Tea")}
            btn2.setOnClickListener{ startListActivity("2","Fresh Milk")}
            btn3.setOnClickListener{ startListActivity("3","Fresh Tea")}
            btn4.setOnClickListener{ startListActivity("4","Macchiato")}
            btn5.setOnClickListener{ startListActivity("5","Fruit Tea")}
            btn6.setOnClickListener{ startListActivity("6","Coffee")}
        }
    }

    private fun startListActivity(categoryId: String, title: String){
        val intent = Intent(this, ListActivity::class.java)
        intent.putExtra("id", categoryId)  // Pass categoryId as String
        intent.putExtra("title", title)
        startActivity(intent)
    }
}





