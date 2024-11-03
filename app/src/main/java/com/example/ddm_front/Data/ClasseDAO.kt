package com.example.ddm_front.Data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.ddm_front.Logica.Classe

@Dao
interface ClasseDAO {

    // Método para inserir uma nova classe
    @Insert
    suspend fun inserirClasse(classe: Classe): Long

    // Método para obter uma classe pelo seu ID
    @Query("SELECT * FROM classe WHERE id = :id")
    suspend fun obterClasse(id: Int): Classe?

    // Método para obter todas as classes
    @Query("SELECT * FROM classe")
    suspend fun obterTodasClasses(): List<Classe>

    // Método para atualizar uma classe existente
    @Update
    suspend fun atualizarClasse(classe: Classe)

    // Método para deletar uma classe
    @Delete
    suspend fun deletarClasse(classe: Classe)
}
