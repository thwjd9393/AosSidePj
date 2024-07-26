package com.lululalal.locationapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LocationViewModel : ViewModel() {
    private val _location = mutableStateOf<LocationData?>(null)
    val location: State<LocationData?> = _location

    //위치 업데이트 하는 함수
    fun updateLocation(newLocation:LocationData) {
        _location.value = newLocation
    }
}