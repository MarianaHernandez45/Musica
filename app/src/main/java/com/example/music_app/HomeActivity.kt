package com.example.music_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.LayoutInflaterCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideType
import com.example.music_app.model.Album
import com.example.music_app.model.ITunesResponsive
import com.example.music_app.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class HomeActivity : AppCompatActivity() {

    private lateinit var tvResultado : TextView


    private lateinit var contenedorIndie : LinearLayout
    private lateinit var contenedorRock : LinearLayout
    private lateinit var contenedorNsqk : LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        tvResultado = findViewById(R.id.tvResultado)
        contenedorIndie = findViewById(R.id.contenedorIndie)
        contenedorRock = findViewById(R.id.contenedorRock)
        contenedorNsqk = findViewById(R.id.contenedorNsqk)





        // AGREGAR METODO LLAMADO DE ALBUM
        obtenerAlbum("indie", contenedorIndie)
        obtenerAlbum("rock", contenedorRock)
        obtenerAlbum("Nsqk", contenedorNsqk)




    }

    // METODO PARA OBTENER INFORMACION DEL ALBUM
    private fun obtenerAlbum(genero : String, contenedor : LinearLayout){
        // Hacer el llamdo a la construccion de la URL
        RetrofitClient.api.ontenerAlbumGenero(
            genero = genero,
            pais = "MX",
            media = "music",
            entidad = "album",
            limite = 10,
        ).enqueue( object : Callback<ITunesResponsive?> {
            override fun onResponse(
                call: Call<ITunesResponsive?>,
                response: Response<ITunesResponsive?>
            ) {
               // variable para guardar albumes
                // variable para guardar en una lista tipo album
                val albumes = response.body()?.results ?: emptyList()

                // validar si la variable albumes tiene informacion
                if(albumes.isNotEmpty()){
                    tvResultado.visibility = View.GONE
                    mostrarAlbumes(albumes, contenedor)


                }else{
                    Toast.makeText(this@HomeActivity, "NO HAY RESPUESTA DE LA  API", Toast.LENGTH_SHORT).show()

                }
            }

            // metodo si api falla

            override fun onFailure(call: Call<ITunesResponsive?>, t: Throwable) {
                Toast.makeText(this@HomeActivity, "ERROR EN EL API", Toast.LENGTH_SHORT).show()

            }
        } )

    }

    // metodo para mostrar el listado de un dato de la api
    private fun mostrarAlbumes(albumes: List<Album>, contenedor: LinearLayout){

        // obligatoeio: evitar elementos duplicados
        contenedor.removeAllViews()

        // ciclo para recorrr las multiples peticiones a un API
        for (album in albumes){

            // usar el LiearLayout con el contenedor
            val vistaAlbum = layoutInflater.inflate(
                R.layout.item_album,
                contenedor,
                false

            )
            val imgPortada = vistaAlbum.findViewById<ImageView>(R.id.imgPortadaAlbum)
            val tvNombreAlbum = vistaAlbum.findViewById<TextView>(R.id.tvNombreAlbum)
            val tvNombrertista = vistaAlbum.findViewById<TextView>(R.id.tvNombreArtista)

            // Asignar nombres obtenids del API
            tvNombreAlbum.text = album.collectionName ?: "ALBUM SIN NOMBRE "
            tvNombrertista.text = album.artistName ?: "ARISTA MUY UNDERGROUND QUE NI YO CONOZCO"

            // Mostrar la imagen en la tarjeta
            Glide.with(this@HomeActivity)
                .load(album.obtenerImagnGrnde())
                .into(imgPortada)



            vistaAlbum.setOnClickListener {

                // necesitamos obtemer el ID
                val idAlbum = album.collectionId

                if (idAlbum != null){
                    val intent = Intent(this@HomeActivity, DetailsActivity::class.java)

                    //enviar datos
                    intent.putExtra("ID_ALBUM", idAlbum)
                    intent.putExtra("NOMBRE_ALBUM", album.collectionName)
                    intent.putExtra("ARTISTA_ALBUM", album.artistName)
                    intent.putExtra("GENERO_ALBUM", album.primaryGenreName)
                    intent.putExtra("CANCIONES_ALBUM", album.trackCount)
                    intent.putExtra("IMAGEN_ALBUM", album.obtenerImagnGrnde())

                    startActivity(intent)


                }
            }

            // ASIGNA LA VUSTA DEL CONTENOR A LAS TARJETAS DE LOS ALBUMES
            contenedor.addView(vistaAlbum)


        }


    }



}