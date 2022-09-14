package com.geonwoo.betterthanyesterday

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.geonwoo.betterthanyesterday.databinding.FragmentWeatherBinding
import com.geonwoo.betterthanyesterday.viewmodels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    private val viewModel: WeatherViewModel by viewModels()

    private val cal: Calendar = Calendar.getInstance()
    private val today: String = SimpleDateFormat("yyyyMMdd").format(cal.time)
    private val yesterday: String = SimpleDateFormat("yyyyMMdd").format(cal.apply { add(Calendar.DATE, -1) }.time)

    private val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private val requestMultiplePermissions =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            if (it.all { permission -> permission.value }) {
                bindData()
            } else {
                showToast("권한 거부")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)

        if (checkPermission(permissions)) {
            bindData()
        } else {
            requestMultiplePermissions.launch(permissions)
        }

        return binding.root
    }

    private fun subscribeUI() {
        viewModel.weatherLiveData.observe(viewLifecycleOwner) {
            binding.yesterdayTemp = it[yesterday]?.obsrValue?.toFloat() ?: 0f
            binding.todayTemp = it[today]?.obsrValue?.toFloat() ?: 0f
            binding.baseTime = it[yesterday]?.baseTime
        }
    }

    private fun bindData() {
        getLocation()?.let { location ->
            subscribeUI()
            getWeatherResponse(location.latitude, location.longitude)
        }

        binding.settingView.setOnClickListener {
            SettingFragment().show(parentFragmentManager, "setting")
        }
    }

    private fun getWeatherResponse(lat: Double, lon: Double) {
        val latLng = LatXLonY(lat, lon)

        val baseTimeMap = mapOf<String, String>(
            today to SimpleDateFormat("HHmm").format(Calendar.getInstance().apply {
                if (get(Calendar.MINUTE) < 40) {
                    add(Calendar.HOUR, -1)
                }
            }.time),
            yesterday to SimpleDateFormat("HHmm").format(Calendar.getInstance().apply {
                add(Calendar.MINUTE, 2)
            }.time),
        )

        viewModel.getWeatherData(
            baseTimeMap,
            latLng.x.toInt(),
            latLng.y.toInt()
        )
    }

    private fun checkPermission(permissions: Array<String>): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(
                requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun getLocation(): Location? {
        if (checkPermission(permissions)) {
            val locationManager =
                requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager

            return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        }

        return null
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}