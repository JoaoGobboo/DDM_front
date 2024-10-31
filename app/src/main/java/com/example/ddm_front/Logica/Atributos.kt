import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "atributos")
data class Atributos(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var pontos: Int = 27,
    var forca: Int = 8,
    var destreza: Int = 8,
    var constituicao: Int = 8,
    var inteligencia: Int = 8,
    var sabedoria: Int = 8,
    var carisma: Int = 8
) {
    fun setAtributo(nome: String, atributo: Int) {
        if (atributo < 8 || atributo > 15) {
            throw IllegalArgumentException("Valor deve estar entre 8 e 15")
        } else if (atributo > pontos && atributo != 8) {
            throw IllegalArgumentException("Pontos insuficientes")
        } else if (atributo != 8) {
            // Lógica de custo: a partir de 13, o custo é 2
            val custo = if (atributo >= 13) 2 else 1
            pontos -= custo
        }

        when (nome) {
            "forca" -> forca = atributo
            "destreza" -> destreza = atributo
            "constituição" -> constituicao = atributo
            "inteligência" -> inteligencia = atributo
            "sabedoria" -> sabedoria = atributo
            "carisma" -> carisma = atributo
            else -> throw IllegalArgumentException("Atributo inválido")
        }
    }

    fun getPontosDisponiveis(): Int {
        return pontos
    }
}
