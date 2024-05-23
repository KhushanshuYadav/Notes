package com.khushanshu.wishes.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


//creating a abstract class for dao and passing a wish which we need to store convention to add entity
@Dao
abstract class WishDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE) //ignore iff same wish insertion multiple time
    abstract suspend fun addAWish(wishEntity: Wish)


    @Query( "select * FROM `wish-table`")         //defining type and query it will run so below fun will run this query
    abstract  fun getAllWishes(): Flow<List<Wish>> //no need of suspend as we are returning flow which uses it implicitly

    @Update
    abstract suspend fun updateAWish(wishEntity: Wish)

    @Delete
    abstract suspend fun deleteAWish(wishEntity: Wish)

    @Query( "SELECT * FROM `wish-table` WHERE id=:id") //passing fun id to query
    abstract  fun getAByIdWish(id:Long): Flow<Wish>






}