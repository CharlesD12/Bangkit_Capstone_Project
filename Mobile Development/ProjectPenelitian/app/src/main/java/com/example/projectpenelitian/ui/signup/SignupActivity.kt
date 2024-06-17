package com.example.projectpenelitian.ui.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.projectpenelitian.api.response.ErrorResponse
import com.example.projectpenelitian.data.dataClass.UserRegisterData
import com.example.projectpenelitian.databinding.ActivitySignupBinding
import com.example.projectpenelitian.ui.login.LoginActivity
import com.example.projectpenelitian.ui.login.customview.MyEmailText
import com.example.projectpenelitian.ui.login.customview.MyPasswordText
import com.example.projectpenelitian.ui.signup.customview.MyButton
import com.example.projectpenelitian.ui.signup.customview.MyNameText
import com.google.gson.Gson
import com.wensolution.storyapp.apiservice.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    private lateinit var myName: MyNameText
    private lateinit var myEmail: MyEmailText
    private lateinit var myPasswordText: MyPasswordText
    private lateinit var myButton: MyButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()

        playAnimation()

        myName = binding.nameText
        myEmail = binding.emailText
        myPasswordText = binding.passwordText
        myButton = binding.signupButton

        setMyButtonEnable()

        myName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) { }
        })
        myEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) { }
        })
        myPasswordText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) { }
        })
    }

    private fun setMyButtonEnable() {
        var validasi = true
        myButton.isEnabled = false

        val myName = myName.text
        val myEmail = myEmail.text
        val myPassword = myPasswordText.text
        if (myName == null || myName.toString().length < 8
            || myEmail == null || !Patterns.EMAIL_ADDRESS.matcher(myEmail.toString().trim()).matches()
            || myPassword == null || myPassword.toString().length < 8)
        {
            validasi = false
        }

        if(validasi)
        {
            myButton.isEnabled = true
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {

            showLoading(true)

            // sign up api
            lifecycleScope.launch {
                try {

                    val name = binding.nameText.text.toString()
                    val email = binding.emailText.text.toString()
                    val password = binding.passwordText.text.toString()

                    val requestBody = UserRegisterData(
                        name = name,
                        email = email,
                        password = password
                    )

                    val apiService = ApiConfig.getApiService()
                    val successResponse = apiService.register(requestBody)

                    showLoading(false)

                    if(successResponse.message == "Success"){
                        AlertDialog.Builder(this@SignupActivity).apply {
                            setTitle("Yeah!")
                            setMessage("Akun berhasil dibuat")
                            setPositiveButton("Lanjut") { _, _ ->
                                val intent = Intent(context, LoginActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
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
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val titleTextView = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(200)
        val nameTextView = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(200)
        val nameText = ObjectAnimator.ofFloat(binding.nameText, View.ALPHA, 1f).setDuration(200)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(200)
        val emailText = ObjectAnimator.ofFloat(binding.emailText, View.ALPHA, 1f).setDuration(200)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(200)
        val passwordText = ObjectAnimator.ofFloat(binding.passwordText, View.ALPHA, 1f).setDuration(200)
        val signupButton = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(200)

        AnimatorSet().apply {
            playSequentially(
                titleTextView,
                nameTextView,
                nameText,
                emailTextView,
                emailText,
                passwordTextView,
                passwordText,
                signupButton
            )
            start()
        }
    }
}