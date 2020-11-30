package com.example.spoppinglist.data.product

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Product::class),version = 1, exportSchema = false)
abstract class SavingsRoomDatabase: RoomDatabase(){

    abstract fun savingDao(): ProductDao

    companion object{
        @Volatile
        private var INSTANCE: SavingsRoomDatabase? = null

        fun getDatabase(context: Context): SavingsRoomDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SavingsRoomDatabase::class.java,
                    "product_database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}