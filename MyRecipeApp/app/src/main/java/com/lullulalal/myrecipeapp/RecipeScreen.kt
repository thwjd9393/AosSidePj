package com.lullulalal.myrecipeapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter

//사용자한테 보여질 UI 담당 파일

@Composable
fun RecipeScreen(modifier: Modifier = Modifier) {
    val recipeViewModel : MainViewModel = viewModel() //데이터 가져오는 역할
    val viewState by recipeViewModel.categoriesState
    //getValue import -> state의 값을 바로 가져올 수 있도록 해주는 애

    //ui 그리기용
    Box(modifier = Modifier.fillMaxSize()) {
        //로딩중 표시
        when {
            viewState.loading -> {
                CircularProgressIndicator(modifier.align(Alignment.Center))
            }
            viewState.error != null -> {
                Text(text = "Error")
            }
            else -> {
                //제대로 로딩됐을 때
                CategorScreen(categories = viewState.list)
            }
        }
    }
}

//리스트 항목을 생성하기 위한 view
@Composable
fun CategorScreen(categories : List<Category>) {
    //LazyVerticalGrid => 항목을 격자 무늬로 배치
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
        //아이템과 ui 연결
        items(categories) {
            category ->
            CategoyItem(category = category)
        }
    }
}

//각 아이템 생김새 ui
@Composable
fun CategoyItem(category: Category) {
    Column(modifier = Modifier
        .padding(8.dp)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(category.strCategoryThumb),
            contentDescription = category.strCategoryDescription,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)) // 1:1

        Text(
            text = category.strCategory,
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top=4.dp)
            )
    }
}