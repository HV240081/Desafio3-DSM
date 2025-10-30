package edu.udb.desafio3.network

import edu.udb.desafio3.model.Country
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryApiService {
    @GET("all")
    suspend fun obtenerTodosPaises(): List<Country>

    @GET("region/{region}")
    suspend fun obtenerPaisesPorRegion(@Path("region") region: String): List<Country>
}