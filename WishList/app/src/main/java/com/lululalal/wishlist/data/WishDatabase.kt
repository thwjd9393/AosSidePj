package com.lululalal.wishlist.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Wish::class],
    version = 1,
    exportSchema = false
)
abstract class WishDatabase : RoomDatabase() {
    abstract fun WishDao() : WishDao //WishDao에 작성한 것에 접근 가능하도록 함
}