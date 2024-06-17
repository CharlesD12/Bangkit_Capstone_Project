package com.example.projectpenelitian

import android.annotation.SuppressLint
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import com.example.projectpenelitian.databinding.ActivityBottomNavBinding
import com.example.projectpenelitian.ui.camera.CameraFragment
import com.example.projectpenelitian.ui.home.HomeFragment
import com.example.projectpenelitian.ui.miniklopedia.BookmarkFragment
import com.example.projectpenelitian.ui.miniklopedia.MiniklopediaFragment
import com.example.projectpenelitian.ui.profile.ProfileFragment

class BottomNavActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBottomNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        navView.menu.getItem(2).isEnabled = false

        supportFragmentManager.beginTransaction()
            .add(R.id.nav_host_fragment_activity_bottom_nav, HomeFragment())
            .commit()

        binding.cameraFBtn.setOnClickListener {

            binding.cameraFBtn.strokeColor = resources.getColor(R.color.brown57, theme)
            binding.navView.itemIconTintList = resources.getColorStateList(R.color.gray6A, theme)

            binding.navView.selectedItemId = R.id.navigation_camera

            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_activity_bottom_nav, CameraFragment())
                .commit()
        }

        navView.setOnItemSelectedListener {
            binding.cameraFBtn.strokeColor = resources.getColor(R.color.white, theme)
            binding.navView.itemIconTintList = resources.getColorStateList(R.drawable.bottom_icon_color, theme)

            when(it.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_bottom_nav, HomeFragment())
                        .commit()
                }
                R.id.navigation_bookmark -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_bottom_nav, BookmarkFragment())
                        .commit()
                }
                R.id.navigation_forum -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_bottom_nav, MiniklopediaFragment())
                        .commit()
                }
                R.id.navigation_profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_bottom_nav, ProfileFragment())
                        .commit()
                }
            }

            true
        }
    }
}