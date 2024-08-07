package com.lululalal.wishlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
class WishViewModel:ViewModel() {
    //데이터와 UI간의 소통 책임 - 데이터 저장, 로드, 수집, 수정 둥둥

    var wishTitleState by mutableStateOf("")
    // mutableStateOf사용 ->getValue와 setValue import
    var wishDescriptionState by mutableStateOf("")

    fun onWishTitleChanged(newString: String) { //외부에서 받아온 string
        wishTitleState = newString // mutableStateOf 타입인 변수에 덮어쓰기
    }
    fun onWishDescriptionChanged(newString: String) {
        wishDescriptionState = newString
    }

}