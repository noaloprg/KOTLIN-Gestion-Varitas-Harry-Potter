package com.example.gestion_varitas_hp.Data

import java.math.BigDecimal

data class VaritaCreateDTO(
    val madera: String,
    val nucleo: String,
    val longitud: BigDecimal,
    val rota: Boolean,
) {
}