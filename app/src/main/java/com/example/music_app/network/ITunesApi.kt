package com.example.music_app.network

import com.example.music_app.model.ITunesResponsive
import retrofit2.http.GET
import retrofit2.http.Query

//ARCHIVOS PARA LA CONSULTA DE INFOTMACION ( LOCAL O INTERNET )

interface ITunesApi {
    // retroft para conexion a internet
    // INDICAR METODO HTTP A USAR
    @GET("search")

    // Metodo para obteer l informacion co la url de la API
    fun ontenerAlbumGenero(
        // ustituir terminos
        @Query("term") genero : String,
        @Query("country") pais : String,
        @Query("media") media : String,
        @Query("entity") entidad : String,
        @Query("limit") limite : Int,

    ) : retrofit2.Call<ITunesResponsive>







}