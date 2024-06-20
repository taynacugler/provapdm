package banco

import Cliente
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.app_cugler.classes.Pamonha

class DAO(contexto: Context) {
    var contexto: Context
    var meuBanco: Banco

    init {
        this.contexto = contexto
        this.meuBanco = Banco(contexto)
    }

    fun inserirCliente(cliente: Cliente) {
        val db_insercao = meuBanco.writableDatabase
        val cv_valores = ContentValues().apply {
            put("cpf", cliente.cpf)
            put("nome", cliente.nome)
            put("telefone", cliente.telefone)
            put("email", cliente.email)
        }
        val confirmaInsercao = db_insercao.insert("cliente", null, cv_valores)
        Log.i("Teste", "-> Inserção Cliente: $confirmaInsercao")
    }
    fun procurarClientePorCpf(cpf: String): Cliente? {
        val db_consulta = meuBanco.readableDatabase
        val cursor = db_consulta.query(
            "cliente", // Nome da tabela
            arrayOf("cpf", "nome", "telefone", "email"),
            "cpf = ?", //where
            arrayOf(cpf),
            null,
            null,
            null
        )

        var cliente: Cliente? = null

        if (cursor.moveToFirst()) {
            val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
            val telefone = cursor.getString(cursor.getColumnIndexOrThrow("telefone"))
            val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            cliente = Cliente(cpf, nome, telefone, email)
        }
        cursor.close()
        return cliente
    }

    fun mostrarUmCliente(cpf: String) {
        val db_leitura = meuBanco.readableDatabase
        val cursor = db_leitura.rawQuery("select * from cliente where cpf = ?", arrayOf(cpf))
        if (cursor.moveToFirst()) {
            Log.i("Teste", "-> CPF: ${cursor.getString(0)} - Nome: ${cursor.getString(1)} - Telefone: ${cursor.getString(2)} - Email: ${cursor.getString(3)}")
        }
        cursor.close()
    }

    fun mostrarTodosClientes() {
        val db_leitura = meuBanco.readableDatabase
        val cursor = db_leitura.rawQuery("select * from cliente", null)
        with(cursor) {
            while (moveToNext()) {
                val cpf = getString(getColumnIndexOrThrow("cpf"))
                val nome = getString(getColumnIndexOrThrow("nome"))
                val telefone = getString(getColumnIndexOrThrow("telefone"))
                val email = getString(getColumnIndexOrThrow("email"))
                Log.i("Teste", "-> CPF: $cpf - Nome: $nome - Telefone: $telefone - Email: $email")
            }
        }
        cursor.close()
    }

    fun excluirCliente(cpf: String) {
        val db_exclusao = meuBanco.readableDatabase
        val condicao = "cpf = ?"
        val confirmaExclusao = db_exclusao.delete("cliente", condicao, arrayOf(cpf))
        Log.i("Teste", "-> Exclusão Cliente: $confirmaExclusao")
    }

    fun atualizarCliente(cliente: Cliente) {
        val db_atualizacao = meuBanco.writableDatabase
        val cv_valores = ContentValues().apply {
            put("cpf", cliente.cpf)
            put("nome", cliente.nome)
            put("telefone", cliente.telefone)
            put("email", cliente.email)
        }
        val condicao = "cpf = ?"
        val confirmaAtualizacao = db_atualizacao.update("cliente", cv_valores, condicao, arrayOf(cliente.cpf))
        Log.i("Teste", "-> Atualização Cliente: $confirmaAtualizacao")
    }

    fun inserirPamonha(pamonha: Pamonha) {
        val db_insercao = meuBanco.writableDatabase
        val cv_valores = ContentValues().apply {
            put("idPamonha", pamonha.idPamonha)
            put("tipoDeRecheio", pamonha.tipoDeRecheio)
            put("pesoDeQueijo", pamonha.pesoDeQueijo)
            put("fkCpf", pamonha.fkCpf)
        }
        val confirmaInsercao = db_insercao.insert("pamonha", null, cv_valores)
        Log.i("Teste", "-> Inserção Pamonha: $confirmaInsercao")
    }

    fun mostrarUmaPamonha(idPamonha: Int) {
        val db_leitura = meuBanco.readableDatabase
        val cursor = db_leitura.rawQuery("select * from pamonha where idPamonha = ?", arrayOf(idPamonha.toString()))
        if (cursor.moveToFirst()) {
            Log.i("Teste", "-> ID: ${cursor.getInt(0)} - Tipo de Recheio: ${cursor.getString(1)} - Peso de Queijo: ${cursor.getFloat(2)} - CPF: ${cursor.getString(3)}")
        }
        cursor.close()
    }

    fun mostrarTodasPamomhas() {
        val db_leitura = meuBanco.readableDatabase
        val cursor = db_leitura.rawQuery("select * from pamonha", null)
        with(cursor) {
            while (moveToNext()) {
                val idPamonha = getInt(getColumnIndexOrThrow("idPamonha"))
                val tipoDeRecheio = getString(getColumnIndexOrThrow("tipoDeRecheio"))
                val pesoDeQueijo = getFloat(getColumnIndexOrThrow("pesoDeQueijo"))
                val fkCpf = getString(getColumnIndexOrThrow("fkCpf"))
                Log.i("Teste", "-> ID: $idPamonha - Tipo de Recheio: $tipoDeRecheio - Peso de Queijo: $pesoDeQueijo - CPF: $fkCpf")
            }
        }
        cursor.close()
    }

    fun excluirPamonha(idPamonha: Int) {
        val db_exclusao = meuBanco.readableDatabase
        val condicao = "idPamonha = ?"
        val confirmaExclusao = db_exclusao.delete("pamonha", condicao, arrayOf(idPamonha.toString()))
        Log.i("Teste", "-> Exclusão Pamonha: $confirmaExclusao")
    }

    fun atualizarPamonha(pamonha: Pamonha) {
        val db_atualizacao = meuBanco.writableDatabase
        val cv_valores = ContentValues().apply {
            put("idPamonha", pamonha.idPamonha)
            put("tipoDeRecheio", pamonha.tipoDeRecheio)
            put("pesoDeQueijo", pamonha.pesoDeQueijo)
            put("fkCpf", pamonha.fkCpf)
        }
        val condicao = "idPamonha = ?"
        val confirmaAtualizacao = db_atualizacao.update("pamonha", cv_valores, condicao, arrayOf(pamonha.idPamonha.toString()))
        Log.i("Teste", "-> Atualização Pamonha: $confirmaAtualizacao")
    }


}
