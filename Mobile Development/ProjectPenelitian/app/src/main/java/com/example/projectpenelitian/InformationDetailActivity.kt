package com.example.projectpenelitian

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projectpenelitian.databinding.ActivityInformationDetailBinding
import com.example.projectpenelitian.model.Information

class InformationDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInformationDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityInformationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val information = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(KeyIntent.INFORMATION_KEY, Information::class.java)
        } else {
            intent.getSerializableExtra(KeyIntent.INFORMATION_KEY) as Information
        }

        if (information != null) {
            binding.infoNameTxt.text = information.infoName
            binding.infoImg2.setImageResource(information.infoImg2)
            binding.desc1.text = information.infoDesc1
            binding.desc2.text = information.infoDesc2
            binding.desc3.text = information.infoDesc3
        }
    }
}