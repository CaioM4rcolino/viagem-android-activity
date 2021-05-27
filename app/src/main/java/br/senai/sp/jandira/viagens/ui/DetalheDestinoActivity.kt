package br.senai.sp.jandira.viagens.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.senai.sp.jandira.viagens.R
import br.senai.sp.jandira.viagens.adapter.GaleriaFotosDestinoAdapter
import br.senai.sp.jandira.viagens.api.FotoCall
import br.senai.sp.jandira.viagens.api.RetrofitApi
import br.senai.sp.jandira.viagens.model.DestinosRecentes
import br.senai.sp.jandira.viagens.model.Foto
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class DetalheDestinoActivity : AppCompatActivity() {

    lateinit var collapsingToolbar: CollapsingToolbarLayout
    lateinit var ivFotoDestino: ImageView
    lateinit var tvLocal: TextView
    lateinit var tvValor: TextView
    lateinit var tvTextoDescricao: TextView
    lateinit var rvGaleriaFotosDestino: RecyclerView
    lateinit var galeriaFotosDestinoAdapter: GaleriaFotosDestinoAdapter
    lateinit var destinoRecente: DestinosRecentes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            R.layout.activity_detalhe_destino)

        carregarDados()
        carregarListaDeFotos()
    }

    private fun carregarListaDeFotos() {
        rvGaleriaFotosDestino = findViewById(R.id.rv_galeria_fotos_destino)
        rvGaleriaFotosDestino.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        galeriaFotosDestinoAdapter = GaleriaFotosDestinoAdapter(this)
        rvGaleriaFotosDestino.adapter = galeriaFotosDestinoAdapter

        //fotos que preencheraão a recycler view
        var fotos: List<Foto> = emptyList()

        //instanciar retrofit
        val retrofit = RetrofitApi.getRetrofit()

        //fazer chamada para a interface
        val fotosCall = retrofit.create(FotoCall::class.java)
        val call = fotosCall.getFotosDestino(destinoRecente.id)

        //Executar a requisição para a API
        call.enqueue(object : Callback<List<Foto>>{
            override fun onFailure(call: Call<List<Foto>>, t: Throwable) {
                Toast.makeText(this@DetalheDestinoActivity, "deu ruim", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Foto>>, response: Response<List<Foto>>) {
                fotos = response.body()!!
                galeriaFotosDestinoAdapter.updateListaDeFotos(fotos)
            }

        })
    }

    private fun carregarDados() {
        collapsingToolbar = findViewById(R.id.collapsing_toolbar)
        ivFotoDestino = findViewById(R.id.iv_destino)
        tvLocal = findViewById(R.id.tv_local)
        tvValor = findViewById(R.id.tv_valor)
        tvTextoDescricao = findViewById(R.id.tv_texto_descricao)

        destinoRecente =
            intent.getSerializableExtra("destino") as DestinosRecentes


        collapsingToolbar.title = destinoRecente.nome
        tvLocal.text = destinoRecente.nome
        tvValor.text = destinoRecente.valor.toString()
        tvTextoDescricao.text = destinoRecente.descricao

        if (destinoRecente.urlFoto !== "")
            Glide.with(this).load(destinoRecente.urlFoto).into(ivFotoDestino)
        else
            ivFotoDestino.setImageResource(R.drawable.porto_galinhas)
    }
}