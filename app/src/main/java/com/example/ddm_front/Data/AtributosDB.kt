package up.ddm.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ddm_front.Data.AtributosDAO
import com.example.ddm_front.Data.PersonagemDAO
import com.example.ddm_front.Logica.Atributos
import com.example.ddm_front.Logica.Personagem
import com.example.ddm_front.Logica.MapTypeConverter  // Importa o conversor

@Database(entities = [Atributos::class, Personagem::class], version = 2, exportSchema = false)
@TypeConverters(MapTypeConverter::class)  // Adiciona o conversor aqui
abstract class AtributosDB : RoomDatabase() {

    abstract fun atributosDAO(): AtributosDAO
    abstract fun personagemDAO(): PersonagemDAO

    companion object {

        @Volatile
        private var INSTANCIA: AtributosDB? = null

        fun getDatabase(context: Context): AtributosDB {
            return INSTANCIA ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    AtributosDB::class.java,
                    "atributosDB"
                ).fallbackToDestructiveMigration().build()
                INSTANCIA = instancia
                instancia
            }
        }
    }
}
