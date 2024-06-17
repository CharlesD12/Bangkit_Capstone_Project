package com.example.projectpenelitian.ui.miniklopedia

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectpenelitian.BottomNavActivity
import com.example.projectpenelitian.R
import com.example.projectpenelitian.ViewModelFactory
import com.example.projectpenelitian.adapter.BookmarkAdapter
import com.example.projectpenelitian.api.response.BookmarkDataItem
import com.example.projectpenelitian.api.response.BookmarkResponse
import com.example.projectpenelitian.api.response.ErrorResponse
import com.example.projectpenelitian.data.dataClass.UserLoginData
import com.example.projectpenelitian.data.pref.UserModel
import com.example.projectpenelitian.data.pref.dataStore
import com.example.projectpenelitian.databinding.FragmentBookmarkBinding
import com.example.projectpenelitian.ui.login.LoginViewModel
import com.example.projectpenelitian.ui.welcome.WelcomeActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.wensolution.storyapp.apiservice.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.HttpException

class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(BookmarkViewModel::class.java)

        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin) {
                startActivity(Intent(requireContext(), WelcomeActivity::class.java))
                requireActivity().finish()
            }
        }

        lifecycleScope.launch {
            showLoading(true)

            try {
                var token_string = ""
                val token = stringPreferencesKey("token")
                val session = requireContext().applicationContext.dataStore.data.first()[token]
                token_string = session.toString()

                val apiService = ApiConfig.getApiService()
                val successResponse = apiService.getBookmark("Bearer ${token_string}")

                showLoading(false)
                if (successResponse.message == "Succecss"){
                    cekData(successResponse)
                }else{
                    binding.bookmarkRv.visibility = View.GONE
                    binding.tvMessage.text = "Data kosong"
                    binding.tvMessage.visibility = View.VISIBLE
                    // showToast("Data kosong")
                }
            } catch (e: HttpException) {
                showLoading(false)
                binding.bookmarkRv.visibility = View.GONE

                val errorBody = e.response()?.errorBody()?.string() ?: "Data error, please try again"
                val errorMessage  = Gson().fromJson(errorBody, ErrorResponse::class.java)
                var errorMessage2 = errorMessage.message
                if (errorMessage2 == null)
                {
                    errorMessage2 = "Data error, please try again"
                }

                binding.tvMessage.text = errorMessage2
                binding.tvMessage.visibility = View.VISIBLE
                // showToast(errorMessage2)
            }
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.bookmarkRv.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.bookmarkRv.addItemDecoration(itemDecoration)

    }

    fun refreshData() {
        lifecycleScope.launch {
            showLoading(true)

            try {
                var token_string = ""
                val token = stringPreferencesKey("token")
                val session = requireContext().applicationContext.dataStore.data.first()[token]
                token_string = session.toString()

                val apiService = ApiConfig.getApiService()
                val successResponse = apiService.getBookmark("Bearer ${token_string}")

                showLoading(false)
                if (successResponse.message == "Succecss"){
                    cekData(successResponse)
                }else{
                    binding.bookmarkRv.visibility = View.GONE
                    binding.tvMessage.text = "Data kosong"
                    binding.tvMessage.visibility = View.VISIBLE
                    // showToast("Data kosong")
                }
            } catch (e: HttpException) {
                showLoading(false)
                binding.bookmarkRv.visibility = View.GONE

                val errorBody = e.response()?.errorBody()?.string() ?: "Data error, please try again"
                val errorMessage  = Gson().fromJson(errorBody, ErrorResponse::class.java)
                var errorMessage2 = errorMessage.message
                if (errorMessage2 == null)
                {
                    errorMessage2 = "Data error, please try again"
                }

                binding.tvMessage.text = errorMessage2
                binding.tvMessage.visibility = View.VISIBLE
                // showToast(errorMessage2)
            }
        }
    }

    private fun cekData(bookmark: BookmarkResponse) {
        binding.bookmarkRv.visibility = View.VISIBLE
        binding.tvMessage.visibility = View.GONE
        setBookmarkListData(bookmark.data)
    }

    private fun setBookmarkListData(bookmarkList: List<BookmarkDataItem>) {
        val adapter = BookmarkAdapter(this@BookmarkFragment)
        adapter.submitList(bookmarkList)
        binding.bookmarkRv.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.tvMessage.visibility = View.GONE
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}