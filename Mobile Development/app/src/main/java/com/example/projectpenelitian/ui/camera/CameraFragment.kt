package com.example.projectpenelitian.ui.camera


import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.projectpenelitian.databinding.FragmentCameraBinding

class CameraFragment : Fragment() {

    private lateinit var binding: FragmentCameraBinding
    private lateinit var launchGallery: ActivityResultLauncher<String>
    private lateinit var storagePermission: ActivityResultLauncher<String>
    private lateinit var startCameraX: ActivityResultLauncher<Intent>
    private lateinit var context: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startCameraX = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                binding.imageView5.setImageURI(it.data?.data)
            }
        }

        storagePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {

        }

        launchGallery = registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                binding.imageView5.setImageURI(it)
            }
        }


        binding.galeryBtn.setOnClickListener {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                if (isStoragePermissionGranted()) {
                    launchGallery.launch("image/*")
                } else {
                    storagePermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            } else {
                launchGallery.launch("image/*")
            }
        }

        binding.cameraBtn.setOnClickListener {
            val intent = Intent(context, CameraXActivity::class.java)
            startCameraX.launch(intent)
        }
    }

    private fun isStoragePermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }
}