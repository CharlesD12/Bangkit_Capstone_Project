package com.example.projectpenelitian.ui.camera

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.example.projectpenelitian.api.response.ErrorResponse
import com.example.projectpenelitian.data.pref.dataStore
import com.example.projectpenelitian.databinding.ActivityResultBinding
import com.google.gson.Gson
import com.wensolution.storyapp.apiservice.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.ByteArrayOutputStream

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set data from intent
        val jsonString = intent.getStringExtra(EXTRA_IMAGE)
        if (jsonString != null) {
            val gson = Gson()
            val bitmap = gson.fromJson(jsonString, Bitmap::class.java)
            binding.infoImg.setImageBitmap(bitmap)
        }
        val stringExtra = intent.getStringExtra(EXTRA_CLASSIFICATIONS)
        if(stringExtra != null) {
            val stringExtra_explode = stringExtra.split(" - ")
            val namaTanaman = stringExtra_explode[0]
            val jenisPenyakit = stringExtra_explode[1]

            getDataFromApi(namaTanaman, jenisPenyakit)
        }

        binding.shareBtnId.setOnClickListener {
            addMiniklopedia()
        }

        binding.bookmarkBtnId.setOnClickListener {
            addBookmark()
        }
    }

    // get data detail from api
    private fun getDataFromApi(namaTanaman: String, jenisPenyakit: String)
    {
        binding.namaTanamanId.text = namaTanaman
        binding.jenisPenyakitId.text = jenisPenyakit

        binding.deskripsiId.text = ""
        showLoading(true)

        // api detail plant
        lifecycleScope.launch {
            try {
                val token = stringPreferencesKey("token")
                val sessionToken = this@ResultActivity.dataStore.data.first()[token]
                val tokenString = "Bearer ${sessionToken.toString()}"

                val apiService = ApiConfig.getApiService()
                val successResponse = apiService.detailPlant(tokenString, namaTanaman, jenisPenyakit)

                showLoading(false)

                // data success
                if(successResponse.message == "Success"){
                    var dataResponse = successResponse.data

                    // fetch data
                    if (!dataResponse.isNullOrEmpty()) {
                        val firstDataPlant = dataResponse[0]

                        if(firstDataPlant != null){
                            var combinedText = ""

                            // deskripsi
                            val deskripsi = firstDataPlant.deskripsi?: "-"
                            combinedText = combinedText + "Deskripsi: $deskripsi" + "\n\n"

                            // ciri ciri
                            val ciriCiriData = firstDataPlant.ciriCiri
                            val ciriCiriList = mutableListOf<String>()
                            ciriCiriData?.let { ciriCiri ->
                                if(ciriCiri.jsonMember0 != null) { ciriCiriList.add("- ${ciriCiri.jsonMember0}") }
                                if(ciriCiri.jsonMember1 != null) { ciriCiriList.add("- ${ciriCiri.jsonMember1}") }
                                if(ciriCiri.jsonMember2 != null) { ciriCiriList.add("- ${ciriCiri.jsonMember2}") }
                            }
                            val formattedCiriCiri = ciriCiriList.filter { it.isNotBlank() }.joinToString(separator = "\n")
                            if(formattedCiriCiri != ""){
                                combinedText = combinedText + "Ciri-ciri:\n$formattedCiriCiri"  + "\n\n"
                            }

                            // cara pengobatan
                            val caraPengobatanData = firstDataPlant.caraPengobatan
                            val caraPengobatanList = mutableListOf<String>()
                            caraPengobatanData?.let { caraPengobatan ->
                                if(caraPengobatan.jsonMember0 != null) { caraPengobatanList.add("- ${caraPengobatan.jsonMember0}") }
                                if(caraPengobatan.jsonMember1 != null) { caraPengobatanList.add("- ${caraPengobatan.jsonMember1}") }
                                if(caraPengobatan.jsonMember2 != null) { caraPengobatanList.add("- ${caraPengobatan.jsonMember2}") }
                            }
                            val formattedCaraPengobatan = caraPengobatanList.filter { it.isNotBlank() }.joinToString(separator = "\n")
                            if(formattedCaraPengobatan != ""){
                                combinedText = combinedText + "Cara pengobatan:\n$formattedCaraPengobatan"  + "\n\n"
                            }

                            // cara pengobatan alami
                            val caraPengobatanAlamiData = firstDataPlant.caraPengobatanAlami
                            val caraPengobatanAlamiList = mutableListOf<String>()
                            caraPengobatanAlamiData?.let { caraPengobatanAlami ->
                                if(caraPengobatanAlami.jsonMember0 != null) { caraPengobatanAlamiList.add("- ${caraPengobatanAlami.jsonMember0}") }
                                if(caraPengobatanAlami.jsonMember1 != null) { caraPengobatanAlamiList.add("- ${caraPengobatanAlami.jsonMember1}") }
                                if(caraPengobatanAlami.jsonMember2 != null) { caraPengobatanAlamiList.add("- ${caraPengobatanAlami.jsonMember2}") }
                            }
                            val formattedCaraPengobatanAlami = caraPengobatanAlamiList.filter { it.isNotBlank() }.joinToString(separator = "\n")
                            if(formattedCaraPengobatanAlami != "") {
                                combinedText = combinedText + "Cara Pengobatan Alami:\n$formattedCaraPengobatanAlami" + "\n\n"
                            }

                            // cara perawatan
                            val caraPerawatanData = firstDataPlant.caraPerawatan
                            val caraPerawatanList = mutableListOf<String>()
                            caraPerawatanData?.let { caraPerawatan ->
                                if(caraPerawatan.jsonMember0 != null) { caraPerawatanList.add("- ${caraPerawatan.jsonMember0}") }
                                if(caraPerawatan.jsonMember1 != null) { caraPerawatanList.add("- ${caraPerawatan.jsonMember1}") }
                                if(caraPerawatan.jsonMember2 != null) { caraPerawatanList.add("- ${caraPerawatan.jsonMember2}") }
                            }
                            val formattedCaraPerawatan = caraPerawatanList.filter { it.isNotBlank() }.joinToString(separator = "\n")
                            if(formattedCaraPerawatan != ""){
                                combinedText = combinedText + "Cara Perawatan:\n$formattedCaraPerawatan"  + "\n\n"
                            }

                            binding.deskripsiId.text = combinedText
                        }
                    } else {
                        binding.bookmarkBtnId.visibility = View.GONE
                        binding.shareBtnId.visibility = View.GONE

                        showToast("Tidak ada data  tanaman yang ditemukan.")

                    }
                }else{
                    showToast(successResponse.message)
                }

            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string() ?: "Data error, please try again"
                val errorMessage  = Gson().fromJson(errorBody, ErrorResponse::class.java)
                var errorMessage2 = errorMessage.message
                if (errorMessage2 == null)
                {
                    errorMessage2 = "Data error, please try again"
                }

                showToast(errorMessage2)
                showLoading(false)
            }
        }
    }

    private fun addMiniklopedia() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Miniklopedia")
        builder.setMessage("Apakah Anda yakin ingin share ke Miniklopedia?")

        // jika alert yes
        builder.setPositiveButton("Yes") { dialog, which ->
            uploadToMiniklopedia()
        }

        // Tambahkan tombol "No"
        builder.setNegativeButton("No") { dialog, which ->

        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun uploadToMiniklopedia(){
        // loading
        showLoading(true)

        // get data intent
        val jsonString = intent.getStringExtra(EXTRA_IMAGE)
        val stringExtra = intent.getStringExtra(EXTRA_CLASSIFICATIONS)
        if (jsonString != null && stringExtra != null) {
            val gson = Gson()
            val bitmap = gson.fromJson(jsonString, Bitmap::class.java)
            val stringExtra_explode = stringExtra.split(" - ")
            val namaTanaman = stringExtra_explode[0]
            val jenisPenyakit = stringExtra_explode[1]

            // set data for upload
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            byteArrayOutputStream.close()
            val imageRequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), byteArray)
            val imagePart = MultipartBody.Part.createFormData("image", "image.jpg", imageRequestBody)

            lifecycleScope.launch {
                try {
                    val token = stringPreferencesKey("token")
                    val sessionToken = this@ResultActivity.dataStore.data.first()[token]
                    val tokenString = "Bearer ${sessionToken.toString()}"

                    // upload to miniklopedia
                    val apiService = ApiConfig.getApiService()
                    val successResponse = apiService.addMiniklopedia(namaTanaman, jenisPenyakit, imagePart, tokenString)

                    showLoading(false)

                    if (successResponse.message == "Success") {
                        showToast("Data berhasil diupload di Miniklopedia ^^.")
                        binding.shareBtnId.visibility = View.GONE

                    }else {
                        showToast("Data gagal diupload.")

                    }
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string() ?: "Data error, please try again"
                    val errorMessage  = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    var errorMessage2 = errorMessage.message
                    if (errorMessage2 == null)
                    {
                        errorMessage2 = "Data error, please try again"
                    }

                    showToast(errorMessage2)
                    showLoading(false)
                }
            }
        }
    }

    private fun addBookmark() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Bookmark")
        builder.setMessage("Apakah Anda yakin ingin menambahkan ke bookmark?")

        // jika alert yes
        builder.setPositiveButton("Yes") { dialog, which ->
            uploadToBookmark()
        }

        // Tambahkan tombol "No"
        builder.setNegativeButton("No") { dialog, which ->

        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun uploadToBookmark(){
        // loading
        showLoading(true)

        // get data intent
        val jsonString = intent.getStringExtra(EXTRA_IMAGE)
        val stringExtra = intent.getStringExtra(EXTRA_CLASSIFICATIONS)
        if (jsonString != null && stringExtra != null) {
            val gson = Gson()
            val bitmap = gson.fromJson(jsonString, Bitmap::class.java)
            val stringExtra_explode = stringExtra.split(" - ")
            val namaTanaman = stringExtra_explode[0]
            val jenisPenyakit = stringExtra_explode[1]

            // set data for upload
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            byteArrayOutputStream.close()
            val imageRequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), byteArray)
            val imagePart = MultipartBody.Part.createFormData("image", "image.jpg", imageRequestBody)

            lifecycleScope.launch {
                try {
                    val token = stringPreferencesKey("token")
                    val sessionToken = this@ResultActivity.dataStore.data.first()[token]
                    val tokenString = "Bearer ${sessionToken.toString()}"

                    // upload to bookmark
                    val apiService = ApiConfig.getApiService()
                    val successResponse = apiService.addBookmark(namaTanaman, jenisPenyakit, imagePart, tokenString)

                    showLoading(false)

                    if (successResponse.message == "Succcess") {
                        showToast("Data berhasil ditambahkan di Bookmark ^^.")
                        binding.bookmarkBtnId.visibility = View.GONE

                    }else {
                        showToast("Data gagal diupload.")

                    }
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string() ?: "Data error, please try again"
                    val errorMessage  = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    var errorMessage2 = errorMessage.message
                    if (errorMessage2 == null)
                    {
                        errorMessage2 = "Data error, please try again"
                    }

                    showToast(errorMessage2)
                    showLoading(false)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_IMAGE = "extra_image_uri"
        const val EXTRA_CLASSIFICATIONS  = "extra_classifications"
    }
}