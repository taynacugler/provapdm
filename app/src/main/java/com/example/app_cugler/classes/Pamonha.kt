package com.example.app_cugler.classes

class Pamonha(idPamonha: Int, tipoDeRecheio: String, pesoDeQueijo: Float, fkCpf: String) {
    var idPamonha: Int
    var tipoDeRecheio: String
    var pesoDeQueijo: Float
    var fkCpf: String

    init {
        this.idPamonha = idPamonha
        this.tipoDeRecheio = tipoDeRecheio
        this.pesoDeQueijo = pesoDeQueijo
        this.fkCpf = fkCpf
    }
}