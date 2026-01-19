package com.example.gestion_varitas_hp.network

import com.example.gestion_varitas_hp.Data.Varita
import com.example.gestion_varitas_hp.Data.VaritaCreateDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("varitas")
    suspend fun getAll(): Response<List<Varita>>

    @GET("varitas/{id}")
    suspend fun getById(@Path("id") id: Int): Varita

    @PUT("varitas/varita/romper/android/{id}")
    suspend fun romper(@Path("id") id: Int): Response<Boolean>

    @POST("varitas/crear")
    suspend fun createVarita(@Body varita: VaritaCreateDTO): Response<Boolean>
}