package com.example.national_dish_calculator

import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fun loadJSONFromAsset(context: Context, filename: String): String? {
            return try {
                val inputStream = context.assets.open(filename)
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                String(buffer, Charsets.UTF_8)
            } catch (ex: IOException) {
                ex.printStackTrace()
                null
            }
        }

        val spinner: Spinner = findViewById(R.id.countrySpinner)
        val countryList = mutableListOf("Select a country") // Add placeholder first

        val jsonString = loadJSONFromAsset(this, "national_dishes.json")
        jsonString?.let {
            val jsonObject = org.json.JSONObject(it)
            val keys = jsonObject.keys()
            while (keys.hasNext()) {
                val country = keys.next()
                countryList.add(country)
            }
        }

// Set up spinner adapter after building list
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countryList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter


        val button = findViewById<Button>(R.id.getDishButton)
        val imageView = findViewById<ImageView>(R.id.dishImage)
        val nameView = findViewById<TextView>(R.id.dishName)
        val descView = findViewById<TextView>(R.id.dishDescription)


        // Define outside the button click listener
        fun getDishInfo(context: Context, country: String, mealType: String): Triple<String, String, String>? {
            val jsonString = loadJSONFromAsset(context, "national_dishes.json") ?: return null
            val jsonObject = org.json.JSONObject(jsonString)

            if (jsonObject.has(country)) {
                val meals = jsonObject.getJSONObject(country)
                if (meals.has(mealType)) {
                    val dish = meals.getJSONObject(mealType)
                    val name = dish.getString("name")
                    val image = dish.getString("image") // Must match drawable filename
                    val description = dish.getString("description")
                    return Triple(name, image, description)
                }
            }
            return null
        }

// Inside setOnClickListener
        button.setOnClickListener {
            val selectedCountry = spinner.selectedItem.toString()
            val mealTypeId = findViewById<RadioGroup>(R.id.mealTypeGroup).checkedRadioButtonId
            val mealType = when (mealTypeId) {
                R.id.breakfast -> "breakfast"
                R.id.lunch -> "lunch"
                R.id.dinner -> "dinner"
                R.id.drink -> "drink"
                else -> ""
            }

            if (selectedCountry == "Select a country" || mealType.isEmpty()) {
                nameView.text = "Please select a country and meal type"
                descView.text = ""
                imageView.setImageDrawable(null)
                return@setOnClickListener
            }

            // Call the function and display the result
            val dishInfo = getDishInfo(this, selectedCountry, mealType)
            if (dishInfo != null) {
                nameView.text = dishInfo.first
                descView.text = dishInfo.third

                // Load image from drawable
                val imageResId = resources.getIdentifier(dishInfo.second, "drawable", packageName)
                if (imageResId != 0) {
                    imageView.setImageResource(imageResId)
                } else {
                    imageView.setImageDrawable(null)
                }
            } else {
                nameView.text = "Dish not found"
                descView.text = ""
                imageView.setImageDrawable(null)
            }
        }

        val infoButton = findViewById<Button>(R.id.infoButton)
        infoButton.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.info_dialog, null)
            val dialogBuilder = android.app.AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)

            val dialog = dialogBuilder.create()
            dialog.show()
            dialog.window?.setLayout(1450,7750) // Width in pixels (800px ≈ 300dp)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            val closeButton = dialogView.findViewById<Button>(R.id.closeButton)
            closeButton.setOnClickListener {
                dialog.dismiss()
            }
        }


    }
}