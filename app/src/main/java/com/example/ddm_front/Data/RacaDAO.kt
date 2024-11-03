package com.example.ddm_front.Data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.ddm_front.Logica.Raca

@Dao
interface RacaDAO {

    // Método para inserir uma nova raça
    @Insert
    suspend fun inserirRaca(raca: Raca): Long

    // Método para obter uma raça pelo seu ID
    @Query("SELECT * FROM raca WHERE id = :id")
    suspend fun obterRaca(id: Int): Raca?

    // Método para obter todas as raças
    @Query("SELECT * FROM raca")
    suspend fun obterTodasRacas(): List<Raca>

    // Método para atualizar uma raça existente
    @Update
    suspend fun atualizarRaca(raca: Raca)

    // Método para deletar uma raça
    @Delete
    suspend fun deletarRaca(raca: Raca)
}
