package com.example.spoppinglist.data.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    @PrimaryKey(autoGenerate = false) val name: String,
    @ColumnInfo(name = "toBuy") var toBuy: Boolean
)