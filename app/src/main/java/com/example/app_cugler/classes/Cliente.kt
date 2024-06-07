package com.example.app_cugler.classes

class Cliente (cpf: String, nome: String, telefone: String, email: String) {
    var cpf: String
    var nome: String
    var telefone: String
    var email: String

    init {
        this.cpf = cpf
        this.nome = nome
        this.telefone = telefone
        this.email = email
    }
}

