package com.asrul.covidvaccine.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.asrul.covidvaccine.R
import com.asrul.covidvaccine.utils.SessionManager

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val sessionManager = SessionManager(this)

            if (sessionManager.isLoggedIn()) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        },  1000)
    }
}