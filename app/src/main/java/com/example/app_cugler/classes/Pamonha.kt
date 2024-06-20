package com.example.app_cugler.classes


class Pamonha(
    var tipoDeRecheio: String,
    var pesoDeQueijo: Float?,
    var fkCpf: String
) {
    var idPamonha: Int

    companion object {
        private var lastId: Int = 0
    }

    init {
        lastId++
        idPamonha = lastId
    }
}