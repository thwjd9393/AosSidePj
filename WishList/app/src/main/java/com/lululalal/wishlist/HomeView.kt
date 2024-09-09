package com.lululalal.wishlist

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lululalal.wishlist.data.DummyWish
import com.lululalal.wishlist.data.Wish

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeView(
    navController: NavController,
    viewModel: WishViewModel
) {
    val context = LocalContext.current
    Scaffold(
        topBar = { AppBarView("WishList", {
        }) },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all=20.dp),
                contentColor = Color.White,
                backgroundColor = Color.Black,
                onClick = {
                    navController.navigate(Screen.AddScreen.route+"/0L")
                }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "추가")
            }
        }
    ) {
        //위시 리스트를 가져오기
        val wishList = viewModel.getAllWishes.collectAsState(initial = listOf()) //빈 목록으로 초기화

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(it)){
                //items에 key 설정하면 원하는 item이 삭제가 됨
                items(wishList.value, key={wish->wish.id}) {
                    wish ->
                    //스와이프 삭제
                    //삭제를 위한 코드
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if(it == DismissValue.DismissedToEnd
                                || it == DismissValue.DismissedToStart) {
                                viewModel.deleteWish(wish)
                            }
                            true //상태변화 확인
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        background = {
                            //val => state에서 작동하기 위해 변수 var 아닌 val로 선언
                            val color by animateColorAsState(
                                if (dismissState.dismissDirection == DismissDirection.EndToStart) Color.Red else Color.Transparent,
                                label = ""
                            )
                            val alignment = Alignment.CenterEnd
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(horizontal = 20.dp),
                                contentAlignment = alignment //contentAlignment는 Box에 {}가 있어야만 됨
                            ) {
                                Icon(Icons.Default.Delete,
                                    contentDescription = "삭제",
                                    tint = Color.White)
                            }
                        },
                        //방향 - 왼쪽에서부터 쓸지 오늘쪽에서부터 쓸지 정하는 것
                        directions = setOf(DismissDirection.EndToStart),
                        //어느 시점에서 dismiss 할 지
                        dismissThresholds = {FractionalThreshold(1f)},
                        //쓸어 넘긴 후 삭제할 내용
                        dismissContent = {
                            WishItem(wish = wish) {
                                val id = wish.id
                                navController.navigate(Screen.AddScreen.route + "/$id")
                            }
                        }
                    )
            }
        }
    }
}

//항목 이미지 만들기
@Composable
fun WishItem(wish: Wish, onClick: () -> Unit) {
    //onClick 하면 디테일 페이지로 이동
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, start = 8.dp, end = 8.dp)
        .clickable {
            onClick()
        },
        elevation = 10.dp,
        backgroundColor = Color.White,
        ) {
        Column(modifier = Modifier
            .padding(16.dp)) {
            Text(text = wish.title, fontWeight = FontWeight.ExtraBold)
            Text(text = wish.description)
        }
    }
}