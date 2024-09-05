package com.lululalal.wishlist.data

import androidx.room.Database
import androidx.room.RoomDatabase

//데이터베이스 설정
@Database(
    entities = [Wish::class], //데이터 베이스 내부에 필요한 entity - 지금은 하나지만 여러개가 될 수 있음
    version = 1, //
    exportSchema = false //
)
abstract class WishDatabase : RoomDatabase() { //사용하고 있는 데이터베이스
    abstract fun wishDao() : WishDao //WishDao에 작성한 것에 접근 가능하도록 함
}