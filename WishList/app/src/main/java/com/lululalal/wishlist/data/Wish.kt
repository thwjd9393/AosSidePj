package com.lululalal.wishlist.data

data class Wish(
    val id : Long =0L,
    val title:String,
    val description:String
)

object DummyWish {
    val wishList = listOf<Wish>(
        Wish(1,"aaaa","aaaaaaaaaaaaaaaaa"),
        Wish(2,"bbbbb","test"),
        Wish(3,"ccccc","test test test"),
        Wish(4,"dddd","testtesttesttesttest"),
    )
}