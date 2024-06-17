package com.example.projectpenelitian.ui.login

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
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.projectpenelitian.BottomNavActivity
import com.example.projectpenelitian.ViewModelFactory
import com.example.projectpenelitian.api.response.ErrorResponse
import com.example.projectpenelitian.data.pref.UserModel
import com.example.projectpenelitian.databinding.ActivityLoginBinding
import com.example.projectpenelitian.ui.login.customview.MyEmailText
import com.example.projectpenelitian.ui.login.customview.MyPasswordText
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var myEmail: MyEmailText
    private lateinit var myPasswordText: MyPasswordText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()

        playAnimation()

        myEmail = binding.emailText
        myPasswordText = binding.passwordText

        setMyButtonEnable()

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
        binding.loginButton.isEnabled = false

        val myEmail = myEmail.text
        val myPassword = myPasswordText.text
        if (myEmail == null || !Patterns.EMAIL_ADDRESS.matcher(myEmail.toString().trim()).matches()
            || myPassword == null || myPassword.toString().length < 8)
        {
            validasi = false
        }

        if(validasi)
        {
            binding.loginButton.isEnabled = true
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
        binding.loginButton.setOnClickListener {

            showLoading(true)

            val email = binding.emailText.text.toString()
            // val password = binding.passwordText.text.toString()

//            login api
            lifecycleScope.launch {
                try {
//                    val apiService = ApiConfig.getBaseApiService()
//                    val successResponse = apiService.login(email, password)
//
//                    val userId = successResponse.loginResult?.userId ?: "User ID NULL"
//                    val name = successResponse.loginResult?.name ?: "Name NULL"
//                    val token = successResponse.loginResult?.token ?: "Token NULL"

                    val userId = "User ID 1"
                    val name = "User Name 1"
                    val token = "User Token 1"

                    showLoading(false)

                    viewModel.saveSession(UserModel(userId, name, email, token))
                    AlertDialog.Builder(this@LoginActivity).apply {
                        setTitle("Yeah!")
                        setMessage("Anda berhasil login. Selamat datang $name")
                        setPositiveButton("Lanjut") { _, _ ->
                            val intent = Intent(context, BottomNavActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                        create()
                        show()
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
        val messageTextView = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(200)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(200)
        val emailText = ObjectAnimator.ofFloat(binding.emailText, View.ALPHA, 1f).setDuration(200)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(200)
        val passwordText = ObjectAnimator.ofFloat(binding.passwordText, View.ALPHA, 1f).setDuration(200)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(200)

        AnimatorSet().apply {
            playSequentially(
                titleTextView,
                messageTextView,
                emailTextView,
                emailText,
                passwordTextView,
                passwordText,
                login
            )
            start()
        }
    }

}