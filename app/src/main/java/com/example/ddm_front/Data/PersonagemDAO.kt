package com.example.ddm_front.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ddm_front.Logica.Personagem

@Dao
interface PersonagemDAO {  // Renomeado para PersonagemDao
    @Insert
    suspend fun insert(personagem: Personagem)

    @Query("SELECT * FROM personagem")
    suspend fun getAllPersonagens(): List<Personagem>

    @Query("SELECT * FROM personagem")
    suspend fun getAll(): List<Personagem>

    @Query("SELECT * FROM personagem WHERE id = :id")
    suspend fun getById(id: Long): Personagem

    @Update
    suspend fun update(personagem: Personagem)

    @Delete
    suspend fun delete(personagem: Personagem)
}