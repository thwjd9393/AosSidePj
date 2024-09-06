package com.lululalal.wishlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lululalal.wishlist.data.Wish
import com.lululalal.wishlist.data.WishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WishViewModel(
    //pass로 전달하는 WishRepository를 따로 설정할 필요가 없도록 초기 상태를 설정
    private val wishRepository: WishRepository = Graph.wishRepository
):ViewModel() {
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

    lateinit var getAllWishes:Flow<List<Wish>> //Flow를 사용하면 비동기 상태이기 때문에 lateinit 같이 써야한다
    //getAllWishes를 사용하려면 사용과 동시에 데이터 load 해야함

    //lateinit var ?
    //var 변수에 대한 작업을 call로 호출하기 전에 변수가 초기화될 것이라고 Kotlin compiler(컴파일러)에 약속하는 것
    // 객체 생성 중에 초기화 할 수 없음
    // 초기화 후 non-null 상태여야 하는 경우 사용한다
    // 초기화가 확실하지 않거난 해당 속성에 접근할 때마다 null check를 하기 싫을 떄 사용
    // 주로 Dagger와 같은 dependency injection(종속 항목 삽입)플레임워크를 사용할 때나 메소드로 property에 assign으로 지정할 떄에 사용
    // 레이아웃에 inflate를 쓴 뒤 property에 값을 assign으로 지정할 때 사용
    // primitive type(기본타입)에는 사용 불가

    // getAllWishes 초기화
    init {
        viewModelScope.launch { // getAllWishes를 코루틴스코프의 뷰모델 스코프 내부에서 초기화 -> init 이후 getAllWishes를 empty 상태가 아니게 됨
            getAllWishes = wishRepository.getAllWishes()
        }
    }

    fun addWish(wish: Wish) {
        //코루틴 사용 => 최적화 시켜야함
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.addWish(wish)
        }
    }
    // Dispatchers?
    // 코루틴을 어떤 스레드에서 실행시킬 지 결정하는 객체
    //1. Dispatchers.IO
    // - io는 input과 output을 의미, 입출력 관련 작업 전문가
    // - 파일의 읽기 및 쓰기, 데이터베이스 작업, 네트워크 호출
    // - io작업을 한 때 그 스레드가 일을 마칠 때까지 기다려야함 Dispatchers.IO는 공유 스레드 풀을 유지 관리함
    // - 시스템 리소스가 효율적으로 사용되고 메인 스레드를 차단하지 않도록 보장함
    //2. Dispatchers.Default
    //3. Dispatchers.main
    //4. Dispatchers.Unconfined

    fun updateWish(wish: Wish) {
        //코루틴 사용 => 최적화 시켜야함
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.updateWish(wish)
        }
    }

    fun deleteWish(wish: Wish) {
        //코루틴 사용 => 최적화 시켜야함
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.deleteWish(wish)
        }
    }

    fun getByWishId(id:Long):Flow<Wish> = wishRepository.getByWishId(id)

}