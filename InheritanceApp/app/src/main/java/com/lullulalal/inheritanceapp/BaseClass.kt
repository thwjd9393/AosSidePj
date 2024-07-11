package com.lullulalal.inheritanceapp

open class BaseClass {
    //다른 class들이 상속 받을 클래스
    //open 키워드를 써서 class를 상속 할 수 있는 상태로 만든다
    fun coreValue() {
        println("베이스 클래스")
    }
    
    open fun role() { //오버라이딩 할 수 있도록 하려면 얘도 open으로 열어줘야함
        println("킹")
    }
}