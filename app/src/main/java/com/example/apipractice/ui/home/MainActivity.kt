package com.example.apipractice.ui.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.apipractice.R
import com.example.apipractice.databinding.ActivityMainBinding
import com.example.apipractice.domain.models.WeatherState
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel>()

    private lateinit var mAdapterHourly: HourlyAdapter
    private lateinit var mAdapterDaily: DailyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        checkPermissionsAndGetLocation()
    }




    private fun initUI(){
        lifecycleScope.launch {
            combine(mainViewModel.isLoading, mainViewModel.state) { isLoading, state ->
                binding.progressBar.isVisible = isLoading
                if(state != null){
                    binding.mainLayout.isVisible = !isLoading
                    binding.tvCurrentTemperature.text = state.current.temp.toInt().toString() + "°"
                    binding.tvCurrentWeather.text = state.current.weather[0].main

                    //Set City Name
                    val listLocation = state.timezone.split("/")
                    val n = listLocation.size
                    val country = listLocation[n-2]
                    var  city = listLocation[n-1]
                    if (city.contains("_")){
                        val citySplit = city.split("_")
                        city = ""
                        citySplit.forEach {
                            city += it + " "
                        }
                        city = city.trim()
                        Log.println(Log.ASSERT, "City", city)
                    }
                    val location = country + ", " + city
                    binding.tvTimezone.text = location


                    val icon = state.current.weather[0].icon
                    Glide.with(this@MainActivity)
                        .load("https://openweathermap.org/img/wn/${icon}@2x.png")
                        .into(binding.ivCurrentWeatherIcon)

                    binding.tvUVValue.text = state.current.uvi.toString()
                    binding.tvWindValue.text = state.current.wind_speed.toInt().toString() + " Km/h"
                    binding.tvFeelsLikeValue.text = state.current.feels_like.toInt().toString() + "°"
                    binding.tvHumidityValue.text = state.current.humidity.toString() + "%"

                    initRVs(state)
                }
            }.collect()
        }
    }

    private fun initRVs(state: WeatherState){
        mAdapterHourly = HourlyAdapter(state.hourly)
        binding.rvHourlyWeather.apply {
            adapter = mAdapterHourly
            layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,
                false)
        }
        mAdapterDaily = DailyAdapter(state.daily)
        binding.rvDayWeather.apply {
            adapter = mAdapterDaily
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun checkPermissionsAndGetLocation(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION )
            != PackageManager.PERMISSION_GRANTED){
            requestLocationPermission()
        }
        else {
            val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                    locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if (isGPSEnabled){
                mainViewModel.onCreateViewModel(this)
            }
            else{
                MaterialAlertDialogBuilder(this)
                    .setMessage(getString(R.string.dialog_location_body))
                    .setPositiveButton("Accept"){ dialog, _ ->
                        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        startActivity(intent)
                        dialog.dismiss()
                    }
                    .setNegativeButton("No, thanks", null)
                    .create()
                    .show()
            }

        }

    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 777)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 777){
            if (grantResults.isNotEmpty() &&  grantResults[0] == PackageManager.PERMISSION_GRANTED){
                mainViewModel.onCreateViewModel(this)
            }

            else{
                //No permissions
                Toast.makeText(this, "App needs location permission to work",Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}