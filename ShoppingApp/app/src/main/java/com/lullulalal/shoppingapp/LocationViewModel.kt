package com.lullulalal.shoppingapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LocationViewModel : ViewModel() {
    //우리 위치를 관리해 주는 뷰모델

    private val _location = mutableStateOf<LocationData?>(null)
    val location : State<LocationData?> = _location

    //새로운 위치가 생겼을 때 업데이트 하는 함수
    fun updateLocation(newLocation:LocationData) {
        _location.value = newLocation //뷰 모델 안 상태를 State 머신이 관리해줌
    }

}