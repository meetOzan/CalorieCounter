package com.meetozan.caloriecounter.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.meetozan.caloriecounter.R
import com.meetozan.caloriecounter.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    companion object {
        const val ANIMATION_TIME: Long = 2100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(this.mainLooper).postDelayed({
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
        }, ANIMATION_TIME)
    }
}