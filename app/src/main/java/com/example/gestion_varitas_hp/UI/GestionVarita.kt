package com.example.gestion_varitas_hp.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.gestion_varitas_hp.Data.Varita
import com.example.gestion_varitas_hp.Data.VaritaCreateDTO
import com.example.gestion_varitas_hp.Dialog.OperacionVaritaDialog
import com.example.gestion_varitas_hp.R
import com.example.gestion_varitas_hp.databinding.ActivityGestionVaritaBinding
import com.example.gestion_varitas_hp.network.RetrofitClient
import com.example.gestion_varitas_hp.servicios.ToastService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GestionVarita : AppCompatActivity() {
    private lateinit var binding: ActivityGestionVaritaBinding
    private lateinit var listaEditText: List<EditText>
    private val servicio = RetrofitClient.servicio

    private var idVarita = -1

    //tipo de operacion del boton
    private var operacion = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGestionVaritaBinding.inflate(layoutInflater)
        val vista = binding.root
        setContentView(vista)

        inicializar()
        listeners()
        //si viene de la activity main muestra la pantalla normal, sin poner datos
        setDatosIniciales()
    }

    private fun crearVarita() {
        val varita = crearVaritaDesdeEditText()

        if (varita != null) {
            lifecycleScope.launch(Dispatchers.IO) {

                val resultadoApi = servicio.createVarita(varita)

                //validacion de la repsuesta de la API al metodo POST
                if (resultadoApi.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        val dialogo = OperacionVaritaDialog.newInstance(
                            "Varita creada",
                            "Creado"
                        )
                        dialogo.show(supportFragmentManager, OperacionVaritaDialog.CLAVE)
                    }
                } else {
                    //accede al hilo principal
                    withContext(Dispatchers.Main) {
                        ToastService.crearToast(
                            this@GestionVarita,
                            "La varita no pudo ser creada",
                        )

                    }
                }
            }
        } else {
            ToastService.crearToast(this, "Debe rellenar todos los campos")
        }
    }

    private fun romperVarita() {
        //comprueba la seleccion de la varita
        if (idVarita != -1) {

            //si esta checked es que ya esta rota y no realiza ninguna accion
            if (binding.cbRota.isChecked) {

                val dialogo = OperacionVaritaDialog.newInstance(
                    "La varita ya se encuentra rota",
                    "Accion no disponible"
                )
                dialogo.show(supportFragmentManager, OperacionVaritaDialog.CLAVE)
            } else {
                //si esta rota llama al metodo de la API que la rompe
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val respuestaAPI = servicio.romper(idVarita)

                        //Gestion del resultado de la API
                        if (respuestaAPI.isSuccessful) {
                            withContext(Dispatchers.Main) {
                                val dialogo = OperacionVaritaDialog.newInstance(
                                    String.format(
                                        "Varita %d rota",
                                        idVarita
                                    ), "Actualizado"
                                )
                                dialogo.show(supportFragmentManager, OperacionVaritaDialog.CLAVE)

                            }
                        } else {
                            Log.w("API_GESTION", "No se ha actualizado la varita")
                            withContext(Dispatchers.Main) {
                                ToastService.crearToast(
                                    this@GestionVarita,
                                    "La varita no pudo ser actualizada"
                                )
                            }
                            //comprobacion de errores de la API
                            val codigoError = respuestaAPI.code()
                            val mensajeError = respuestaAPI.errorBody()?.string()

                            Log.e("API_ERROR", "Código: $codigoError")
                            Log.e("API_ERROR", "Cuerpo: $mensajeError")
                        }
                    } catch (e: Exception) {
                        Log.e("GESTION_ROMPER", "${e.message}")

                    }
                }
            }
        }
    }

    //obtiene los valores de los EditText y a traves de ellos crea la el DTO de creacion
    private fun crearVaritaDesdeEditText(): VaritaCreateDTO? {

        if (comprobarCamposEditText()) {
            var madera = binding.etMadera.text.toString()
            var nucleo = binding.etNucleo.text.toString()
            var longitud = binding.etLongitud.text.toString().toBigDecimal()
            var rota = binding.cbRota.isChecked

            return VaritaCreateDTO(madera, nucleo, longitud, rota);
        } else return null
    }

    //comprueba que todos los campos del editText esten completos
    private fun comprobarCamposEditText(): Boolean {
        var esCorrecto = true
        for (et in listaEditText) {
            if (et.text.isNullOrEmpty()) esCorrecto = false
        }
        return esCorrecto
    }

    private fun listeners() {
        binding.btnVolver.setOnClickListener { vista ->
            finish()
        }

        binding.btnCrearBorrar.setOnClickListener { vista ->
            if (operacion.equals("ROMPER")) {
                romperVarita()
            } else {
                crearVarita()
            }
        }

        //listener que gestiona el flujo de las activities despues de crear o romper varita
        supportFragmentManager.setFragmentResultListener(
            OperacionVaritaDialog.CLAVE,
            this
        ) { _, _ ->
            //si rompe devuelve resultado a la activity de la lista y se cierra. Asi no tiene que vovler a abrirla
            if (operacion == "ROMPER") {
                var intent = Intent()
                setResult(RESULT_OK, intent)
                finish()
                Log.d("INTENT_GESTION", "cierra activity y vuelve a lista")

            } else {
                //si viene de la activity main cierra esta y abre el listado desde 0 para mostrar la nueva varita
                var intent = Intent(this, ListadoVaritas::class.java)
                startActivity(intent)
                finish()
                Log.d("INTENT_GESTION", "cierra activity y abre a lista")
            }
        }
    }

    //pone datos de la varita en los EditText y el checkBox
    private fun setDatosVaritaEditText(v: Varita) {
        binding.etLongitud.setText(v.longitud.toString())
        binding.etMadera.setText(v.madera)
        binding.etNucleo.setText(v.nucleo)
        binding.cbRota.isChecked = v.rota
    }

    //datos que se muestran al abrir la activity
    private fun setDatosIniciales() {

        //si es -1 quiere decir que no se ha seleccionado ninguna varita
        if (idVarita != -1) {
            lifecycleScope.launch(Dispatchers.IO) {
                try {

                    //obtiene varita por id de la API
                    val varita = servicio.getById(idVarita)
                    Log.i("API_GESTION", "metodo GET por ID funciona correctamente")

                    setDatosVaritaEditText(varita)

                } catch (e: Exception) {
                    Log.e("API_GESTION", "${e.message}")
                }
            }
        }
    }

    private fun activarDesactivarEditText(activar: Boolean) {
        for (et in listaEditText) {
            //si es true se permite modificar
            if (activar) {
                et.isEnabled = true
                binding.cbRota.isEnabled = true
            } else {
                et.isEnabled = false
                binding.cbRota.isEnabled = false
            }
        }

    }

    private fun inicializar() {
        listaEditText = listOf(binding.etMadera, binding.etNucleo, binding.etLongitud)

        //otencion de datos de las activities
        val pantalla = intent.getStringExtra("pantalla")
        idVarita = intent.getIntExtra("id_varita", -1)

        //acciones en funcion de la activity que la ha abierto
        if (pantalla.equals("listado_varitas")) {
            //modificacion del boton
            binding.btnCrearBorrar.setText("ROMPAR VARITA")
            binding.btnCrearBorrar.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.rojo
                )
            )

            operacion = "ROMPER"
            activarDesactivarEditText(false)

        } else if (pantalla.equals("main")) {
            binding.btnCrearBorrar.setText("CREAR")
            binding.btnCrearBorrar.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.amarilloPobre
                )
            )
            operacion = "CREAR"
            activarDesactivarEditText(true)
        }
    }
}