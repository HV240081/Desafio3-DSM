package edu.udb.desafio3.network

import edu.udb.desafio3.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val retrofitPaises = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL_PAISES)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitClima = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL_CLIMA)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val countryApiService: CountryApiService = retrofitPaises.create(CountryApiService::class.java)
    val weatherApiService: WeatherApiService = retrofitClima.create(WeatherApiService::class.java)
}