package com.example.gestion_varitas_hp.UI

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gestion_varitas_hp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val vista = binding.root
        setContentView(vista)

        listeners()
    }

    private fun listeners() {
        binding.btnGestion.setOnClickListener { vista ->
            val intent = Intent(this, ListadoVaritas::class.java)
            startActivity(intent)
        }

        binding.btnCreacion.setOnClickListener { vista ->
            val intent = Intent(this, GestionVarita::class.java)
            intent.putExtra("pantalla", "main")
            startActivity(intent)
        }
    }
}