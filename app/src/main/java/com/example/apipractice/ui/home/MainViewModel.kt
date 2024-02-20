package com.example.apipractice.ui.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apipractice.data.local.LocationService
import com.example.apipractice.data.models.WeatherApiResponse
import com.example.apipractice.domain.GetWeatherUseCase
import com.example.apipractice.domain.models.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val locationService: LocationService,
    private val getWeatherUseCase: GetWeatherUseCase) :ViewModel(){


    private val _state = MutableStateFlow<WeatherState?>(null)
    val state:StateFlow<WeatherState?> = _state

    private val _isLoading = MutableStateFlow(false)
    val isLoading:StateFlow<Boolean> = _isLoading

    fun onCreateViewModel(context: Context) {
        viewModelScope.launch {
            _isLoading.emit(true)
            val deferredLocation = async { locationService.getUserLocation(context) }
            val result = deferredLocation.await()
            if (result != null) {
                val response = getWeatherUseCase(result)
                if (response != null){
                    _state.emit(response)
                    _isLoading.emit(false)
                }
                else {
                    _isLoading.emit(false)
                    Toast.makeText(context, "Something went wrong, try again later", Toast.LENGTH_LONG)
                }
            }
            else {
                _isLoading.emit(false)
                Toast.makeText(context, "Could not get location", Toast.LENGTH_SHORT).show()
            }
        }
    }

}