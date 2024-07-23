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
//fun SecondScreen(name:String, navigateToThirdScreen:(nameToFirst)->Unit) {
fun SecondScreen(name:String, navigationToFirstScreen:()->Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Second", fontSize = 24.sp)
        Text(text = "welcome $name", fontSize = 24.sp)
        Button(onClick = {
//            navigateToThirdScreen()
            navigationToFirstScreen()
        }) {
            Text(text = "Go to Third Screen")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecondScreenPreview() {
    NavigationSampleTheme {
        SecondScreen("Rose",{})
    }
}
