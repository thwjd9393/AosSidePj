package com.lululalal.locationapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class LocationUtils(val context: Context) {

    //위치 권한에 액세스가 있는지 없는지 확인하는 함수
//    fun hasLocationPermission(context: Context):Boolean {
//        if (ContextCompat.checkSelfPermission(
//                context,
//                Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
//            return true
//        } else {
//            return false
//        }
//    }

    //쓸데없는 if문 삭제하고 리턴에 바로 쓰기
    fun hasLocationPermission(context: Context):Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED //PERMISSION_GRANTED문자열이 ACCESS_FINE_LOCATION에 있는지 체크하는 부분
                &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    }
    //checkSelfPermission는 리턴 값이 int임 우린 Boolean이 필요 떄문에 PERMISSION_GRANTED와 같은 지 체크하여 액세스 승인했는지 체크함

}