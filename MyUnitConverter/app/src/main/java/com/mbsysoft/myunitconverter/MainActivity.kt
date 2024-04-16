package com.mbsysoft.myunitconverter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mbsysoft.myunitconverter.ui.theme.MyUnitConverterTheme
import kotlin.math.roundToInt

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitConverter() {

    var inputValue by remember {
        mutableStateOf("")
    }
    var outputValue by remember {
        mutableStateOf("")
    }
    var inputUnit by remember {
        mutableStateOf("m")
    }
    var outputUnit by remember {
        mutableStateOf("m")
    }
    var iExpanded by remember {
        mutableStateOf(false)
    }
    var oExpanded by remember {
        mutableStateOf(false)
    }
    val iConversionFactor = remember { //입력을 위한 전환 요소
        mutableStateOf(1.00)
    }
    val oConversionFactor = remember { //출력을 위한 전환 요소
        mutableStateOf(1.00)
    }

    val customTextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 16.sp,
        color = Color.Red
    )

    fun convertUnits() {
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        val result = (inputValueDouble * iConversionFactor.value * 100.0 / oConversionFactor.value).roundToInt() / 100.0
        outputValue = result.toString()
    }


//    Column {
//        // ui 요소 쌓는 방법
//        // 세로로 표시 (열)
//        Greeting("aaa")
//        Greeting("bbb")
//        Row { // 가로로 표시 (행)
//            Greeting("Android1")
//            Greeting("Android2")
//        }
//    }

    // textField 연습
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        // 세로로 표시 (열)
        Text(text = "Unit Converter",
            style = customTextStyle
        )

        Spacer(modifier = Modifier.height(16.dp)) //간격을 주고싶은 요소들 사이에 작성

        //1.
//        TextField(value = , onValueChange = )
        //2.
//        BasicTextField(value = ) {
//
//        }
        //3.
        //Enter Value
        OutlinedTextField(value = inputValue, onValueChange = {
            //onValueChange에는 value가 변할 때 해야할 것을 알려줘야함, 아무것도 하지말라고 지시해도 됨
            //onValueChange: (String) -> Unit
            inputValue = it //remember state를 사용하면 자동적으로 ui 에 반영됨
            convertUnits()
        }, label = { Text(text = "Enter Value") }) // label = placeholder
        
//        Row { // 가로로 표시 (행)
//
//            val context = LocalContext.current
//            //context : 어플리케이션 어트 위치에서 주어진 명령을 실행될 것인지에 대한 맥락
//
//            Button(onClick = { Toast.makeText(context, "클릭", Toast.LENGTH_SHORT).show() }) {
//                Text(text = "Click Me!")
//            }
//            Button(onClick = { /*TODO*/ }) {
//                Text(text = "clear!")
//            }
//        }

        Spacer(modifier = Modifier.height(16.dp)) //간격을 주고싶은 요소들 사이에 작성

        Row { // 가로로 표시 (행)

            // Input Box
            Box {
                //Box : 행이나 열과 같은 레이아웃 요소, 컴포저블을 정렬하고 세로로 나열하는 특징이 있다.
                // 보통 복잡한 레이아웃이나 커스텀 레이아웃을 만드는데 사용함

                // Input Button
                Button(onClick = {
                    iExpanded = true
                }, modifier = Modifier.padding(0.dp,0.dp,10.dp,0.dp)) {
                    Text(text = inputUnit)
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "드롭")
                    //contentDescription? 이 아이콘이 무엇을 상징하는지 설명하는 텍스트
                }
                // 드롭다운 메뉴는 버튼 아래에 생성
                //expanded? 초기에 드롭다운이 열려 있는지
                //onDismissRequest? 드롭다운이 닫히면 실행될 일 작성
                DropdownMenu(expanded = iExpanded, onDismissRequest = {
                    // 드롭박스 외의 화면의 다른곳을 터치시 해야할 일
                    iExpanded = false
                }) {
                    //여기에 드롭다운 메뉴 항목들 추가
                    DropdownMenuItem(
                        text = { Text(text = "cm") },
                        onClick = {
                            iExpanded = false
                            inputUnit = "cm"
                            iConversionFactor.value = 0.01
                            convertUnits()
                        })
                    DropdownMenuItem(
                        text = { Text(text = "m") },
                        onClick = { iExpanded = false
                            inputUnit = "m"
                            iConversionFactor.value = 1.0
                            convertUnits() })
                    DropdownMenuItem(
                        text = { Text(text = "feet") },
                        onClick = {
                            iExpanded = false
                            inputUnit = "feet"
                            iConversionFactor.value = 0.3048
                            convertUnits() })
                    DropdownMenuItem(
                        text = { Text(text = "mm") },
                        onClick = {
                            iExpanded = false
                            inputUnit = "mm"
                            iConversionFactor.value = 0.001
                            convertUnits()
                        })

                }

            }

            // output Box
            Box {

                //output Button
                Button(onClick = {
                    /* 아래 드롭박스와 연결 */
                    oExpanded = true
                }) {
                    Text(text = outputUnit)
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "드롭")
                    //contentDescription? 이 아이콘이 무엇을 상징하는지 설명하는 텍스트
                }
                DropdownMenu(expanded = oExpanded, onDismissRequest = {
                    oExpanded = false
                }) {
                    //여기에 드롭다운 메뉴 항목들 추가
                    DropdownMenuItem(
                        text = { Text(text = "cm") },
                        onClick = {
                            oExpanded = false
                            outputUnit = "cm"
                            oConversionFactor.value = 0.01
                            convertUnits()
                        })
                    DropdownMenuItem(
                        text = { Text(text = "m") },
                        onClick = {
                            oExpanded = false
                            outputUnit = "m"
                            oConversionFactor.value = 1.0
                            convertUnits()
                        })
                    DropdownMenuItem(
                        text = { Text(text = "feet") },
                        onClick = {
                            oExpanded = false
                            outputUnit = "feet"
                            oConversionFactor.value = 0.3048
                            convertUnits()
                        })
                    DropdownMenuItem(
                        text = { Text(text = "mm") },
                        onClick = {
                            oExpanded = false
                            outputUnit = "mm"
                            oConversionFactor.value = 0.001
                            convertUnits()
                        })

                }
            }
        }

        Text(text = "Result : $outputValue $outputUnit",
            style = MaterialTheme.typography.headlineMedium
            )
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier,
//    )
//}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    MyUnitConverterTheme {
////        Greeting("Android")
//    }
//}

@Preview(showBackground = true)
@Composable
fun unitConverterPreview() { // 프리뷰 함수이름은 무조건 Preview를 붙여야 인식한다!!
    UnitConverter()
}