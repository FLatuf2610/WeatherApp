package com.example.apipractice.data.local

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class LocationService @Inject constructor() {
    @SuppressLint("MissingPermission")
    suspend fun getUserLocation(context: Context):Location?{
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                    locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!isGPSEnabled){
            Log.println(Log.ASSERT, "NO GPS", "Mostra algo en pantalla")
            Toast.makeText(context, "GPS is not enabled", Toast.LENGTH_SHORT).show()
            return null
        }
            return suspendCancellableCoroutine { cont ->
                fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                    .addOnSuccessListener {
                        cont.resume(it)
                    }
                    .addOnFailureListener {
                        cont.resumeWithException(it)
                    }
                    .addOnCanceledListener {
                        cont.resume(null)
                    }
            }

        /*return suspendCancellableCoroutine { cont ->
            fusedLocationProviderClient.lastLocation.apply {
                if (isComplete){
                    if (isSuccessful){
                        cont.resume(result){}
                    }else{
                        cont.resume(null){}
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    cont.resume(it){}
                }
            }
        }*/
    }
}