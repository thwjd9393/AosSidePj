package com.lullulalal.navigationsample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lullulalal.navigationsample.ui.theme.NavigationSampleTheme

@Composable
fun ThirdScreen(navigationToFirstScreen:()->Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "This is the Third Screen. Go to First Screen", fontSize = 24.sp)
        Text(text = "나는 세번째 화면", fontSize = 24.sp)
        Button(onClick = {
            navigationToFirstScreen()
        }) {
            Text(text = "first로 이동")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ThirdScreenPreview() {
    NavigationSampleTheme {
        ThirdScreen({})
    }
}
