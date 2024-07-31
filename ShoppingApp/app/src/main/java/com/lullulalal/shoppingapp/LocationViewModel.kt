package com.lullulalal.shoppingapp

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LocationViewModel : ViewModel() {
    //우리 위치를 관리해 주는 뷰모델

    private val _location = mutableStateOf<LocationData?>(null)
    val location : State<LocationData?> = _location

    private val _address = mutableStateOf(listOf<GeocordingResult>())
    val address:State<List<GeocordingResult>> = _address

    //새로운 위치가 생겼을 때 업데이트 하는 함수
    fun updateLocation(newLocation:LocationData) {
        _location.value = newLocation //뷰 모델 안 상태를 State 머신이 관리해줌
    }

    //주소 얻는 함수
    fun fetchAddress(latLng:String) {
        try {
            viewModelScope.launch {
                val result = RetrofitClient.create()
                    .getAddressFromCoordinates(latLng, "")

                _address.value = result.results //리스트의 첫번째 항목을 가져와 화면에 표시할 것임
            }
        } catch (e: Exception) {
            Log.d("TAG","${e.message}")
        }
    }

}