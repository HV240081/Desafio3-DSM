package edu.udb.desafio3.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.udb.desafio3.R
import edu.udb.desafio3.model.Country

class CountryAdapter(
    private var paises: List<Country>,
    private val onItemClick: (Country) -> Unit
) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombrePais: TextView = itemView.findViewById(R.id.textCountryName)
        val capitalPais: TextView = itemView.findViewById(R.id.textCountryCapital)
        val imageFlag: ImageView = itemView.findViewById(R.id.imageFlag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pais = paises[position]

        // Configurar textos
        holder.nombrePais.text = pais.name.common
        holder.capitalPais.text = pais.capital?.firstOrNull() ?: "Sin capital"

        // Cargar bandera con Picasso
        Picasso.get()
            .load(pais.flags.png)
            .resize(100, 60)
            .centerInside()
            .into(holder.imageFlag)

        holder.itemView.setOnClickListener {
            onItemClick(pais)
        }
    }

    override fun getItemCount(): Int = paises.size

    fun actualizarDatos(nuevosPaises: List<Country>) {
        paises = nuevosPaises
    }
}