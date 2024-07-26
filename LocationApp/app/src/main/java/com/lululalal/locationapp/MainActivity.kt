package com.lululalal.locationapp

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lululalal.locationapp.ui.theme.LocationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel:LocationViewModel = viewModel()

            LocationAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp(viewModel)
                }
            }
        }
    }
}

@Composable
fun MyApp(viewModel: LocationViewModel) {//부모 컴프저블 만들기
    val context = LocalContext.current //지금 내가 있는 액티비티의 context 요청
    val locationUtils = LocationUtils(context)
    LocationDisplay(locationUtils = locationUtils, viewModel,context = context)
}

@Composable
fun LocationDisplay(
    locationUtils: LocationUtils,
    viewModel: LocationViewModel,
    context: Context
) {
    //[1]권한 받기
    //1. 권한 런처 표시
    // rememberLauncherForActivityResult 사용
    // 결과를 위해 액티비티를 시작 요청을 등록하는 것, 보이지 않는 곳에서 자동으로 관리 요청 코드 및 변환과 관련된 레코드가 레지스트리에 생성됨
    //2. 권한이 왜 필요한지 알려줘야함
    // rationaleRequired의 shouldShowRequestPermissionRationale() 메소드를 사용하여 왜 이 퍼미션이 필요한지에 대한 이유를 표시한다.

    //[2]위치 데이터 얻기
    //1.구글에서 제공하는 위도 경도 정보 얻어주는 것 그래이들에 추가
    //2.위도 경도 데이터 클래스와 voewmodel작성
    //3.LocationUtils에 위도 경도 받아오는 함수 작성
    //4.display할 곳에서 위도 경도 지켜보는 뷰모델 매개변수로 받기 및 퍼미션 허용 했을 때 3번에서 작성한 함수 실행 하도록 호출
    //5.위도 경도 화면에 표시해주기 위해 viewModel에 있는 자료형이 State인 location의 value값 가져오기

    val location = viewModel.location.value

    val address = location?.let {
        locationUtils.reverseGeoDecodeLocation(location)
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(), //contract는 permissions를 리턴함
        onResult = {permissions ->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION]==true
                && permissions[Manifest.permission.ACCESS_FINE_LOCATION]==true ) {
                //위치에 권한 있다
                //위도 -경도 셋팅하는 함수 호출!!
                locationUtils.requestLocationUpdate(viewModel)

            } else {
                //권한 요청 필요
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity, //메인 액티비치 말곤 다른창에선 이 팝업 열지말라는 등록
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                
                //이 퍼미션이 필요한 이유를 설명해주기
                if (rationaleRequired) {
                    Toast.makeText(context, "이 기능을 사용하려면 위치 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
                } else {
                    //rationaleRequired이 거부 상황일경우 사용자의 설정이나 안드로이드 폰 설정으로 이동
                    Toast.makeText(context, "설정에 들어가 위치권한을 활성화 해주세요.", Toast.LENGTH_SHORT).show()
                }
                
            }
        }) //contract에서 리턴받은 permissions가 모두 허용됐는지 체크하는 부분

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (location != null) {
            Text(text = "Address = ${location.longitude} : ${location.latitude} \n 이 곳은? $address")
        } else {
            Text(text = "Location not available")
        }

        Button(onClick = {
            if (locationUtils.hasLocationPermission(context)) {
                //이미 권한이 있으면 엡데이트
            } else {
                //위치 권한 요청

                //여기서 실제로 포미션 요청을 런칭함!
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }) {
            Text(text = "get location")
        }
    }
}

