package com.lullulalal.shoppingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.lullulalal.shoppingapp.ui.theme.ShoppingAppTheme
import com.mbsysoft.myshoppinglistapp.ShoppingListApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Navigation()

                }
            }
        }
    }
}

//navigation을 사용하여 쇼핑리스트와 지도맵 화면을 바꿔줘야함
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val viewModel: LocationViewModel = viewModel()
    val context = LocalContext.current
    val locationUtil = LocationUtils(context = context)

    NavHost(navController, startDestination = "shoppinglistscreen"){
        composable("shoppinglistscreen") {
            ShoppingListApp(
                locationUtils = locationUtil,
                viewModel = viewModel,
                navController = navController,
                context = context,
                address = viewModel.address.value.firstOrNull()?.formatted_address ?: "No Address"
                //firstOrNull() : list에 있는 함수로 찾아온 항목이 몇개든지 첫번쨰 아이템만 필요하다는 뜻
            )
        }

        //NavHost는 전체화면 차지하는 composable만 사용하는 것이 아니라 dialog도 사용가능함
        dialog("locationscreen") {
                backStack-> viewModel.location.value?.let {
                    //it이 부르는 lacationCata가 locationSelectionScreen에 위치로 사용가능해짐
                    LocationSelectionScreen(lacation = it, onLocationSelected = {
                        viewModel.fetchAddress("${it.latitude}, ${it.longitude}")
                        navController.popBackStack() //팝업에서 뒤로가기 누르면 스택에서 삭제
                    })
            }
        }
    }
}