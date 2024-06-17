package com.example.projectpenelitian.ui.camera


import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.checkSelfPermission
import com.example.projectpenelitian.databinding.FragmentCameraBinding
import com.example.projectpenelitian.ml.CassavaModel
import com.example.projectpenelitian.ml.Corn
import com.example.projectpenelitian.ml.RiceModel
import com.google.gson.Gson
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder


class CameraFragment : Fragment() {

    private lateinit var binding: FragmentCameraBinding
    private lateinit var storagePermission: ActivityResultLauncher<String>
    private lateinit var context: Context

    private var extra_image: String? = null
    private var extra_classification: String? = null
    private var myBitmapImage: Bitmap? = null
    private val imageSize = 224

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

        storagePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {

        }

        binding.cameraBtn.setOnClickListener {
            if (checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                val maxWidth = 1920
                val maxHeight = 1080
                cameraIntent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, "${maxWidth}x${maxHeight}")

                startActivityForResult(cameraIntent, 3)
            } else {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        }

        binding.galeryBtn.setOnClickListener {
            val cameraIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(cameraIntent, 1)
        }

        // button upload
        binding.padiBtn.setOnClickListener {
            classifyPadi(myBitmapImage)
        }
        binding.jagungBtn.setOnClickListener {
            classifyJagung(myBitmapImage)
        }
        binding.singkongBtn.setOnClickListener {
            classifySingkong(myBitmapImage)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == 3) {
                val image = data?.extras?.get("data") as Bitmap
                val dimension = Math.min(image.width, image.height)
                val thumbnail = Bitmap.createScaledBitmap(image, dimension, dimension, false)
                binding.imageView5.setImageBitmap(thumbnail)
                val scaledImage = Bitmap.createScaledBitmap(thumbnail, imageSize, imageSize, false)

                myBitmapImage = scaledImage
                // classifyImage(scaledImage)

                val gson = Gson()
                extra_image = gson.toJson(thumbnail)

            } else {
                val dat = data?.data
                var image: Bitmap? = null
                try {
                    image = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, dat)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                binding.imageView5.setImageBitmap(image)
                val scaledImage = Bitmap.createScaledBitmap(image!!, imageSize, imageSize, false)

                myBitmapImage = scaledImage
                // classifyImage(scaledImage)

                val gson = Gson()
                extra_image = gson.toJson(image)

            }
        }
    }

    private fun classifyPadi(image: Bitmap?) {
        if (image != null){
            try {
                val model = RiceModel.newInstance(requireContext())
                val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
                val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
                byteBuffer.order(ByteOrder.nativeOrder())

                val intValues = IntArray(imageSize * imageSize)
                image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
                var pixel = 0
                for (i in 0 until imageSize) {
                    for (j in 0 until imageSize) {
                        val value = intValues[pixel++]
                        byteBuffer.putFloat(((value shr 16) and 0xFF) * (1f / 255f))
                        byteBuffer.putFloat(((value shr 8) and 0xFF) * (1f / 255f))
                        byteBuffer.putFloat((value and 0xFF) * (1f / 255f))
                    }
                }

                inputFeature0.loadBuffer(byteBuffer)
                val outputs = model.process(inputFeature0)
                val outputFeature0 = outputs.getOutputFeature0AsTensorBuffer()

                val confidences = outputFeature0.floatArray
                var maxPos = 0
                var maxConfidence = 0f
                for (i in confidences.indices) {
                    if (confidences[i] > maxConfidence) {
                        maxConfidence = confidences[i]
                        maxPos = i
                    }
                }

                var labels = requireContext().assets.open("riceModelLabels.txt").bufferedReader().readLines()

                extra_classification = labels[maxPos]
                model.close()

                val intent = Intent(requireContext(), ResultActivity::class.java)
                intent.putExtra(ResultActivity.EXTRA_IMAGE, extra_image.toString())
                intent.putExtra(ResultActivity.EXTRA_CLASSIFICATIONS, extra_classification.toString())
                startActivity(intent)

            } catch (e: IOException) {
                // Handle the exception
                Toast.makeText(requireContext(), "Error: ${e.toString()}", Toast.LENGTH_SHORT).show()
            }
        }else{
             Toast.makeText(requireContext(), "Mohon pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show()
        }
    }

    private fun classifyJagung(image: Bitmap?) {
        if (image != null){
            try {
                val model = Corn.newInstance(requireContext())
                val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
                val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
                byteBuffer.order(ByteOrder.nativeOrder())

                val intValues = IntArray(imageSize * imageSize)
                image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
                var pixel = 0
                for (i in 0 until imageSize) {
                    for (j in 0 until imageSize) {
                        val value = intValues[pixel++]
                        byteBuffer.putFloat(((value shr 16) and 0xFF) * (1f / 255f))
                        byteBuffer.putFloat(((value shr 8) and 0xFF) * (1f / 255f))
                        byteBuffer.putFloat((value and 0xFF) * (1f / 255f))
                    }
                }

                inputFeature0.loadBuffer(byteBuffer)
                val outputs = model.process(inputFeature0)
                val outputFeature0 = outputs.getOutputFeature0AsTensorBuffer()

                val confidences = outputFeature0.floatArray
                var maxPos = 0
                var maxConfidence = 0f
                for (i in confidences.indices) {
                    if (confidences[i] > maxConfidence) {
                        maxConfidence = confidences[i]
                        maxPos = i
                    }
                }

                var labels = requireContext().assets.open("cornModelLabels.txt").bufferedReader().readLines()

                extra_classification = labels[maxPos]
                model.close()

                val intent = Intent(requireContext(), ResultActivity::class.java)
                intent.putExtra(ResultActivity.EXTRA_IMAGE, extra_image.toString())
                intent.putExtra(ResultActivity.EXTRA_CLASSIFICATIONS, extra_classification.toString())
                startActivity(intent)

            } catch (e: IOException) {
                // Handle the exception
                Toast.makeText(requireContext(), "Error: ${e.toString()}", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(requireContext(), "Mohon pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show()
        }
    }

    private fun classifySingkong(image: Bitmap?) {
        if (image != null){
            try {
                val model = CassavaModel.newInstance(requireContext())
                val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
                val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
                byteBuffer.order(ByteOrder.nativeOrder())

                val intValues = IntArray(imageSize * imageSize)
                image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
                var pixel = 0
                for (i in 0 until imageSize) {
                    for (j in 0 until imageSize) {
                        val value = intValues[pixel++]
                        byteBuffer.putFloat(((value shr 16) and 0xFF) * (1f / 255f))
                        byteBuffer.putFloat(((value shr 8) and 0xFF) * (1f / 255f))
                        byteBuffer.putFloat((value and 0xFF) * (1f / 255f))
                    }
                }

                inputFeature0.loadBuffer(byteBuffer)
                val outputs = model.process(inputFeature0)
                val outputFeature0 = outputs.getOutputFeature0AsTensorBuffer()

                val confidences = outputFeature0.floatArray
                var maxPos = 0
                var maxConfidence = 0f
                for (i in confidences.indices) {
                    if (confidences[i] > maxConfidence) {
                        maxConfidence = confidences[i]
                        maxPos = i
                    }
                }

                var labels = requireContext().assets.open("cassavaModelLabels.txt").bufferedReader().readLines()

                extra_classification = labels[maxPos]
                model.close()

                val intent = Intent(requireContext(), ResultActivity::class.java)
                intent.putExtra(ResultActivity.EXTRA_IMAGE, extra_image.toString())
                intent.putExtra(ResultActivity.EXTRA_CLASSIFICATIONS, extra_classification.toString())
                startActivity(intent)

            } catch (e: IOException) {
                // Handle the exception
                Toast.makeText(requireContext(), "Error: ${e.toString()}", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(requireContext(), "Mohon pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show()
        }
    }

}