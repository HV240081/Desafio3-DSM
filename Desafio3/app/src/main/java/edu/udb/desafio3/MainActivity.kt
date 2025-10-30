package edu.udb.desafio3

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.udb.desafio3.controller.CountryController
import edu.udb.desafio3.model.CountryRepository
import edu.udb.desafio3.view.CountryListActivity
import edu.udb.desafio3.view.RegionAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: RegionAdapter
    private val countryController = CountryController(CountryRepository())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configurarVistas()
        cargarRegiones()
    }

    private fun configurarVistas() {
        recyclerView = findViewById(R.id.recyclerViewRegions)
        progressBar = findViewById(R.id.progressBar)

        adapter = RegionAdapter(emptyList()) { region ->
            abrirListaPaises(region)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun cargarRegiones() {
        progressBar.visibility = ProgressBar.VISIBLE

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val regiones = withContext(Dispatchers.IO) {
                    countryController.obtenerRegiones()
                }
                adapter.actualizarDatos(regiones)
                progressBar.visibility = ProgressBar.GONE
            } catch (e: Exception) {
                progressBar.visibility = ProgressBar.GONE
                mostrarError("Error cargando regiones: ${e.message}")
            }
        }
    }

    private fun abrirListaPaises(region: String) {
        val intent = Intent(this@MainActivity, CountryListActivity::class.java)
        intent.putExtra("REGION", region)
        startActivity(intent)
    }

    private fun mostrarError(mensaje: String) {
        Toast.makeText(this, getString(R.string.error_loading_regions, mensaje), Toast.LENGTH_LONG).show()
    }
}