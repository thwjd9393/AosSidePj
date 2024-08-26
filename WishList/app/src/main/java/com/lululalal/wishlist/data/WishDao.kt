package com.lululalal.wishlist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

//추상 클래스로 작성
//추상 클래스=>구현부가 없는 함수
@Dao
abstract class WishDao {

    //suspend 키워드를 사용해서 백그라운드에서 실행되도록 함
    //getAllWishes와 getByWishId엔 안붙여줘도 되는데 이미 코루틴 방식과 비슷한 Flow를 사용하기 떄문

    // onConflict = OnConflictStrategy.IGNORE = 존재하는 항목이 있으면 무시해라
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addWish(wishEntity : Wish)

    @Query("SELECT * FROM `wish-table`")
    abstract fun getAllWishes(): Flow<List<Wish>> //kotlinx.coroutines.flow.Flow 임포트
    //Flow => 코틀린 코루틴 라이브러리에 포함되어 있으며, 비동기 데이터 스트림을 반응형 방식으로 제어함

    @Update
    abstract suspend fun updateWish(wishEntity : Wish)

    @Delete
    abstract suspend fun deleteWish(wishEntity : Wish)

    @Query("SELECT * FROM `wish-table` WHERE id=:id")
    abstract fun getByWishId(id:Long): Flow<Wish>

}