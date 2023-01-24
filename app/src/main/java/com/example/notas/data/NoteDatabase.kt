package com.example.notas.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notas.data.models.NoteEntity

//Se crea la base de datos, NO SE INSTANCIA de eso se encarga Room
@Database(//Se coloca la etiqueta "@Database" y dentro de esta se coloca las entidades que conectaran con la base de datos
    entities = [NoteEntity::class],//Se agrega el parametro "entities". La cual es un listado de las entidades que se quieren crear
    version = 1,//Se coloca la version de la base de datos
    exportSchema = false//No se quiere generar un esquema de exportación, así que se coloca en falso
)
abstract class NoteDatabase: RoomDatabase() {//La clase sera abstracta y heredara de "RoomDatabase()"
    abstract fun noteDao(): NoteDao//Tiene una funcion la cual regresara un valor de tipo "NoteDao", esta es donde se hicieron las Querys
//  abstract val dao: UserDao//Se crea una variable de tipo "NoteDao", la cual se encargara de traer o "contener" el Dao
}