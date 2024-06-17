package com.example.projectpenelitian.ui.camera

import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.os.Environment
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.projectpenelitian.databinding.ActivityCameraXactivityBinding
import com.google.common.util.concurrent.ListenableFuture
import java.io.File
import java.io.IOException
import java.util.Date
import java.util.Locale

class CameraXActivity : AppCompatActivity() {

    lateinit var binding: ActivityCameraXactivityBinding
    lateinit var imageCapture: ImageCapture
    lateinit var cameraPermission: ActivityResultLauncher<String>
    lateinit var processCameraProvider: ListenableFuture<ProcessCameraProvider>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraXactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        processCameraProvider = ProcessCameraProvider.getInstance(this)

        cameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                initializeCamera()
            }
        }

        if (isCameraPermissionGranted()) {
            initializeCamera()
        } else {
            cameraPermission.launch(android.Manifest.permission.CAMERA)
        }

        binding.cameraFBtn.setOnClickListener {

            val fileOptions = ImageCapture.OutputFileOptions.Builder(createImageFile()).build()

            imageCapture.takePicture(fileOptions, ContextCompat.getMainExecutor(this), object :
                ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val uri = outputFileResults.savedUri
                    if (uri != null) {
                        val intent = Intent()
                        intent.setData(uri)
                        setResult(RESULT_OK, intent)
                        finish()
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    exception.printStackTrace()
                }
            })
        }

    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }

    private fun initializeCamera() {
        processCameraProvider.addListener({

            val provider = processCameraProvider.get()

            imageCapture = ImageCapture.Builder()
                .build()
            val preview = Preview.Builder()
                .build()
            preview.setSurfaceProvider(binding.preview.surfaceProvider)

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                provider.unbindAll()
                provider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
            } catch (exc: Exception) {
                exc.printStackTrace()
            }
        },ContextCompat.getMainExecutor(this))
    }

    private fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }
}