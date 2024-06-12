package com.example.app_cugler

import Cliente
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import banco.Banco
import banco.DAO
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val banco = Banco(this)
        val db = banco.writableDatabase
        val dao = DAO(this)

        val cliente = Cliente("44494836850", "Tayna", "11987654321", "tayna@example.com")
        dao.inserirCliente(cliente)

        val button: Button = findViewById(R.id.bt_cadastro)
        button.setOnClickListener {
            val intentCadastro = Intent(this, SegundaTela::class.java)
            startActivity(intentCadastro)
        }

        val editTextCpf: EditText = findViewById(R.id.cpf_t1)
        val buttonEntrar: Button = findViewById(R.id.bt_entrar)

        buttonEntrar.setOnClickListener {
            val cpf = editTextCpf.text.toString()
            val clientePorCpf = dao.procurarClientePorCpf(cpf)
            if (clientePorCpf != null) {
                Toast.makeText(this, "Cliente encontrado", Toast.LENGTH_LONG).show()
                val intent = Intent(this, TerceiraTela::class.java)
                intent.putExtra("cliente", clientePorCpf) // Passando o cliente como extra
                startActivity(intent)
            } else {
                Toast.makeText(this, "Cliente não encontrado", Toast.LENGTH_LONG).show()
            }
        }
    }
}



class SegundaTela : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main2)

            val button: Button = findViewById(R.id.voltar_bt)
            button.setOnClickListener {
                finish()
            }
        }
    }

    class TerceiraTela : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main3)
            val cliente: Cliente? = intent.getParcelableExtra("cliente")
            val dao = DAO(this)
            if (cliente != null) {
                dao.mostrarUmCliente(cliente.cpf)
            }



//            if (cliente != null) {
//                Toast.makeText(this, "Cliente: ${cliente.nome}, CPF: ${cliente.cpf}, Telefone: ${cliente.telefone}, Email: ${cliente.email}", Toast.LENGTH_LONG).show()
//            } else {
//                Toast.makeText(this, "Cliente não encontrado", Toast.LENGTH_LONG).show()
//            }
            val botaoSair: Button = findViewById(R.id.bt_sair)
            botaoSair.setOnClickListener {
                finish()
            }
            val botaoPedidos: Button = findViewById(R.id.pedidos_bt)
            botaoPedidos.setOnClickListener {
                val intentPedidos = Intent(this, QuintaTela::class.java)
                startActivity(intentPedidos)
            }
            val botaoConta: Button = findViewById(R.id.conta_bt)
            botaoConta.setOnClickListener {
                val intentConta = Intent(this, QuartaTela::class.java)
                startActivity(intentConta)
            }


        }
    }

class QuartaTela : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        val botaoSair: Button = findViewById(R.id.voltar_btt_t4)
        botaoSair.setOnClickListener {
            finish()
        }
    }
}

class QuintaTela : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        val botaoSair: Button = findViewById(R.id.voltar_tb_t5)
        botaoSair.setOnClickListener {
            finish()
        }
    }
}



