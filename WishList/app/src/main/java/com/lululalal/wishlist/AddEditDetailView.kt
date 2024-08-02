package com.lululalal.wishlist

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
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditDetailView(
    id : Long,
    wishViewModel: WishViewModel,
    navController: NavController) {
    Scaffold(
        topBar = { AppBarView(title =
                if (id != 0L) stringResource(id = R.string.update_wish)
                else stringResource(id = R.string.add_wish)
            )
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
                onValueChanged = {
                    wishViewModel.onWishTitleChanged(it)
                    //it = editText에 사용자가 쓴 값
                })

            Spacer(modifier = Modifier.height(10.dp))

            WishTextField("description",
                value = wishViewModel.wishDescriptionState,
                onValueChanged = {
                    wishViewModel.onWishDescriptionChanged(it)
                    //it = editText에 사용자가 쓴 값
                })

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { /*TODO*/ }) {
                Text(text = "Update Wish")
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