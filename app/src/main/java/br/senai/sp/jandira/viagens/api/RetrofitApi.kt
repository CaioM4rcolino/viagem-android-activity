package br.senai.sp.jandira.viagens.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import br.senai.sp.jandira.viagens.api.UrlApi.Companion.BASE_URL

class RetrofitApi {

    companion object{
        fun getRetrofit(): Retrofit {

            var retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit
        }
    }


}