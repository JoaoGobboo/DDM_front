package com.example.ddm_front.Data

import androidx.room.Dao
import androidx.room.Query
import com.example.ddm_front.Logica.Classe

@Dao
interface ClasseDao {
    @Query("SELECT * FROM personagem WHERE id = :classeId")
    fun getClasseById(classeId: Int): Classe?
}