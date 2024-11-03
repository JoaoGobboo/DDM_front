package up.ddm.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ddm_front.Data.AtributosDAO
import com.example.ddm_front.Data.ClasseDAO
import com.example.ddm_front.Data.PersonagemDAO
import com.example.ddm_front.Data.RacaDAO
import com.example.ddm_front.Logica.Atributos
import com.example.ddm_front.Logica.Classe
import com.example.ddm_front.Logica.Personagem
import com.example.ddm_front.Logica.Raca
import com.example.ddm_front.Logica.MapTypeConverter

@Database(
    entities = [
        Atributos::class,
        Personagem::class,
        Classe::class,
        Raca::class
    ],
    version = 6,  // Incrementar a versão já que estamos adicionando novas tabelas
    exportSchema = false
)
@TypeConverters(MapTypeConverter::class)
abstract class AtributosDB : RoomDatabase() {

    abstract fun atributosDAO(): AtributosDAO
    abstract fun personagemDAO(): PersonagemDAO
    abstract fun racaDAO(): RacaDAO      // Mantido conforme sua preferência
    abstract fun classeDAO(): ClasseDAO   // Mantido conforme sua preferência

    companion object {
        @Volatile
        private var INSTANCIA: AtributosDB? = null

        fun getDatabase(context: Context): AtributosDB {
            return INSTANCIA ?: synchronized(this) {
                val novaInstancia = Room.databaseBuilder(
                    context.applicationContext,
                    AtributosDB::class.java,
                    "atributosDB"
                )
                    .fallbackToDestructiveMigration()  // Isso irá recriar o banco quando a versão mudar
                    .build()
                INSTANCIA = novaInstancia
                novaInstancia
            }
        }
    }
}
