package edu.udb.desafio3.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.udb.desafio3.R

class RegionAdapter(
    private var regiones: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<RegionAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreRegion: TextView = itemView.findViewById(R.id.textRegionName)
        val imageRegionBackground: ImageView = itemView.findViewById(R.id.imageRegionBackground)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_region, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val region = regiones[position]
        val nombreTraducido = traducirRegion(region, holder.itemView.context)

        holder.nombreRegion.text = nombreTraducido

        val imageResource = obtenerImagenMipmap(region)
        holder.imageRegionBackground.setImageResource(imageResource)

        holder.itemView.setOnClickListener {
            onItemClick(region)
        }
    }

    override fun getItemCount(): Int = regiones.size

    fun actualizarDatos(nuevasRegiones: List<String>) {
        regiones = nuevasRegiones
    }

    private fun traducirRegion(region: String, context: Context): String {
        return when (region) {
            "Africa" -> context.getString(R.string.region_africa)
            "America" -> context.getString(R.string.region_americas)
            "Asia" -> context.getString(R.string.region_asia)
            "Europe" -> context.getString(R.string.region_europe)
            "Oceania" -> context.getString(R.string.region_oceania)
            else -> region
        }
    }

    private fun obtenerImagenMipmap(region: String): Int {
        return when (region) {
            "Africa" -> R.mipmap.africa
            "America" -> R.mipmap.america
            "Asia" -> R.mipmap.asia
            "Europe" -> R.mipmap.europa
            "Oceania" -> R.mipmap.oceania
            else -> R.mipmap.ic_launcher
        }
    }
}