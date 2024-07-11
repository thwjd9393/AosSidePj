package com.lullulalal.mymvvmtest

//데이터 클래스
//repository는 클린 API처럼 동작하는 디자인 패턴이며
//API는 애플리케이션의 데이터에 접근하기 위한 어플리케이션 프로그래밍 인터페이스라는 뜻
data class CounterModel(var count : Int)

class CounterRepository {
    private var _counter = CounterModel(0)

    fun getCounter() = _counter
    fun incrementCounter() {
        _counter.count++
    }

    fun decrementCounter() {
        _counter.count--
    }
}