package com.example.gestion_varitas_hp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    //url base de la api
    private const val BASE_URL = "http://10.0.2.2:8050/api/"

    //variable de retrofit para conectar con la api
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /*
    crear el servicio que obtiene la conexion a traves de retrofit
    y que define los metodos Http de a traves de la interfaz
     */

    val servicio = retrofit.create(ApiService::class.java)
}