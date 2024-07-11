package com.lullulalal.mymvvmtest

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

//class CounterViewModel():ViewModel() {
//
//    //remember - mutableStateOf 키워드 없이 mutableStateOf한 변수 만들기
//    private val _count = mutableStateOf(0) //private 변수 앞엔 _붙이는게 관습이다
//
//    //외부에서 _count에 접근하기 위해선
//    //count 변수를 immutable한 상태로 노출시켜야함
//    val count : MutableState<Int> = _count
//
//    //_count를 변경하도록 해야함 변경 값은 count에 저장됨
//    //_count변수 자체를 노출x 그 값만 노출될 수 있도록함
//    fun increment() {
//        _count.value++
//    }
//
//    fun decrement() {
//        _count.value--
//    }
//
//}

class CounterViewModel():ViewModel() {

    private val _repository: CounterRepository = CounterRepository()

    //remember - mutableStateOf 키워드 없이 mutableStateOf한 변수 만들기
    private val _count = mutableStateOf(_repository.getCounter().count) //모델 변수에 접근

    //외부에서 _count에 접근하기 위해선
    //count 변수를 immutable한 상태로 노출시켜야함
    val count : MutableState<Int> = _count

    //_count를 변경하도록 해야함 변경 값은 count에 저장됨
    //_count변수 자체를 노출x 그 값만 노출될 수 있도록함
    fun increment() {
        _repository.incrementCounter()
        _count.value = _repository.getCounter().count
    }

    fun decrement() {
        _repository.decrementCounter()
        _count.value = _repository.getCounter().count
    }

}