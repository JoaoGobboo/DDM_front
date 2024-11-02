package com.example.ddm_front.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.ddm_front.Logica.Atributos

@Dao
interface AtributosDAO {
    @Insert
    suspend fun insert(atributos: Atributos): Long  // Retorna o ID gerado da nova entrada

    @Query("SELECT * FROM atributos")
    suspend fun getAllAtributos(): List<Atributos>

    @Query("SELECT * FROM atributos WHERE id = :atributosId")
    suspend fun getById(atributosId: kotlin.Long): Atributos

    @Delete
    suspend fun delete(atributos: Atributos)

}