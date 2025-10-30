package edu.udb.desafio3.view

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.udb.desafio3.R
import edu.udb.desafio3.controller.CountryController
import edu.udb.desafio3.model.Country
import edu.udb.desafio3.model.CountryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CountryListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: CountryAdapter
    private val countryController = CountryController(CountryRepository())

    private var region: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_list)

        region = intent.getStringExtra("REGION") ?: ""
        supportActionBar?.title = getString(R.string.title_countries_by_region, region)

        configurarVistas()
        cargarPaises()
    }

    private fun configurarVistas() {
        recyclerView = findViewById(R.id.recyclerViewCountries)
        progressBar = findViewById(R.id.progressBar)

        adapter = CountryAdapter(emptyList()) { pais ->
            abrirDetallePais(pais)
        }

        // GridLayoutManager con 2 columnas
        val gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter
    }

    private fun cargarPaises() {
        progressBar.visibility = ProgressBar.VISIBLE

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val paises = withContext(Dispatchers.IO) {
                    countryController.obtenerPaisesPorRegion(region)
                }
                adapter.actualizarDatos(paises)
                progressBar.visibility = ProgressBar.GONE
            } catch (e: Exception) {
                progressBar.visibility = ProgressBar.GONE
                mostrarError("Error cargando países: ${e.message}")
            }
        }
    }

    private fun abrirDetallePais(pais: Country) {
        val intent = Intent(this, CountryDetailActivity::class.java).apply {
            putExtra("COUNTRY", pais)
        }
        startActivity(intent)
    }

    private fun mostrarError(mensaje: String) {
        Toast.makeText(this, getString(R.string.error_loading_countries, mensaje), Toast.LENGTH_LONG).show()
    }
}