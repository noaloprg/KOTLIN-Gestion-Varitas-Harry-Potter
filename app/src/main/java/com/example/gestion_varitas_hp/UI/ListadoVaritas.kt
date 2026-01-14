package com.example.gestion_varitas_hp.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.gestion_varitas_hp.Data.Varita
import com.example.gestion_varitas_hp.adapter.VaritasAdapter
import com.example.gestion_varitas_hp.databinding.ActivityListadoVaritasBinding
import com.example.gestion_varitas_hp.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class ListadoVaritas : AppCompatActivity() {
    private lateinit var binding: ActivityListadoVaritasBinding
    private lateinit var adaptador: ArrayAdapter<Varita>
    private lateinit var listaVaritas: List<Varita>
    private val servicio = RetrofitClient.servicio

    //cuando se cree la varita que se actualize la lista volviendola a cargar
    private val lanzador =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                asignarDatos()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListadoVaritasBinding.inflate(layoutInflater)
        val vista = binding.root
        setContentView(vista)
        asignarDatos()
        listeners()
    }

    private fun asignarDatos() {
        //se tiene que acceder desde otro hilo al internet
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                //obtiene la lista de varitas
                val respuesta = servicio.getAll()

                //si es correcto que guarde la lista
                if (respuesta.isSuccessful) {
                    //obtenemos respuesta API
                    val datos = respuesta.body() ?: emptyList()
                    listaVaritas = datos

                    //vuelve al hilo donde se puede usar Context
                    withContext(Dispatchers.Main) {
                        //crea adaptador
                        adaptador = VaritasAdapter(this@ListadoVaritas, listaVaritas)
                        //asigna adaptador
                        binding.lvVaritas.adapter = adaptador
                    }
                } else {
                    Log.e(
                        "LISTA_VARITA",
                        "Error en respuesta: ${respuesta.code()}"
                    )
                }
            } catch (exce: Exception) {
                Log.e("LISTA_VARITA", "Error de conexion: ${exce.message}")
            }
        }
    }

    private fun listeners() {
        binding.btnVolver.setOnClickListener { vista ->
            finish()
        }

        binding.lvVaritas.onItemClickListener =
            AdapterView.OnItemClickListener { lista, vista, pos, id ->
                val intent = Intent(this, GestionVarita::class.java)
                intent.putExtra("indice_varita", pos)
                intent.putExtra("pantalla", "listado_varitas")

                lanzador.launch(intent)
            }
    }
}