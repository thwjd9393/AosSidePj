package com.lululalal.wishlist

import android.content.Context
import androidx.room.Room
import com.lululalal.wishlist.data.WishDatabase
import com.lululalal.wishlist.data.WishRepository

object Graph {
    //그래프에서 필요한거
    //1. lateinit 키워드 사용해서 데이터베이스 선언
    lateinit var database: WishDatabase //사용전에 초기화되도록 약속된 non-nullable한 데이터베이스 선언
    //lateinit 키워드 -> 디펜던시 인젝션을 통해 인스턴스를 초기화할 때 유용
    //WishDatabase는 RoomDatabase 클래스 유형으로 받음

    //2. wishRepository에 데이터 베이스 넘겨주기
    val wishRepository by lazy { //lazy 키워드를 통해 처음 사용할 때 초기화 되도록 선언 -> 리소스 아껴줌
        WishRepository(wishDao = database.wishDao())
    }

    //3. databaseBuilder만들기
    //데이터베이스의 인스턴스 생성 -> 인스턴스는 lateinit var database: WishDatabase에 할당됨
    //데이터베이스 초기화
    fun provide(context: Context) {
        database = Room.databaseBuilder(context,WishDatabase::class.java, "wishList.db").build()
    }

    //3. dependency제공하는 Service Locator

}