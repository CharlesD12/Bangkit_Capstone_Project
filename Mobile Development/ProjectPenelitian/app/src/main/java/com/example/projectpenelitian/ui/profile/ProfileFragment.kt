package com.example.projectpenelitian.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.projectpenelitian.ViewModelFactory
import com.example.projectpenelitian.api.response.ErrorResponse
import com.example.projectpenelitian.data.dataClass.UserEditData
import com.example.projectpenelitian.data.pref.dataStore
import com.example.projectpenelitian.databinding.FragmentProfileBinding
import com.example.projectpenelitian.ui.login.LoginViewModel
import com.example.projectpenelitian.ui.welcome.WelcomeActivity
import com.google.gson.Gson
import com.wensolution.storyapp.apiservice.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!
    private lateinit var context: Context

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(context)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set data from datastore
        lifecycleScope.launch {
            val name = stringPreferencesKey("name")
            val email = stringPreferencesKey("email")
            val token = stringPreferencesKey("token")

            val sessionName = context.dataStore.data.first()[name]
            val sessionEmail = context.dataStore.data.first()[email]
            val sessionToken = context.dataStore.data.first()[token]
            val tokenString = "Bearer ${sessionToken.toString()}"

            binding.nameText.setText(sessionName)
            binding.emailText.setText(sessionEmail)
            binding.emailText.setEnabled(false);
        }


        // validasi button update
        setMyButtonEnable()
        binding.nameText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) { }
        })
        binding.emailText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) { }
        })
        binding.passwordText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }
            override fun afterTextChanged(s: Editable) { }
        })

        // button update onclick
        binding.btnUpdate.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Profile")
            builder.setMessage("Apakah Anda yakin ingin memperbarui profile?")

            // jika alert yes
            builder.setPositiveButton("Yes") { dialog, which ->
                updateData()
            }

            // Tambahkan tombol "No"
            builder.setNegativeButton("No") { dialog, which ->

            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        // button logout onclick
        binding.btnLogout.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Logout")
            builder.setMessage("Apakah Anda yakin ingin mengakhiri session ini?")

            // jika alert yes
            builder.setPositiveButton("Yes") { dialog, which ->
                viewModel.logout()

                val alertDialogBuilder = AlertDialog.Builder(requireContext())
                alertDialogBuilder.apply {
                    setTitle("Yeah!")
                    setMessage("Anda berhasil logout")
                    setPositiveButton("Lanjut") { _, _ ->
                        val intent = Intent(requireContext(), WelcomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        requireActivity().finish()
                    }
                    create()
                    show()
                }
            }

            // Tambahkan tombol "No"
            builder.setNegativeButton("No") { dialog, which ->

            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    private fun updateData() {
        showLoading(true)

        // update profile api
        lifecycleScope.launch {
            try {

                val name = binding.nameText.text.toString()
                val password = binding.passwordText.text.toString()
                val token = stringPreferencesKey("token")
                val sessionToken = context.dataStore.data.first()[token]
                val tokenString = "Bearer ${sessionToken.toString()}"
                val requestBody = UserEditData(
                    name = name,
                    password = password
                )
                val apiService = ApiConfig.getApiService()
                val successResponse = apiService.updateProfile(tokenString, requestBody)

                showLoading(false)

                if(successResponse.message == "Success"){
                    viewModel.logout()

                    val alertDialogBuilder = AlertDialog.Builder(requireContext())
                    alertDialogBuilder.apply {
                        setTitle("Data berhasil diperbarui!")
                        setMessage("Silahkan login kembali")
                        setPositiveButton("Lanjut") { _, _ ->
                            val intent = Intent(requireContext(), WelcomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            requireActivity().finish()
                        }
                        create()
                        show()
                    }
                }else{
                    Toast.makeText(requireContext(), successResponse.message, Toast.LENGTH_SHORT).show()
                }

            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string() ?: "Data error, please try again"
                val errorMessage  = Gson().fromJson(errorBody, ErrorResponse::class.java)
                var errorMessage2 = errorMessage.message
                if (errorMessage2 == null)
                {
                    errorMessage2 = "Data error, please try again"
                }

                Toast.makeText(requireContext(), errorMessage2, Toast.LENGTH_SHORT).show()
                showLoading(false)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    // set button update enable
    private fun setMyButtonEnable() {
        var validasi = true
        binding.btnUpdate.isEnabled = false

        val myName = binding.nameText.text
        val myEmail = binding.emailText.text
        if (
            myName == null || myName.toString().length < 8
            || myEmail == null || !Patterns.EMAIL_ADDRESS.matcher(myEmail.toString().trim()).matches()
            )
        {
            validasi = false
        }

        val myPassword = binding.passwordText.text
        if(myPassword.toString() != "" && myPassword.toString().length < 8)
        {
            validasi = false
        }

        if(validasi)
        {
            binding.btnUpdate.isEnabled = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}