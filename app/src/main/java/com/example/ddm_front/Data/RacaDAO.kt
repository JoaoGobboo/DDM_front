package com.example.ddm_front.Data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ddm_front.Logica.Raca

@Dao
interface RacaDAO {
    @Insert
    suspend fun insert(raca: Raca)

    @Query("SELECT * FROM raca WHERE id = :id")
    suspend fun getRacaById(id: Int): Raca?

    @Query("SELECT * FROM raca")
    suspend fun getAllRacas(): List<Raca>
}
