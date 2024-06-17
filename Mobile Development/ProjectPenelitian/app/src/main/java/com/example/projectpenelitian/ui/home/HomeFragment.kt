package com.example.projectpenelitian.ui.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectpenelitian.R
import com.example.projectpenelitian.adapter.InformationAdapter
import com.example.projectpenelitian.databinding.FragmentHomeBinding
import com.example.projectpenelitian.model.Information
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.wensolution.storyapp.apiservice.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private lateinit var context: Context
    private lateinit var permissions: ActivityResultLauncher<Array<String>>
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.infoRv.layoutManager = LinearLayoutManager(context)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        permissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (it.containsValue(true)) {
                getDeviceLocation()
            }
        }

        if (!isLocationPermissionGranted()) {
            permissions.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
        } else {
            getDeviceLocation()
        }

        val infoNames = resources.getStringArray(R.array.info_name)
        val infoDesc1 = resources.getStringArray(R.array.info_desc_1)
        val infoDesc2 = resources.getStringArray(R.array.info_desc_2)
        val infoDesc3 = resources.getStringArray(R.array.info_desc_3)
        val infoImg = resources.obtainTypedArray(R.array.info_img)
        val infoImg2 = resources.obtainTypedArray(R.array.info_img2)
        val information = ArrayList<Information>()
        for (i in infoNames.indices) {
            information.add(Information(
                infoName = infoNames[i],
                infoDesc1 = infoDesc1[i],
                infoDesc2 = infoDesc2[i],
                infoDesc3 = infoDesc3[i],
                infoImg = infoImg.getResourceId(i, 0),
                infoImg2 = infoImg2.getResourceId(i, 0)
            ))
        }

        binding.infoRv.adapter = InformationAdapter(information)
        infoImg.recycle()
        infoImg2.recycle()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isLocationPermissionGranted(): Boolean {
        return (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) &&(ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
    }

    private fun getDeviceLocation() {
        try {
            if (isLocationPermissionGranted()) {
                val tokenSource: CancellationTokenSource = CancellationTokenSource()
                fusedLocationProviderClient.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    tokenSource.getToken()
                ).addOnSuccessListener { task ->
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val response = ApiConfig.getApiService()?.weather(task.latitude, task.longitude)
                            if (response?.isSuccessful == true) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    if (response.body()?.data?.isNotEmpty() == true) {
                                        binding.tempTxt.text = response.body()?.data?.get(0)?.temp.toString() + "°"
                                        binding.weatherTxt.text = response.body()?.data?.get(0)?.weather?.description
                                        binding.textView3.text = response.body()?.data?.get(0)?.cityName
                                        binding.textView4.text = response.body()?.data?.get(0)?.timezone
                                        Log.d("2504", response.body().toString())
                                    } else {
                                        Log.d("2504", "error")
                                    }
                                }
                            }
                        } catch (e: Exception) {
                            Log.d("2504", e.message, e)
                        }

                    }
                }.addOnFailureListener { e ->

                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }
}