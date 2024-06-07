package banco

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class Banco(context: Context) : SQLiteOpenHelper(context, "BancoIFTMCAUPT2024", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val SQL_criacaoCliente = """
            CREATE TABLE cliente (
                cpf VARCHAR(20) NOT NULL PRIMARY KEY, 
                nome VARCHAR(50) NOT NULL, 
                telefone VARCHAR(20) NOT NULL, 
                email VARCHAR(50) NOT NULL
            )
        """.trimIndent()

        val SQL_criacaoPamonha = """
            CREATE TABLE pamonha (
                idPamonha INTEGER NOT NULL PRIMARY KEY, 
                tipoDeRecheio VARCHAR(50) NOT NULL, 
                pesoDeQueijo REAL NOT NULL, 
                fkCpf VARCHAR(20) NOT NULL,
                FOREIGN KEY (fkCpf) REFERENCES cliente(cpf)
            )
        """.trimIndent()

        db.execSQL(SQL_criacaoCliente)
        db.execSQL(SQL_criacaoPamonha)
    }

    override fun onUpgrade(db: SQLiteDatabase, versaoAntiga: Int, novaVersao: Int) {
        val SQL_exclusaoCliente = "DROP TABLE IF EXISTS cliente"
        val SQL_exclusaoPamonha = "DROP TABLE IF EXISTS pamonha"

        db.execSQL(SQL_exclusaoCliente)
        db.execSQL(SQL_exclusaoPamonha)

        onCreate(db)
    }
}