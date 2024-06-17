package com.example.projectpenelitian.ui.miniklopedia

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectpenelitian.R
import com.example.projectpenelitian.ViewModelFactory
import com.example.projectpenelitian.adapter.BookmarkAdapter
import com.example.projectpenelitian.api.response.BookmarkDataItem
import com.example.projectpenelitian.api.response.BookmarkResponse
import com.example.projectpenelitian.data.pref.dataStore
import com.example.projectpenelitian.databinding.FragmentBookmarkBinding
import com.example.projectpenelitian.ui.login.LoginViewModel
import com.example.projectpenelitian.ui.welcome.WelcomeActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

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

        var token_string = ""
        val token = stringPreferencesKey("token")
        lifecycleScope.launch {
            val session = requireContext().applicationContext.dataStore.data.first()[token]
            token_string = session.toString()

            val bookmarkViewModel = ViewModelProvider(this@BookmarkFragment).get(
                BookmarkViewModel::class.java)
            bookmarkViewModel.setParameter(token_string)
            bookmarkViewModel.bookmark.observe(viewLifecycleOwner) { bookmark ->
                cekData(bookmark)
            }
            bookmarkViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.bookmarkRv.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.bookmarkRv.addItemDecoration(itemDecoration)

    }

    fun refreshData() {
        var token_string = ""
        val token = stringPreferencesKey("token")
        lifecycleScope.launch {
            val session = requireContext().applicationContext.dataStore.data.first()[token]
            token_string = session.toString()

            val bookmarkViewModel = ViewModelProvider(this@BookmarkFragment).get(
                BookmarkViewModel::class.java)
            bookmarkViewModel.setParameter(token_string)
            bookmarkViewModel.bookmark.observe(viewLifecycleOwner) { bookmark ->
                cekData(bookmark)
            }
            bookmarkViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        }
    }

    private fun cekData(bookmark: BookmarkResponse) {
        val message = bookmark.message
        if (message != "Succecss") {
            binding.tvMessage.visibility = View.GONE
            Snackbar.make(
                requireActivity().window.decorView.rootView,
                "Error $message",
                Snackbar.LENGTH_SHORT
            ).show()

        } else {
            val listBookmark_count = bookmark.data.size
            if (listBookmark_count > 0) {
                binding.tvMessage.visibility = View.GONE

                val bookmarkViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                    BookmarkViewModel::class.java)
                bookmarkViewModel.listBookmark.observe(viewLifecycleOwner) { listBookmark ->
                    setBookmarkListData(listBookmark)
                }
                bookmarkViewModel.isLoading.observe(viewLifecycleOwner) {
                    showLoading(it)
                }
            } else {
                binding.tvMessage.text = getString(R.string.data_not_found)
                binding.tvMessage.visibility = View.VISIBLE

                Snackbar.make(
                    requireActivity().window.decorView.rootView,
                    getString(R.string.data_not_found),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
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