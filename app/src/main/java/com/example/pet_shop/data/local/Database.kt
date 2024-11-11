package com.example.pet_shop.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pet_shop.data.response.Product

@Database(entities = [Product::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class Database : RoomDatabase() {

    abstract fun dao(): Dao

}