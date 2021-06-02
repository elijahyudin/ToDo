package com.coursework.androidtodometer.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.coursework.androidtodometer.databinding.SplashActivityBinding
import com.coursework.androidtodometer.ui.main.MainActivity
import kotlinx.coroutines.delay

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: SplashActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launchWhenCreated {
            delay(500)
            binding.motionLayout.transitionToEnd()
            delay(1000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }
}
