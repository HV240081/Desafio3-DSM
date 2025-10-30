package edu.udb.desafio3.model

import edu.udb.desafio3.network.CountryApiService
import edu.udb.desafio3.network.RetrofitClient

class CountryRepository {
    private val countryApiService: CountryApiService = RetrofitClient.countryApiService

    suspend fun obtenerTodosPaises(): List<Country> {
        return countryApiService.obtenerTodosPaises()
    }

    suspend fun obtenerPaisesPorRegion(region: String): List<Country> {
        return countryApiService.obtenerPaisesPorRegion(region)
    }
}