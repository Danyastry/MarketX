package com.example.pet_shop.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.pet_shop.data.response.Dimensions
import com.example.pet_shop.data.response.Meta
import com.example.pet_shop.data.response.Review
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class TypeConverter {

    @TypeConverter
    fun fromDimensions(dimensions: Dimensions): String = Gson().toJson(dimensions)

    @TypeConverter
    fun toDimensions(value: String): Dimensions =
        Gson().fromJson(value, Dimensions::class.java)

    @TypeConverter
    fun fromMeta(meta: Meta): String = Gson().toJson(meta)

    @TypeConverter
    fun toMeta(value: String): Meta = Gson().fromJson(value, Meta::class.java)

    @TypeConverter
    fun fromStringList(value: List<String>): String = Gson().toJson(value)

    @TypeConverter
    fun toStringList(value: String): List<String> =
        Gson().fromJson(value, object : TypeToken<List<String>>() {}.type)

    @TypeConverter
    fun fromReviewList(value: List<Review>): String = Gson().toJson(value)

    @TypeConverter
    fun toReviewList(value: String): List<Review> =
        Gson().fromJson(value, object : TypeToken<List<Review>>() {}.type)
}
