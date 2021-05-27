package br.senai.sp.jandira.viagens.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.senai.sp.jandira.viagens.R
import br.senai.sp.jandira.viagens.model.Foto
import com.bumptech.glide.Glide

class GaleriaFotosDestinoAdapter(var context: Context):
    RecyclerView.Adapter<GaleriaFotosDestinoAdapter.ViewHolder>() {


    private var listaDeFotos: List<Foto> = listOf()

    fun updateListaDeFotos(lista: List<Foto>){
        listaDeFotos = lista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(context).inflate(R.layout.destino_foto_layout, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = listaDeFotos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foto = listaDeFotos[position]
        Glide.with(context).load(foto.url).into(holder.imageFoto)

        holder.imageFoto.setOnClickListener {
            Toast.makeText(context, "${foto.id}", Toast.LENGTH_SHORT).show()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val imageFoto = view.findViewById<ImageView>(R.id.image_foto)
    }
}