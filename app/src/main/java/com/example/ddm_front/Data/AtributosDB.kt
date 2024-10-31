package com.example.ddm_front.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ddm_front.Logica.Atributos
import com.example.ddm_front.Logica.Personagem
import com.example.ddm_front.Logica.Raca

@Database(entities = [Atributos::class, Personagem::class, Raca::class], version = 2, exportSchema = false)
abstract class AtributosDB : RoomDatabase() {

    abstract fun atributosDAO(): AtributosDAO
    abstract fun personagemDAO(): PersonagemDAO
    abstract fun racaDAO(): RacaDAO

    companion object {
        @Volatile
        private var INSTANCE: AtributosDB? = null

        fun getDatabase(context: Context): AtributosDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AtributosDB::class.java,
                    "atributosDB"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
