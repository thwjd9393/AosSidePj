package com.mbsysoft.mycoroutinetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        //안드로이드 UI는 5초까지만 딜레이 간으 이후는 ANR 오류 남

        /**
         * 코루틴이란?
         * 1. Light Weight
         * -> 서스펜션의 지원으로 단일 스레드에서 많은 코루틴 실행 가능
         * 2. fewer memory leaks
         * -> 코루틴과 특정 스코프를 실행하면 스코프 끝에서 종료될 때 누출이 발생하지 않음
         * 3. built in cancellation support
         * -> lauch 옵션은 실행중인 코루틴을 취소하는 데 사용할 수 있는 작업을 반환하고 제트 팩에 통합한다
         * -> 그래서 많은 제트팩 라이브러리들은 코루틴에 대한 자우너을 포함하고 있다 ex) room
         **/

        /**
         * 코루틴 스코프란?
         * 코루틴을 실행하여 최종적으로 이 기능을 실행할 수 있는 스코프가 내장되어 있다
         * 하지만 코루틴 범위가 필요하고 스텐트 구조화된 동시성의 원칙에 따라 최신 코루틴은
         * 특정 코루틴 범위에서만 출시 된다
         *
         * 스코프를 생성한 후 마지막에 삭제하지 않으면 메모리 누수가 발생할 수 있다
         **/

        // 1. viewModelScope
        // 뷰모델 클래스에서 코루틴을 실행하는 데 사용할 수 있으며 이 모델이 지워지면 자동 취소됨
        /**
        viewModelScope.lauch(Dispatchers.IO) {
            try {
                val client = repository.getCharacter("1")
                charactersLiveData.postValue(ScreenStatus.Error(e.message!!, null))
            }catch (e:Exception) {
                charactersLiveData.postValue(ScreenStatus.Error(e.message!!, null))
            }
        }
        **/

        // 2.lifecycleScope
        // 액티비티나 프래그먼트에서 코루틴을 실행하는 데 사용하는 라이프사리클스코프는 망가지면 자동 취소됨
        // 라이프사이클이 중단되는 경우 메모리 누수가 발생하지 않아 좋다
        /**
        lifecycleScope.lauch {
            try {
                val client = ApiClient.apiservice.fetchCharacters("1")
            } catch (e:Exception) {}
        }
        **/

        // 3. Custom scope
        // 사용자 지정 범위를 만들 수도 있지만 실행이 완료되면 작업에 연결하고 지우는 것이 중요하다
        // 액티비티에서 작업변수를 만들려면 클로벌 작업 변수를 만들어서 코루틴 범위를 생성하고 작업을 연결한 다음 실행함
        // 그리고 마지막으로 액티비티가 중단되면 작업을 취소함
        /**
        private val job = Job()

        CoroutineScope(Dispatchers.IO + job).launch {
            try {
                val client = ApiClient.apiservice.fetchCharacters("1")
            } catch (e:Exception) {

            }
        }

        override fun onDestroy() {
            super.onDestroy()
            job.cancle
        }
        **/
        
    }
}