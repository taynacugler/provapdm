package com.example.app_cugler

import Cliente
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import banco.Banco
import banco.DAO
import com.example.app_cugler.classes.Pamonha

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
        //mostrar as classes e o banco antes
        //conexão com o banco
        val banco = Banco(this)
        val db = banco.writableDatabase
        val dao = DAO(this)

        //criei um objeto cliente e chamei o metodo inserir cliente do dao para teste
        val cliente = Cliente("44494836850", "Tayna", "11987654321", "tayna@example.com")
        dao.inserirCliente(cliente)

        //se eu clicar no botão cadastro da primeira página ele vai chama a "classe" intentCadastro que é a segunda tela (
        val button: Button = findViewById(R.id.bt_cadastro)
        button.setOnClickListener {
            val intentCadastro = Intent(this, SegundaTela::class.java)
            startActivity(intentCadastro)
        }


        val editTextCpf: EditText = findViewById(R.id.cpf_t1) //pega o cpf
        val buttonEntrar: Button = findViewById(R.id.bt_entrar) //quando eu clico no botão entrar

        buttonEntrar.setOnClickListener {
            val cpf = editTextCpf.text.toString() //transforma o cpf em string
            val clientePorCpf = dao.procurarClientePorCpf(cpf) //procura se existe um cpf no banco
            if (clientePorCpf != null) {
                //se não for nulo ou seja se existir um cliente com esse cpf ele mostra na tela que foi encontrado e chama a classe da terceira tela
                Toast.makeText(this, "Cliente encontrado", Toast.LENGTH_LONG).show()
                val intent = Intent(this, TerceiraTela::class.java)
                intent.putExtra("cliente", clientePorCpf) // Passando o cliente como extra pra não perder a referencia
                startActivity(intent)
            } else {
                Toast.makeText(this, "Cliente não encontrado", Toast.LENGTH_LONG).show() //se não achar ele avisa que não foi encontrado e continua na pagina inicial
            }
        }
    }
}


//quando clica no cadastro vem pra essa tela
class SegundaTela : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main2) //aparece a tela 2

            val button: Button = findViewById(R.id.voltar_bt) //se clicar no botão voltar ele faz "finish" e vai pra
            button.setOnClickListener {
                finish()
            }
            val botaoCad: Button = findViewById(R.id.cadastro_bt) //botão de cadastro
            botaoCad.setOnClickListener{
                val dao = DAO (this) //objeto dao para chamar o metodo

                //pega os valores que colocar na tela

                    val nome: EditText = findViewById(R.id.nome_t2)
                    val cpf: EditText = findViewById(R.id.cpf_t2)
                    val telefone: EditText = findViewById(R.id.telefone_t2)
                    val email: EditText = findViewById(R.id.email_t2)

                    //transforma tudo em string
                   val nomeText = nome.text.toString()
                   val cpfText = cpf.text.toString()
                   val telefoneText = telefone.text.toString()
                   val emailText = email.text.toString()

                val clienteTeste: Cliente? = dao.procurarClientePorCpf(cpfText) //ele procura o cliente por cpf

              //  se o cliente voltar não nulo quer dizer que ja existe
                if (clienteTeste == null) {
                    //cria um objeto cliente
                    val cliente = Cliente (
                        nome = nomeText,
                        cpf = cpfText,
                        telefone = telefoneText,
                        email = emailText
                    )
                    dao.inserirCliente(cliente)
                    Toast.makeText(this, "Cliente inserido", Toast.LENGTH_LONG).show()
                    //volta pra pagina inicial
                    finish()
                }
                else {
                    //avisa que ja existe um
                    Toast.makeText(this, "Já existe um cliente com esse cpf", Toast.LENGTH_LONG).show()
                }

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


            if (cliente != null) {
                Toast.makeText(this, "Cliente: ${cliente.nome}, CPF: ${cliente.cpf}, Telefone: ${cliente.telefone}, Email: ${cliente.email}", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Cliente não encontrado", Toast.LENGTH_LONG).show()
            }
            val botaoSair: Button = findViewById(R.id.bt_sair)
            botaoSair.setOnClickListener {
                finish()
            }
            val botaoPedidos: Button = findViewById(R.id.pedidos_bt)
            botaoPedidos.setOnClickListener {
                val intentPedidos = Intent(this, QuintaTela::class.java)
                intentPedidos.putExtra("cliente", cliente)
                startActivity(intentPedidos)
            }
            val botaoConta: Button = findViewById(R.id.conta_bt)
            botaoConta.setOnClickListener {
                val intent = Intent(this, QuartaTela::class.java)
                intent.putExtra("cliente", cliente) // Passando o cliente como extra
                startActivity(intent)
            }


        }
    }

class QuartaTela : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        val cliente: Cliente? = intent.getParcelableExtra("cliente")

        val nomeTextView: TextView = findViewById(R.id.nome_t4)
        val cpfTextView: TextView = findViewById(R.id.cpf_t4)
        val telefoneTextView: TextView = findViewById(R.id.telefone_t4)
        val emailTextView: TextView = findViewById(R.id.email_t4)

        nomeTextView.text = "Nome: ${cliente?.nome}"
        cpfTextView.text = "CPF: ${cliente?.cpf}"
        telefoneTextView.text = "Telefone: ${cliente?.telefone}"
        emailTextView.text = "E-mail: ${cliente?.email}"


        val botaoSair: Button = findViewById(R.id.voltar_btt_t4)
        botaoSair.setOnClickListener {
            finish()
        }
        val botaoAtt: Button = findViewById(R.id.att_bt)
        val dao = DAO (this)
        botaoAtt.setOnClickListener {
            val nome: EditText = findViewById(R.id.getNome)
            val telefone: EditText = findViewById(R.id.getTelefone)
            val email: EditText = findViewById(R.id.getEmail)

            val nomeText = nome.text.toString()
            val telefoneText = telefone.text.toString()
            val emailText = email.text.toString()

            //pegar o cpf do cliente
            if (cliente != null) {
                val clienteAtt = Cliente(cliente.cpf, nomeText, telefoneText, emailText)
                dao.atualizarCliente(clienteAtt)
            }

        }
        val botaoDelete: Button = findViewById((R.id.delete_bt))
        botaoDelete.setOnClickListener {
            //pegar cpf do cliente
            if (cliente != null) {
                dao.excluirCliente(cliente.cpf)
            }
        }
    }
}

class QuintaTela : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)
        val cliente: Cliente? = intent.getParcelableExtra("cliente")

        val botaoPedido: Button = findViewById(R.id.pedir)
        botaoPedido.setOnClickListener {
            val recheio: EditText = findViewById(R.id.recheio)
            val queijo: EditText = findViewById(R.id.queijo)

            val recheioText = recheio.text.toString()
            val queijoText = queijo.text.toString()

            // Convertendo o texto para Float de forma segura
            val queijoPeso = queijoText.toFloatOrNull()

            val cpf = cliente?.cpf
            val pedido =
                    Pamonha(
                        tipoDeRecheio = recheioText,
                        pesoDeQueijo = queijoPeso,
                        fkCpf = cpf.toString()
                    )

            val dao = DAO (this)
            if (pedido != null) {
                dao.inserirPamonha(pedido)
                dao.mostrarUmaPamonha(1)
            }

            }


        val botaoSair: Button = findViewById(R.id.sair_t5)
        botaoSair.setOnClickListener {
            finish()
        }

    }


}




