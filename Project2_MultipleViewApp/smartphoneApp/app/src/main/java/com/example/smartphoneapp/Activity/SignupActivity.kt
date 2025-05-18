package com.example.smartphoneapp.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.smartphoneapp.PrefsHelper
import com.example.smartphoneapp.R

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val nameInput = findViewById<EditText>(R.id.nameInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val signupBtn = findViewById<Button>(R.id.signupBtn)
        val toggleLogin = findViewById<TextView>(R.id.toggleLogin)

        toggleLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        signupBtn.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (name.isNotEmpty() && password.isNotEmpty()) {
                // Save user but not auto-login
                PrefsHelper.saveUser(this, name, password)

                // Immediately log in the user
                PrefsHelper.loginUser(this)

                // ✅ Redirect to MainActivity
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}

