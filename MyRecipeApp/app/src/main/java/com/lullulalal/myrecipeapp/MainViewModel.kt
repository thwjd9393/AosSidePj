package com.lullulalal.myrecipeapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel :ViewModel() { //ViewModel : 데이터와 UI 간의 통신 처리

    private val _categoryState = mutableStateOf(RecipeState())
    val categoriesState:State<RecipeState> = _categoryState //다른 클래스에 노출할 변수
    //State => 구성 가능한 함수를 실행하는 동안 값 속성을 참조하는 value holder는 현재의 재구성 범위가 해당
    //값의 변화에 따르게 되고 이는 기본적으로 실제로 우리의 RecipeState 객체가 변경될 떄마다 사용자 인터페이스가
    //업데이트 되기를 원한다는 것
    //RecipeState의 값이 변화된것을 알려줌

    //받은 데이터를 표시하기 - MainViewModel 호출하면 실행되는 부분
    init {
        fetchCategories()
    }

    private fun fetchCategories() { //화면에 표시하기 위해 불러야 하는 함수
        //viewModelScope => suspend함수가 처리되도록 launch를 제공함
        //suspend => 백그라운드에서 실행
        //suspend 실행하고 싶으면 corutine 내에서 시작해야함

        viewModelScope.launch {
            try {
                val response = recepeService.getCategories()
                _categoryState.value = _categoryState.value.copy(
                    list = response.categories,
                    loading = false,
                    error = null
                )
            } catch (e:Exception) {
                _categoryState.value = _categoryState.value.copy(
                    loading = false,
                    error = "Error ${e.message}"
                )
            }
        }

    }
     data class RecipeState(
         val loading : Boolean = true,
         val list : List<Category> = emptyList(),
         val error : String? = null
     )

}