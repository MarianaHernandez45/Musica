package com.example.music_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var etCorreo : EditText
    private lateinit var  etPassword: EditText
    private lateinit var  btnIngresar: Button

    private lateinit var  dbHelper: Databasehelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val tvCrearCuenta = findViewById<TextView>(R.id.tvCrearCuenta)

        etCorreo = findViewById(R.id.etCorreo)
        etPassword = findViewById(R.id.etPassword)
        btnIngresar = findViewById(R.id.btnIngresar)

        dbHelper = Databasehelper(this)

        // Evento pra iniciar sesion
        btnIngresar.setOnClickListener {
            // llamara al metodo iniciar sesion
            iniciarSesion()

        }



        tvCrearCuenta.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        }

        }
    // Metdo para iniciar sesion
    // metodo privado
    private fun iniciarSesion(){
        val correo = etCorreo.text.toString().trim()
        val pass = etPassword.text.toString().trim()

        if(correo.isEmpty() || pass.isEmpty()){
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }
        val resultado = dbHelper.iniciarSesion(correo, pass)
        
        // validar si el iniio de sesion fue correcto 
        if(resultado){
            Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{
            // resultado es falso
            Toast.makeText(this, "Credenciales Icorrectas", Toast.LENGTH_SHORT).show()
        }


    }



    }
