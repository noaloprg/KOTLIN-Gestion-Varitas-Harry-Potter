package com.example.gestion_varitas_hp.servicios

import android.content.Context
import android.widget.Toast

class ToastService {
    companion object {
        fun crearToast(context: Context, mensaje: String) {
            Toast.makeText(
                context,
                mensaje,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}