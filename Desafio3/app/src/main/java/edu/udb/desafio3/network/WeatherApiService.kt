package edu.udb.desafio3.network

import edu.udb.desafio3.model.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("current.json")
    suspend fun obtenerClimaActual(
        @Query("key") claveApi: String,
        @Query("q") ciudad: String
    ): Weather
}