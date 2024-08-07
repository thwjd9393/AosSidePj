package com.mbsysoft.myshoppinglistapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class ShoppingItem(val id:Int,
                        var name:String,
                        var quantity: Int,
                        var isEditing:Boolean = false)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListApp() {
    // 목록 상태를 지켜보는 변수
    val sItems by remember {
        mutableStateOf(listOf<ShoppingItem>())
    }
    var showDialog by remember {
        mutableStateOf(false) //표시 상태 지켜보는 애 true가 되면 보임
    }
    var itemName by remember {
        mutableStateOf("")
    }
    var itemQuntity by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { showDialog = true },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Add Item")
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            content = {
                items(sItems) {

                }
            })
    }

    //다이아 로그
    if (showDialog) {
        AlertDialog(onDismissRequest = {showDialog = false},
            title = { Text(text = "추가") },
            text = { Column {
                OutlinedTextField(
                    value = itemName,
                    onValueChange = { itemName = it},//값을 입력할 떄 마다 보여줌
                    singleLine = true, //다중줄 막음
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                )
                OutlinedTextField(
                    value = itemQuntity,
                    onValueChange = { itemQuntity = it},//값을 입력할 떄 마다 보여줌
                    singleLine = true, //다중줄 막음
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                )
            }},
            confirmButton = { Text(text = "확인") })
    }
}


