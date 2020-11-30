package com.example.spoppinglist.data.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProductViewModel(application: Application): AndroidViewModel(application){

    private val repository: ProductRepository
    var savingList: LiveData<List<Product>>
    var notAddedProduct: List<Product>? = null

    init {
        val productDao = SavingsRoomDatabase.getDatabase(application).savingDao()
        repository = ProductRepository(productDao)
        repository.getAllSavings()
        savingList = repository.productList!!
    }

    fun getNotAddedProducts() = viewModelScope.launch (Dispatchers.IO){
        notAddedProduct = repository.getNotAddedProducts1()
    }

    fun insert(saving: Product) = viewModelScope.launch(Dispatchers.IO){
        repository.insertSaving(saving)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO){
        repository.deleteAll()
    }

    fun delete(product: Product) = viewModelScope.launch (Dispatchers.IO){
        repository.delete(product)
    }

    fun update(product: Product) = viewModelScope.launch(Dispatchers.IO){
        repository.update(product)
    }

}