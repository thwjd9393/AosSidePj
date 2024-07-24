package com.lullulalal.myrecipeapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

//디테일 뷰
@Composable
fun RecipeDetailScreen(category:Category) {
    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = category.strCategory,
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom =4.dp)
        )
        Image(
            painter = rememberAsyncImagePainter(category.strCategoryThumb),
            contentDescription = "${category.strCategory} Thumbnail",
            modifier = Modifier
                .aspectRatio(1f)) // 1:1

        //스크롤 텍스트 만들기
        Text(
            text = category.strCategoryDescription,
            textAlign = TextAlign.Justify, //부드러운 줄바꿈 해줌
            color = Color.Black,
            modifier = Modifier
                .verticalScroll(rememberScrollState()) //텍스트 스크롤 rememberScrollState이 스크롤이 어떤 상태 인지를 알려준다
                .padding(top = 4.dp)
        )
    }
}