package com.example.music_app.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// archivo para poder conectarnos a internte
object RetrofitClient {

    // constante para tener la base url

    // sustituir base url
    private const val BASE_URL = "https://itunes.apple.com/"

    // variable qu heredad de "lazy"
    val api : ITunesApi by lazy {
        // construir la URL completa
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ITunesApi::class.java)
    }


}