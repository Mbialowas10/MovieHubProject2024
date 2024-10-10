package com.mbialowas.moviehubproject2024.utility

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class Converters {
    private val moshi = Moshi.Builder().build()

    @TypeConverter
    fun fromString(value: String?): List<Int>?{
        if (value == null) return null

        val listType = Types.newParameterizedType(List::class.java, Integer::class.java)
        val adapter: JsonAdapter<List<Int>> = moshi.adapter(listType)

        return adapter.fromJson(value)
    }
    // converter a list of integers into a string
    @TypeConverter
    fun ListToString(list: List<Int>?): String?{
        if (list == null) return null

        val listType = Types.newParameterizedType(List::class.java, Integer::class.java)
        val adapter: JsonAdapter<List<Int>> = moshi.adapter(listType)

        return adapter.toJson(list)
    }
}




