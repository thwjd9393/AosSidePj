package com.lullulalal.navigationsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lullulalal.navigationsample.ui.theme.NavigationSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    myApp()
                }
            }
        }
    }
}

@Composable
fun myApp() {
    //NavController는 중심이 되는 API다
    //앱 화면을 만드는 백 스택과 각 화면의 상태를 유지함
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "firstScreen") {
        //composable => 경로와 필요한 경우 인수들을 갖을 수 있다
        //route는 앱 어디서든 화면을 식별하는데 사용함
        composable(route = "firstScreen"){
            //firstScreen을 경로로 하는 컴포저블이 실행될 때 무슨 일이 일어나야 하는지 정의
            FirstScreen {
                navController.navigate("secondScreen")
            }
        }
        composable(route = "secondScreen"){
            SecondScreen{
             navController.navigate("thirdScreen")
            }
        }
        composable("thirdScreen") {
            ThirdScreen {
                navController.navigate("firstScreen")
            }
        }
    }
}

