package com.example.gestion_varitas_hp.network

import com.example.gestion_varitas_hp.Data.Varita
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("varitas")
    suspend fun getAll() : Response<List<Varita>>

    @GET("varitas/{id}")
    suspend fun getById(@Path("id") id : Int) : Varita
}