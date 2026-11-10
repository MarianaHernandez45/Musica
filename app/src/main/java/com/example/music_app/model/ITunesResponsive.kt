package com.example.music_app.model

data class ITunesResponsive(
    // Confirmacion de la respesta exitosa o no
    // saber si da error o que nos da
    val resultCount : Int,
    val results: List<Album>


)
