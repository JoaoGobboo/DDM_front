package com.example.ddm_front.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ddm_front.Logica.Personagem

@Dao
interface PersonagemDAO {
    @Insert
    suspend fun insert(personagem: Personagem): Long  // Insere um personagem e retorna seu ID

    @Query("SELECT * FROM Personagem")  // Certifique-se de que o nome da tabela está correto
    suspend fun getAll(): List<Personagem>  // Retorna todos os personagens

    @Query("SELECT * FROM Personagem WHERE id = :id")
    suspend fun getById(id: Long): Personagem?  // Retorna um personagem pelo ID ou null se não encontrado

    @Update
    suspend fun update(personagem: Personagem)  // Atualiza as informações do personagem

    @Delete
    suspend fun delete(personagem: Personagem)  // Deleta um personagem da tabela
}
