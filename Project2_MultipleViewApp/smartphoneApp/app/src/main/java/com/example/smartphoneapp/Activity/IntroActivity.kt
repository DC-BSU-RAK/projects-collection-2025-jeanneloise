package com.example.smartphoneapp.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.smartphoneapp.PrefsHelper
import com.example.smartphoneapp.R

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        Handler(Looper.getMainLooper()).postDelayed({
            if (PrefsHelper.isLoggedIn(this)) {

                startActivity(Intent(this, MainActivity::class.java))
            } else {

                startActivity(Intent(this, LoginActivity::class.java))

            }
            finish()
        }, 2000) // 2 seconds splash delay
    }
}

