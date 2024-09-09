package com.lululalal.wishlist

import androidx.collection.scatterSetOf
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lululalal.wishlist.data.Wish
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditDetailView(
    id : Long,
    wishViewModel: WishViewModel,
    navController: NavController) {

    // 스낵바 변수 선언
    val snackMessage = remember {
        mutableStateOf("")
    }

    // 데이터 베이스에 데이터 저장하는 작업 등 비동기로 실행하기 위한 스코프
    val scope = rememberCoroutineScope()

    // Scaffold State 변수 선언
    // -> Scaffold : UI의 외관에 대한 디테일이 포함되어 있는 부분
    //UI 상태를 유지하기 위한 변수
    val scaffoldState = rememberScaffoldState()

    //wishViewModel 셋팅하는 부분 추가
    if (id != 0L) {
        val wish = wishViewModel.getByWishId(id).collectAsState(initial = Wish(0L,"","")) //초기값 설정
        wishViewModel.wishTitleState = wish.value.title
        wishViewModel.wishDescriptionState = wish.value.description
    } else {
        wishViewModel.wishTitleState = ""
        wishViewModel.wishDescriptionState = ""
    }

    Scaffold( // AppBar의 onBackNavClicked()은 Scaffold에 작성
        scaffoldState = scaffoldState, //Scaffold에 상태 유지용 매개변수로 넣기
        topBar = { AppBarView(title =
                if (id != 0L) stringResource(id = R.string.update_wish)
                else stringResource(id = R.string.add_wish)
            )
            {
                navController.navigateUp()
                // navigateUp() => HomeView로 돌아감
                // 사용자를 이전에 있던 화면으로 돌아가게 하는 것을 의도
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            WishTextField("title",
                value = wishViewModel.wishTitleState,
                onValueChanged = { //onValueChanged -> 일어날 일 적기
                    wishViewModel.onWishTitleChanged(it)
                    //it = editText에 사용자가 쓴 값
                })

            Spacer(modifier = Modifier.height(10.dp))

            WishTextField("Description",
                value = wishViewModel.wishDescriptionState,
                onValueChanged = {
                    wishViewModel.onWishDescriptionChanged(it)
                    //it = editText에 사용자가 쓴 값
                })

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick =
                { /*데이터 데이스  Room에 저장하기*/
                    if (wishViewModel.wishTitleState.isNotEmpty()
                        && wishViewModel.wishDescriptionState.isNotEmpty()) {
                        if (id != 0L) {
                            //TODO 기존 데이터 업데이트
                            wishViewModel.updateWish(
                                Wish(
                                    id = id,
                                    title = wishViewModel.wishTitleState.trim(),
                                    description = wishViewModel.wishDescriptionState.trim()
                                )
                            )
                        } else {
                            //TODO 데이터 추가
                            wishViewModel.addWish(
                                Wish(
                                    title = wishViewModel.wishTitleState.trim(),
                                    description = wishViewModel.wishDescriptionState.trim()
                                )
                            )
                            snackMessage.value = "등록 완료"
                        }
                    } else {
                        //TODO 필드 작성 & wish 항목 생성
                        snackMessage.value = "항목 생성을 위해 필드를 작성하세요"
                    }

                    //생성 후 페이지 이동
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                        navController.navigateUp()
                    }
                },
                modifier = Modifier.fillMaxWidth()) {
                //버튼 텍스트를 표시하되 뭔가를 받아왔는지에 따라 달라질것
                Text(
                    text =
                    if (id != 0L) stringResource(id = R.string.update_wish)    
                        else stringResource(id = R.string.add_wish),
                    style = TextStyle(
                        fontSize = 18.sp
                    )
                )
            }
        }
    }
}

@Composable
fun WishTextField(
    label:String,
    value:String,
    onValueChanged:(String) -> Unit //텍스트에 보이는 것을 수정할 수 있도록해줌
) {
    OutlinedTextField(value = value,
        onValueChange = onValueChanged,
        label = {
            Text(text = label,
                color = Color.Black)
        },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), //키보드 타입 지정 (이메일, 텍스트, 넘버 등등)
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Blue,
            focusedBorderColor = Color.Green,
            unfocusedBorderColor = Color.Black,
            cursorColor = Color.Magenta,
            focusedLabelColor = Color.Green,
            unfocusedLabelColor = Color.Black
        ) //개별 색성 정의 가능 : 텍스트 색상, 테두리 강조 색상, 테두리 강조 취소 색상 등
    )
}

@Preview
@Composable
fun WishTextFieldPrev() {
    WishTextField("title","aaa", {})
}