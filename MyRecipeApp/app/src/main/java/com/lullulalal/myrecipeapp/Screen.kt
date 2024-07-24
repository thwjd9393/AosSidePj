package com.lullulalal.myrecipeapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class Screen(val route:String) {
    //경로 탐색을 담당하는 클래스

    //sealed
    //서브 클래스의 자료형 중 하나를 따르는 자료형인 것을 미리 알 수 있을 때 사용함
    //런타임이 아니라 컴파일할 때 일치하는 자료형으로 제한하여 안정성을 보장함
    //이를 통해 실수로 경로의 이름을 틀리거나 한 경우 경로를 호출할 때 문제가 발생하지 않도록 할 수 있다
    //제네릭 같은거..라고 할 수 있나..

    //1. 매개변수로 val route:String 전달
    //2. 이동할 화면 만큼 객체(Object)를 만든다
    // 이동할 각 화면을 상수로 만드는데 sealed Screen 클래스를 이용해서 객체로 만들고 경로 이름을 전달하고 있음
    object RecipeScreen:Screen("recipeScreen")
    object RecipeDetailScreen:Screen("recipeDetailScreen")

}