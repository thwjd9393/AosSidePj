package com.lullulalal.myrecipeapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun ReciprApp(navController: NavHostController) {
    //NavHostController
    //NavHost에서 사용하기 위해 매개변수로 받는다

    //데이터 전달하기 위해 데이터 담당 부름
    val recipeViewModel : MainViewModel = viewModel() //데이터 가져오는 역할
    val viewState by recipeViewModel.categoriesState
    //getValue import -> state의 값을 바로 가져올 수 있도록 해주는 애
    //viewState를 이용해서 다른 화면으로 이동 뿐 아니라 RecipeScreen을 보여줌


    //startDestination에 경로를 가진 Screen 클래스 사용하기
    NavHost(navController = navController, startDestination = Screen.RecipeScreen.route) {
        composable(route = Screen.RecipeScreen.route) { //1.경로 설정
            RecipeScreen(viewState=viewState, navigateTorecipeDetailScreen = {
                //2. 이 부분이 현재 화면에서 상세 화면으로 데이터를 전달하는 역할을 한다

                //3.상세화면으로 보내줄 데이터 저장
                //navController.currentBackStackEntry => 현재 화면의 상태를 나타내는 부분 탐색 지점을 얻음
                //savedStateHandle => 다른 화면 간에 데이터를 전달하는 역할을 한다
                //set("cat",it) => savedStateHandle에 키-값 쌍을 저장함
                navController.currentBackStackEntry?.savedStateHandle?.set("cat",it)

                //4.상세 화면으로 이동
                navController.navigate(Screen.RecipeDetailScreen.route)
            })
        }
        composable(route = Screen.RecipeDetailScreen.route) {
            //1. 전달 받은 it:NavBackStackEntry -> previousBackStackEntry를 통해 전달받은 카테고리를 얻어옴
            val category = navController.previousBackStackEntry?.savedStateHandle?.
                get<Category>("cat") ?: Category("","","","")

            //2. 실제로 detail로 전달하기
            RecipeDetailScreen(category = category)
            
        }
    }
}