package com.mbsysoft.myunitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mbsysoft.myunitconverter.ui.theme.MyUnitConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyUnitConverterTheme { //적용할 테마 불러오는 곳
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), //fillMaxSize => 화면 전체 채우기
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting("Android")
//                    Greeting("Android")
//                    함수를 두번 호출해도 Hello Android는 UI에 한번만 표시됨
//                    열을 알려주지않으면 내가 어디에 위치해야되는지 몰라서 자리를 찾지못한다.

                    UnitConverter() // @Compose의 Column과 row를 사용하여 데이터를 쌓는다
                }
            }
        }
    }
}

@Composable
fun UnitConverter() {
    Column {
        // ui 요소 쌓는 방법
        // 세로로 표시 (열)
        Greeting("aaa")
        Greeting("bbb")
        Row { // 가로로 표시 (행)
            Greeting("Android1")
            Greeting("Android2")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyUnitConverterTheme {
        Greeting("Android")
    }
}