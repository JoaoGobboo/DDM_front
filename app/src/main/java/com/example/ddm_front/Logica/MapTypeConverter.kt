package com.example.ddm_front.Logica

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MapTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromMap(map: MutableMap<String, Int>): String {
        return gson.toJson(map)
    }

    @TypeConverter
    fun toMap(value: String): MutableMap<String, Int> {
        val mapType = object : TypeToken<MutableMap<String, Int>>() {}.type
        return gson.fromJson(value, mapType)
    }

    // Conversor para Raca
    @TypeConverter
    fun fromRaca(raca: Raca?): String? {
        return raca?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toRaca(value: String?): Raca? {
        return value?.let {
            val type = object : TypeToken<Raca>() {}.type
            gson.fromJson(it, type)
        }
    }

    // Conversor para Classe
    @TypeConverter
    fun fromClasse(classe: Classe?): String? {
        return classe?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toClasse(value: String?): Classe? {
        return value?.let {
            val type = object : TypeToken<Classe>() {}.type
            gson.fromJson(it, type)
        }
    }
}
