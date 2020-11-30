package com.example.spoppinglist.data.product

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {
    @Query("SELECT * FROM product WHERE toBuy = 1")
    fun getList(): LiveData<List<Product>>

    @Query("SELECT * FROM product WHERE toBuy = 0")
    suspend fun getNotAddedProducts(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Query("DELETE FROM product")
    suspend fun deleteAllList()

    @Delete
    suspend fun delete(product: Product)

    @Update
    suspend fun update(product: Product)

}