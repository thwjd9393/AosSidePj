package com.lullulalal.inheritanceapp

open class Secondary : BaseClass() {

    override fun role() {
        //super.role() //super : 기본적으로 BaseClass에서 정의한 내용을 실행하란뜻

        println("세자 입니다")
    }

}