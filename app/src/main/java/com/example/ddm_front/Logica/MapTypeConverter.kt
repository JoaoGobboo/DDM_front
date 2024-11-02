package com.example.ddm_front.Logica

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MapTypeConverter {
    @TypeConverter
    fun fromMap(map: MutableMap<String, Int>): String {
        return Gson().toJson(map)
    }

    @TypeConverter
    fun toMap(value: String): MutableMap<String, Int> {
        val mapType = object : TypeToken<MutableMap<String, Int>>() {}.type
        return Gson().fromJson(value, mapType)
    }
}