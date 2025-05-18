package com.example.smartphoneapp.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartphoneapp.PrefsHelper
import com.example.smartphoneapp.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val nameInput = findViewById<EditText>(R.id.nameInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val toggleSignup = findViewById<TextView>(R.id.toggleSignup)

        loginBtn.setOnClickListener {
            val enteredName = nameInput.text.toString().trim()
            val enteredPassword = passwordInput.text.toString().trim()

            val savedName = PrefsHelper.getUserName(this)
            val savedPassword = PrefsHelper.getPassword(this)

            if (enteredName == savedName && enteredPassword == savedPassword) {
                PrefsHelper.loginUser(this)

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }

        toggleSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}
