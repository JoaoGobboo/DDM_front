package com.example.ddm_front.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.ddm_front.Logica.Personagem


@Dao
interface PersonagemDAO {

    @Insert
    suspend fun insert(personagem: Personagem)

    @Query("SELECT * FROM personagem")
    suspend fun getAllPersonagens(): List<Personagem>

    @Delete
    suspend fun delete(personagem: Personagem)
}