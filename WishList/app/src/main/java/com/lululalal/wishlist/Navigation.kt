package com.lululalal.wishlist

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel //네비게이션에서 사용할 뷰모델 임포트는 이거
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

//NavHost와 NavController를 포함한 Composable
@Composable
fun Navigation(viewModel: WishViewModel = viewModel(),
               navController: NavHostController = rememberNavController()) {
    //NavController에 초기상태 rememberNavController()를 전달 해둠

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        //화면이 될 composable 추가
        composable(Screen.HomeScreen.route) {
            HomeView()
        }
    }

}