package com.lullulalal.mymvvmtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lullulalal.mymvvmtest.ui.theme.MyMVVMTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            //viewModel() 사용하기 위해선
            //implementation 'androidx.lifecycle:lifecycle-viewmodel-compose:2.8.3' 되어 있어야함
            val viewModel : CounterViewModel = viewModel()

            MyMVVMTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TheCounterApp(viewModel)
//                    TheCounterApp()
                }
            }
        }
    }

    @Composable
    fun TheCounterApp(viewModel: CounterViewModel) {//viewModel전달
//        val count = remember {
//            mutableStateOf(0)
//        }

//        fun increment() {
//            count.value++
//        }
//
//        fun decrement() {
//            count.value--
//        }

        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = "Count : ${viewModel.count.value}", //viewModel에 작성함 count변수에 접근
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
                )
            Spacer(modifier = Modifier.height(16.dp))
            Row() {
                Button(onClick = { viewModel.increment() }) {
                    Text(text = "increment")
                }
                Button(onClick = { viewModel.decrement() }) {
                    Text(text = "decrement")
                }
            }
        }
    }
}

