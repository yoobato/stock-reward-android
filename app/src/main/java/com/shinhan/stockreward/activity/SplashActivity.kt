package com.shinhan.stockreward.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.shinhan.stockreward.R

class SplashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            this@SplashActivity.finish()
        }, 1000)
    }
}