package com.example.gestion_varitas_hp.Data

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class Varita(
    val madera: String,
    val nucleo: String,
    val longitud: Double,
    val rota: Boolean,
    val personaje: String
) {
}