package com.example.pet_shop.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pet_shop.data.response.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Query("SELECT * FROM Product")
    fun getProducts(): Flow<List<Product>>

    @Query("SELECT * FROM Product WHERE id=:id")
    suspend fun getProduct(id: Int): Product?


}