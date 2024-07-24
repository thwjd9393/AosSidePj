package com.lullulalal.myrecipeapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
fun RecipeScreen(modifier: Modifier = Modifier,
                 viewState:MainViewModel.RecipeState,
                 navigateTorecipeDetailScreen: (Category) -> Unit) {

    val recipeViewModel : MainViewModel = viewModel() //데이터 가져오는 역할
//    val viewState by recipeViewModel.categoriesState
//    -- 기존에 바로 viewState를 만들어서 사용한 대신 ReciprApp에서 전달해주기 위해 삭제

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
                CategorScreen(categories = viewState.list, navigateTorecipeDetailScreen)
            }
        }
    }
}

//리스트 항목을 생성하기 위한 view
@Composable
fun CategorScreen(categories : List<Category>,
                  navigateTorecipeDetailScreen: (Category) -> Unit) {
    //LazyVerticalGrid => 항목을 격자 무늬로 배치
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
        //아이템과 ui 연결
        items(categories) {
            category ->
            CategoyItem(category = category, navigateTorecipeDetailScreen)
        }
    }
}

//각 아이템 생김새 ui
@Composable
fun CategoyItem(category: Category,
                navigateTorecipeDetailScreen: (Category) -> Unit) {
    Column(modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .clickable { navigateTorecipeDetailScreen(category) }, //clickable은 modifier의 속성 중 하나
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