package com.example.gestion_varitas_hp.UI

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.gestion_varitas_hp.Data.Varita
import com.example.gestion_varitas_hp.R
import com.example.gestion_varitas_hp.databinding.ActivityGestionVaritaBinding
import com.example.gestion_varitas_hp.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GestionVarita : AppCompatActivity() {
    private lateinit var binding: ActivityGestionVaritaBinding

    private val servicio = RetrofitClient.servicio

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGestionVaritaBinding.inflate(layoutInflater)
        val vista = binding.root
        setContentView(vista)

        inicializar()
        listeners()
        setDatosIniciales()
    }

    private fun setDatosIniciales() {
        val indice = intent.getIntExtra("indice_varita", -1)

        if (indice != -1) {
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val varita = servicio.getById(indice + 1)
                    ponerDatosVarita(varita)

                } catch (e: Exception) {
                    Log.e("GESTION_VARITAS", "${e.message}")
                }
            }
        }
    }

    private fun ponerDatosVarita(v: Varita) {
        binding.etLongitud.setText(v.longitud.toString())
        binding.etMadera.setText(v.madera)
        binding.etNucleo.setText(v.nucleo)
        binding.cbRota.isChecked = v.rota
    }

    private fun listeners() {
        binding.btnVolver.setOnClickListener { vista ->
            finish()
        }
    }

    private fun inicializar() {
        val pantalla = intent.getStringExtra("pantalla")

        if (pantalla.equals("listado_varitas")) {
            binding.btnCrearBorrar.setText("MODIFICAR")
        } else if (pantalla.equals("main")) {
            binding.btnCrearBorrar.setText("CREAR")
        }
    }
}