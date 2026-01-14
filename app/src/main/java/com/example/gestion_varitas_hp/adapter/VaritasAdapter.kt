package com.example.gestion_varitas_hp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.gestion_varitas_hp.Data.Varita
import com.example.gestion_varitas_hp.R

class VaritasAdapter(context: Context, private val varitas: List<Varita>) :
    ArrayAdapter<Varita>(context, R.layout.item_varita, varitas) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //crear inflador de vistas
        val inflador = LayoutInflater.from(context)
        //obtener el XML qeu contiene como se van a proyectar los datos
        val vista = inflador.inflate(R.layout.item_varita, parent, false)

        //controladores de la vista para asignar datos
        val titulo = vista.findViewById<TextView>(R.id.tvInfoVarita)
        val subtitulo = vista.findViewById<TextView>(R.id.tvInfoPersonaje)

        //asignacion de valores a los controladores
        titulo.text = String.format("%s - %s ", varitas[position].madera, varitas[position].nucleo)
        subtitulo.text = varitas[position].personaje

        //devuelve la vista con los datos asignados
        return vista
    }
}