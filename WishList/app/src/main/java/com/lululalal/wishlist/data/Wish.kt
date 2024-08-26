package com.lululalal.wishlist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wish-table")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id : Long =0L,
    @ColumnInfo(name = "wish-title")
    val title:String,
    @ColumnInfo("wish-desc")
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