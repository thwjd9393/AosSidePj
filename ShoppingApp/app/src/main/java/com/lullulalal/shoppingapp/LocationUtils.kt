package com.lullulalal.shoppingapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import java.util.Locale

class LocationUtils(val context: Context) {

    //FusedLocationProviderClient -> 위도 경도 찾을 수 있음
    private val _fusedLocationClient : FusedLocationProviderClient
        = LocationServices.getFusedLocationProviderClient(context)

    //실제 위치를 탐색할 함수
    @SuppressLint("MissingPermission")
    fun requestLocationUpdate(viewModel: LocationViewModel) {
        //Callback : 무언가를 요청한 후 해당 작업이 완료될 때까지 기다린 다음 반환해주는 것
        //시간이 걸릴 것 같은 경우 이 콜백 써준다
        val locationCallback = object : LocationCallback(){
            //클래스에서 상속받아 오버라이드 하는 대신 객체를 직접 만들고 그 내에서 바로 오버라이드 가능
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                //locationResult.lastLocation : 제일 최근 위치 반환
                locationResult.lastLocation?.let {
                    //let 함수 안에서 it이 정보를 보여하고 있음
                    val location = LocationData(latitude = it.latitude, longitude = it.longitude) //it함수 안에서 정보 추출
                    viewModel.updateLocation(location) //뷰 모델에 위도경도 업데이트
                }
            }
        }

        //위치 요청 빌더 만들기
        //LocationRequest.Builder(위치 정확도, 얼마나 자주 탐색할것인지).build()
        //Priority엔 4가지 속성 있음
        //정확도가 높을 수록 배터리 많이 사용
        //1.PRIORITY_HIGH_ACCURACY : 높은 정확도
        //2.PRIORITY_LOW_POWER : 배터리 덜 사용
        //3.PRIORITY_BALANCED_POWER_ACCURACY : 배터리와 정확도 균형
        //4.PRIORITY_PASSIVE : 배터리 아주 적게 사용
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()

        //_fusedLocationClient의 requestLocationUpdates 매소드 사용하여 모든것을 하나로 묶기
        _fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        //-> 해당 기능을 사용하고 싶을 떄마다 권한을 요청해야됨
        //하지만 실제로 권한이 있을 떄만 이 함수를 실행할것이기떄문에 퍼미션을 무시하라는 어노테이션 붙여주기 : @SuppressLint("MissingPermission")
        //Looper.getMainLooper() : 위치 업데이트를 위한 threading과 메세지 처리하는 데 사용함
        //특정 looper를 제공해 어떤 스레드 위치 업데이트가 전달 될 지 정할 수 있음 지금은 메인 루퍼 전달함

    }

    //위치 권한에 액세스가 있는지 없는지 확인하는 함수
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

    fun reverseGeoDecodeLocation(location: LocationData) : String {
        val geocoder = Geocoder(context, Locale.getDefault())
        //Geocoder(Context, 언어 설정 (Locale.getDefault()는 폰의 기본 언어 설정 부름))
        val coordinator = LatLng(location.latitude,location.longitude)
        //구글 맵에서 제공하는 LatLng매소드에 좌표 설정
        val addresses : MutableList<Address>? =
            geocoder.getFromLocation(
                coordinator.latitude,
                coordinator.longitude,
                1)
        //특정 위치에 맞는 주소가 여러개 있을 수 있기 때문에 MutableList로 받음
        //geocoder.getFromLocation(위도,경도,찾은 것중 리턴할 주소 갯수)

        return if (addresses?.isNotEmpty() == true) {
            addresses[0].getAddressLine(0)
        } else {
            "찾은 주소가 없습니다"
        }

    }

}