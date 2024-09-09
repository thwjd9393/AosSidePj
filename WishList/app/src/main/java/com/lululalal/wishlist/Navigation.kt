package com.lululalal.wishlist

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel //네비게이션에서 사용할 뷰모델 임포트는 이거
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

//NavHost와 NavController를 포함한 Composable
@Composable
fun Navigation(viewModel: WishViewModel = viewModel(),
               navController: NavHostController = rememberNavController()) {
    //NavController에 초기상태 rememberNavController()를 전달 해둠
    //기본적으로 ViewModel을 쓰고 생성하는 navController 객체들을 기억하라고 명령해둠

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        //화면이 될 composable 추가
        composable(Screen.HomeScreen.route) {
            HomeView(navController, viewModel)
        }

        composable(Screen.AddScreen.route+"/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                }
            )
            ) { entry ->
            val id = if(entry.arguments != null) entry.arguments!!.getLong("id") else 0L
            AddEditDetailView(id = id, wishViewModel = viewModel, navController = navController)
        }
    }

}