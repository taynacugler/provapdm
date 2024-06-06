import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Cliente (var cpf: String, var nome: String, var telefone: String, var email: String): Parcelable{
    override fun toString(): String {
        return ("CPF: $cpf | Nome: $nome | Telefone: $telefone | Email: $email")
    }
}
