package com.lululalal.wishlist

sealed class Screen(val route:String) { //navigation을 위해 사용할 화면 등록
    //sealed - 상속 불가능 하도록
    object HomeScreen : Screen("home_screen")
    object AddScreen : Screen("add_screen")
}

