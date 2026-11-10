
package com.example.music_app

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Clase Databasehelper que hereda de SQLiteOpenHelper
class Databasehelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    companion object {
        // Información de la base de datos
        private const val DATABASE_NAME = "music.db"
        private const val DATABASE_VERSION = 3

        // Información de la tabla usuarios
        private const val TABLE_USUARIOS = "usuarios"
        private const val COL_ID = "id"
        private const val COL_NOMBRE = "nombre"
        private const val COL_CORREO = "correo"
        private const val COL_CONTRASENA = "contraseña"

        // Información de la tabla favoritos
        private const val TABLE_FAVORITOS = "favoritos"
        private const val COL_ID_FAVORITO = "id"
        private const val COL_ALBUM_ID = "album_id"
        private const val COL_NOMBRE_ALBUM = "nombre_album"
        private const val COL_ARTISTA = "artista"
        private const val COL_GENERO = "genero"
        private const val COL_CANCIONES = "num_canciones"
        private const val COL_FECHA = "fecha"
        private const val COL_IMAGEN = "imagen"
    }

    // Método para crear las tablas de la base de datos
    override fun onCreate(db: SQLiteDatabase?) {

        val createTableUsuarios = """
            CREATE TABLE $TABLE_USUARIOS (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NOMBRE TEXT NOT NULL,
                $COL_CORREO TEXT NOT NULL,
                $COL_CONTRASENA TEXT NOT NULL
            )
        """.trimIndent()

        val createTableFavoritos = """
            CREATE TABLE $TABLE_FAVORITOS (
                $COL_ID_FAVORITO INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_ALBUM_ID INTEGER UNIQUE,
                $COL_NOMBRE_ALBUM TEXT NOT NULL,
                $COL_ARTISTA TEXT NOT NULL,
                $COL_GENERO TEXT,
                $COL_CANCIONES INTEGER,
                $COL_FECHA TEXT,
                $COL_IMAGEN TEXT
            )
        """.trimIndent()

        db?.execSQL(createTableUsuarios)
        db?.execSQL(createTableFavoritos)
    }

    // Método para actualizar la base de datos
    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIOS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_FAVORITOS")
        onCreate(db)
    }

    // Método para registrar un usuario
    fun registrarUsuario(
        nombre: String,
        correo: String,
        password: String
    ): Boolean {

        val db = writableDatabase

        val datos = ContentValues().apply {
            put(COL_NOMBRE, nombre)
            put(COL_CORREO, correo)
            put(COL_CONTRASENA, password)
        }

        val resultado = db.insert(TABLE_USUARIOS, null, datos)

        db.close()

        return resultado != -1L
    }

    // Método para iniciar sesión
    fun iniciarSesion(
        correo: String,
        password: String
    ): Boolean {

        val db = readableDatabase

        val querySql = """
            SELECT * FROM $TABLE_USUARIOS
            WHERE $COL_CORREO = ? AND $COL_CONTRASENA = ?
        """.trimIndent()

        val cursor = db.rawQuery(
            querySql,
            arrayOf(correo, password)
        )

        val usuarioEncontrado = cursor.count > 0

        cursor.close()
        db.close()

        return usuarioEncontrado
    }

    // Método para guardar un álbum en favoritos
    fun guardarAlbumFavoritos(
        albumId: Long,
        nombreAlbum: String,
        artista: String,
        genero: String,
        canciones: Int,
        fecha: String,
        imagen: String
    ): Boolean {

        val db = writableDatabase

        val valores = ContentValues().apply {
            put(COL_ALBUM_ID, albumId)
            put(COL_NOMBRE_ALBUM, nombreAlbum)
            put(COL_ARTISTA, artista)
            put(COL_GENERO, genero)
            put(COL_CANCIONES, canciones)
            put(COL_FECHA, fecha)
            put(COL_IMAGEN, imagen)
        }

        val resultado = db.insertWithOnConflict(
            TABLE_FAVORITOS,
            null,
            valores,
            SQLiteDatabase.CONFLICT_IGNORE
        )

        db.close()

        return resultado != -1L
    }
}
