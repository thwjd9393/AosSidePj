package com.lululalal.wishlist

import com.lululalal.wishlist.data.WishDatabase
import com.lululalal.wishlist.data.WishRepository

object Graph {
    lateinit var database: WishDatabase

    //wishEwpository에 데이터 베이스 넘겨주기
    val wishRepository by lazy {
        WishRepository(wishDao = database.wishDao())
    }
}