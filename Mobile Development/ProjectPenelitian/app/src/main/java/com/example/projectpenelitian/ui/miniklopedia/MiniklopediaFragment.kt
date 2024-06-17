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
import com.example.projectpenelitian.adapter.MiniklopediaAdapter
import com.example.projectpenelitian.api.response.DataItem
import com.example.projectpenelitian.data.pref.dataStore
import com.example.projectpenelitian.api.response.MiniklopediaResponse
import com.example.projectpenelitian.databinding.FragmentMiniklopediaBinding
import com.example.projectpenelitian.ui.login.LoginViewModel
import com.example.projectpenelitian.ui.welcome.WelcomeActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MiniklopediaFragment : Fragment() {

    private var _binding: FragmentMiniklopediaBinding? = null
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

        _binding = FragmentMiniklopediaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
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

            val miniklopediaViewModel = ViewModelProvider(this@MiniklopediaFragment).get(
                MiniklopediaViewModel::class.java)
            miniklopediaViewModel.setParameter(token_string)
            miniklopediaViewModel.miniklopedia.observe(viewLifecycleOwner) { miniklopedia ->
                cekData(miniklopedia)
            }
            miniklopediaViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvId.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvId.addItemDecoration(itemDecoration)

    }

    private fun refreshData() {
        var token_string = ""
        val token = stringPreferencesKey("token")
        lifecycleScope.launch {
            val session = requireContext().applicationContext.dataStore.data.first()[token]
            token_string = session.toString()

            val miniklopediaViewModel = ViewModelProvider(this@MiniklopediaFragment).get(
                MiniklopediaViewModel::class.java)
            miniklopediaViewModel.setParameter(token_string)
            miniklopediaViewModel.miniklopedia.observe(viewLifecycleOwner) { miniklopedia ->
                cekData(miniklopedia)
            }
            miniklopediaViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        }
    }

    private fun cekData(miniklopedia: MiniklopediaResponse) {
        val message = miniklopedia.message
        if (message != "Succecss") {
            binding.tvMessage.visibility = View.GONE
            Snackbar.make(
                requireActivity().window.decorView.rootView,
                "Error $message",
                Snackbar.LENGTH_SHORT
            ).show()

        } else {
            val listMiniklopedia_count = miniklopedia.data.size
            if (listMiniklopedia_count > 0) {
                binding.tvMessage.visibility = View.GONE

                val miniklopediaViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                    MiniklopediaViewModel::class.java)
                miniklopediaViewModel.listMiniklopedia.observe(viewLifecycleOwner) { listMniklopedia ->
                    setMiniklopediaListData(listMniklopedia)
                }
                miniklopediaViewModel.isLoading.observe(viewLifecycleOwner) {
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

    private fun setMiniklopediaListData(miniklopediaList: List<DataItem>) {
        val adapter = MiniklopediaAdapter(this@MiniklopediaFragment)
        adapter.submitList(miniklopediaList)
        binding.rvId.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.tvMessage.visibility = View.GONE
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}