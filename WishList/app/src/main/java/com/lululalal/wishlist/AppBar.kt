package com.lululalal.wishlist

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class) //TopAppBar 사용하기 위해서 추가해야됨
@Composable
fun AppBarView(
    title:String,
    onBackNavClicked:() -> Unit = {} //초기에는 아무것도 안할거라 비워둠
) {

    //나중에 이 이아콘을 보이거나 보이지 않도록 수정하기 위해 따로 정의
    val navigationIcon : (@Composable () -> Unit)? = {

        //필요할 때만 보이도록 설정
        if (!title.contains("WishList")) {
            IconButton(onClick = { onBackNavClicked() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    tint = Color.White,
                    contentDescription = "home"
                )
            }
        } else {
            null
        }
    }

    TopAppBar( //TopAppBar를 따로 관리하면 유연함을 확보할 수 있음
        title = {
        Text(text = title,
            color = colorResource(id = R.color.white),
            modifier = Modifier
                .padding(start = 4.dp)
                .heightIn(max = 24.dp))
    },
        elevation = 3.dp,
        backgroundColor = colorResource(id = R.color.app_bar_color),
        navigationIcon = navigationIcon
    )
}