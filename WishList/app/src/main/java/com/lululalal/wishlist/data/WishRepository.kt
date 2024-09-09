package com.lululalal.wishlist.data

import kotlinx.coroutines.flow.Flow

class WishRepository(private val wishDao: WishDao) {

    suspend fun addWish(wish:Wish) {
        wishDao.addWish(wish)
    }

    fun getAllWishes():Flow<List<Wish>> = wishDao.getAllWishes()

    fun getByWishId(id:Long): Flow<Wish> = wishDao.getByWishId(id)

    suspend fun updateWish(wish:Wish) = wishDao.updateWish(wish)

    suspend fun deleteWish(wish:Wish) = wishDao.deleteWish(wish)

}