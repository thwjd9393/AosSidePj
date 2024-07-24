package com.lullulalal.navigationsample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lullulalal.navigationsample.ui.theme.NavigationSampleTheme

// 버튼을 이용해 화면을 이동하기 위해선 두가지 준비가 필요하다
//1.실행할 코드나 함수를 전달하는 것, 익명 함수 또는 람다 사용해 간결하게 할 수 있음
//fun FirstScreen(navigationToSecondScreen:()->Unit)
//2.버튼 안에서 매개변수로 받은 함수 호출

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstScreen(navigationToSecondScreen:(String,String)->Unit) {
    val name = remember {
        mutableStateOf("")
    }
    val age = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "First", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = name.value, onValueChange = {
            name.value = it
        })
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = age.value, onValueChange = {
            age.value = it
        })
        Button(onClick = {
            navigationToSecondScreen(name.value, age.value)
        }) {
            Text(text = "이동")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FirstScreenPreview() {
    NavigationSampleTheme {
//        FirstScreen("","",{})
    }
}