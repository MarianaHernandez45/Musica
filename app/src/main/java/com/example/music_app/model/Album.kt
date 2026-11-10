package com.example.music_app.model

// ARCHIVO PARA OBTENER LA INFORMACIÓN QUE REGRESA LA API
data class Album(

    // SINO DE INTERROGAI´N ES PAR QUE NO CRASHEE
    val wrapperType: String?,
    val collectionType: String?,

    val artistId: Long?,
    val collectionId: Long?,

    val artistName: String?,
    val collectionName: String?,

    val artistViewUrl: String?,
    val collectionViewUrl: String?,


    val artworkUrl100: String?,

    val collectionPrice: Double?,
    val trackCount: Int?,
    val releaseDate: String?,
    val primaryGenreName: String?,
    val country: String?,
    val currency: String?
)
{
// METODO PARA OBTENER LA IMAGEN MAS GRANDE
fun obtenerImagnGrnde() : String{
    // en caso de que no encuntre la url va  apasar ua cadena vacia
    return artworkUrl100?.replace("100x100bb.jpg", "600x600bb.jpg") ?: ""
}
}
