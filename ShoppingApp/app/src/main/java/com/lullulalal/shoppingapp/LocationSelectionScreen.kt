package com.lullulalal.shoppingapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

//두가지 매개변수 필요
//1.실제 위치 : 화면이나 지도레서 선택해서 얻은 위도 경도
//2.위치 선택했을 때 실행하려는 것, 선택한 위치로 어떤 것을 해야하는지
@Composable
fun LocationSelectionScreen(
    lacation : LocationData,
    onLocationSelected : (LocationData) -> Unit
) {
    //실제 사용자 위치
    val userLocation = remember{
        mutableStateOf(LatLng(lacation.latitude, lacation.longitude))
    }

    //구글 지도 선택지에서 내 위치 찾기
    var cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLocation.value, 10f) //내 위지(핸드폰 위치)와 그 위치 기준으로 얼마나 확대해 보여줄건지
    }

    Column(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp),
            cameraPositionState = cameraPositionState,
            onMapClick = {
                userLocation.value = it //지도에서 클릭한 it이 가르키는 LatLng 클래스가 userLocation에 들어가도록
            }
        ) {
            Marker(state = MarkerState(position = userLocation.value))
        }

        var newLocation : LocationData

        Button(onClick = {
            /* 버튼 클릭 시 위지 셋팅 */
            newLocation = LocationData(
                userLocation.value.latitude,
                userLocation.value.longitude)
            onLocationSelected(newLocation) //매개변수로 설정한 놈, 위치 클릭하면 위도 경도 가져옴
        }) {
            Text(text = "Set Location")
        }
    }
}


