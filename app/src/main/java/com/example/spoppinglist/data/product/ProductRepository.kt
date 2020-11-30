package com.example.spoppinglist.data.product

import androidx.lifecycle.LiveData

class ProductRepository(private val productDao: ProductDao){

    var productList: LiveData<List<Product>>? = null
    lateinit var notAddedProducts: List<Product>

    fun getAllSavings(){
        productList = productDao.getList()
    }

    suspend fun getNotAddedProducts1(): List<Product>{
        notAddedProducts = productDao.getNotAddedProducts()
        return notAddedProducts
    }

    suspend fun insertSaving(product: Product){
        productDao.insertProduct(product)
    }

    suspend fun deleteAll(){
        productDao.deleteAllList()
    }

    suspend fun delete(product: Product){
        productDao.delete(product)
    }

    suspend fun update(product: Product){
        productDao.update(product)
    }

}