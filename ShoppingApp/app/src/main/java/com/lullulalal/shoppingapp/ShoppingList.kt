package com.mbsysoft.myshoppinglistapp

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.lullulalal.shoppingapp.LocationUtils
import com.lullulalal.shoppingapp.LocationViewModel
import com.lullulalal.shoppingapp.MainActivity

data class ShoppingItem(val id:Int,
                        var name:String,
                        var quantity: Int,
                        var isEditing:Boolean = false,
                        var address: String="")


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListApp(
    locationUtils: LocationUtils,
    viewModel: LocationViewModel,
    navController: NavController,
    context: Context,
    address: String,
) {
    // 목록 상태를 지켜보는 변수
    //Compose에서 어떠한 상태 값이 바뀌게 되면 재구성(Recomposition)이 일어나게 된다
    //MutableState 클래스는 Compose에서 읽기와 쓰기를 관찰하기 위해 만들어진 클래스
    var sItems by remember {//by 키워드를 사용하게 되면 get/set에 대한 위임이 이루어지
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

    //위치 권한 런처
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(), //contract는 permissions를 리턴함
        onResult = {permissions ->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION]==true
                && permissions[Manifest.permission.ACCESS_FINE_LOCATION]==true ) {
                //위치에 권한 있다
                //위도 -경도 셋팅하는 함수 호출!!
                locationUtils.requestLocationUpdate(viewModel)

            } else {
                //권한 요청 필요
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity, //메인 액티비치 말곤 다른창에선 이 팝업 열지말라는 등록
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )

                //이 퍼미션이 필요한 이유를 설명해주기
                if (rationaleRequired) {
                    Toast.makeText(context, "이 기능을 사용하려면 위치 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
                } else {
                    //rationaleRequired이 거부 상황일경우 사용자의 설정이나 안드로이드 폰 설정으로 이동
                    Toast.makeText(context, "설정에 들어가 위치권한을 활성화 해주세요.", Toast.LENGTH_SHORT).show()
                }

            }
        }) //contract에서 리턴받은 permissions가 모두 허용됐는지 체크하는 부분


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

        LazyColumn( //리사이클러뷰 같은 거 - Lazy column 은 잠재적으로 무한하거나 끝이 없는 항목을 처리하도록 설계
            // 그리드 레이아웃을 만들기 위한 것이 아니라 수직 시퀀스에 잠재적으로 무한한 항목 목록을 표시하는 데 사용
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            content = {
                items(sItems) {
//                    ShoppingListItem(it, {}, {})
                    item ->
                    if (item.isEditing){ //수정
                        ShoppingItemEditor(item = item, onEditComplete = {
                            editedName, editedQuanitity ->
                            sItems = sItems.map { it.copy(isEditing = false) }
                            val editedItem = sItems.find { it.id == item.id } //특정 항목 찾아줌
                            editedItem?.let {
                                it.name = editedName //편집 항목의 이름이 지금 텍스트 입력창에 입력한 이름이로 변경
                                it.quantity = editedQuanitity
                                it.address = address
                            }
                        })
                    } else {
                        ShoppingListItem(item = item, onEditClick = {
                            //어떤 항목을 편집하는지 알아내는 코드
                            //편집하는 것은 isEditing이 true인것을 의미한다
                            sItems = sItems.map { it.copy(isEditing = it.id == item.id) }
                        }, onDeleteClick = {
                            sItems = sItems - item
                        })
                    }
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
                    onValueChange = {
                        if (it.all { char -> char.isDigit() }) {
                            itemQuntity = it
                        }
                    },//값을 입력할 떄 마다 보여줌
                    singleLine = true, //다중줄 막음
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )

                //권한 요청 버튼
                Button(onClick = {
                    if (locationUtils.hasLocationPermission(context)) {
                        locationUtils.requestLocationUpdate(viewModel)
                        navController.navigate("locationscreen"){
                            this.launchSingleTop //스택 내부에는 한가지 화면만 있어야한다하는 뜻
                        }
                    } else {
                        requestPermissionLauncher.launch(arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ))
                    }
                }) {
                    Text(text = "address here")
                }
            }},
            confirmButton = {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween //양 끝에 위치하기
                ) {
                    Button(onClick = {
                        if (itemName.isNotBlank() && itemQuntity.isNotBlank()) {
                            val newItem = ShoppingItem(
                                id = sItems.size+1,
                                name = itemName,
                                quantity = itemQuntity.toInt(),
                                address = address
                            )
                            sItems = sItems + newItem
                            showDialog = false
                            itemName = ""
                        }

                    }) {
                        Text(text = "Add")
                    }
                    
                    Button(onClick = {showDialog = false}) {
                        Text(text = "Close")
                    }
                }
            })
    }
}

@Composable
fun ShoppingItemEditor(item: ShoppingItem, onEditComplete:(String, Int) -> Unit){
    var editeName by remember { mutableStateOf(item.name) }
    var editeQuality by remember { mutableStateOf(item.quantity.toString()) }
    var isEditing by remember { mutableStateOf(item.isEditing) }

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly) {
        Column() {
            BasicTextField(
                value = editeName,
                onValueChange = { editeName = it },
                singleLine = true,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(8.dp))

            BasicTextField(
                value = editeQuality,
                onValueChange = { editeQuality = it },
                singleLine = true,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(8.dp))
            
            Button(onClick = {
                isEditing = false
                onEditComplete(editeName, editeQuality.toIntOrNull()?:1)
            }) {
                Text(text = "Save")
            }
        }
    }

}

@Composable
fun ShoppingListItem(
    item: ShoppingItem,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Row(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .border(
            border = BorderStroke(2.dp, Color.Blue),
            shape = RoundedCornerShape(20)
        ),
        horizontalArrangement = Arrangement.SpaceBetween
        ) {
        Column(
            Modifier
                .weight(1f)
                .padding(8.dp)) {
            Row {
                Text(text = item.name, modifier = Modifier.padding(8.dp))
                Text(text = "Qty: ${item.quantity}", modifier = Modifier.padding(8.dp))
            }

            Row(Modifier.fillMaxWidth()) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = "map")
                Text(text = item.address) //주소 표시
            }
        }

        Row(modifier = Modifier.padding(8.dp)) {
            IconButton(onClick = onEditClick) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "수정")
                //Icons.Default.Edit : 안드로이드에서 기본 제공 icon
            }
            IconButton(onClick = onDeleteClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "삭제")
            }
        }
    }
}


