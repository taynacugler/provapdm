package com.example.app_cugler

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import banco.Banco
import banco.DAO
import com.example.app_cugler.classes.Cliente

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

        val cliente = Cliente("12345678900", "João da Silva", "11987654321", "joao@example.com")
        dao.inserirCliente(cliente)
        dao.mostrarUmCliente("12345678900")
    }



}