package com.example.projectpenelitian.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.projectpenelitian.BottomNavActivity
import com.example.projectpenelitian.R
import com.example.projectpenelitian.ViewModelFactory
import com.example.projectpenelitian.ui.login.LoginViewModel
import com.example.projectpenelitian.ui.welcome.WelcomeActivity

class SplashScreenActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(Looper.getMainLooper()).postDelayed({

            viewModel.getSession().observe(this) { user ->
                if (user.isLogin) {
                    startActivity(Intent(this, BottomNavActivity::class.java))
                }else{
                    startActivity(Intent(this, WelcomeActivity::class.java))
                }
            }
            finish()
        }, 1500)
    }
}