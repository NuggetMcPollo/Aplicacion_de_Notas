package com.example.notas.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notas.presentation.screen.home.Priority
import com.example.notas.util.Constants.Note_Table

@Entity(tableName = Note_Table)//Se le agrega la anotacion para indicarle a la libreria Room que se creara una tabla en la base de datos, cada dato dentro de esta data class, sera una una columna dentro de la tabla
data class NoteEntity (
    @PrimaryKey(autoGenerate = true)//Se coloca una generacion automatica de la variable "id", de modo que cuando se instance una nueva entidad "NoteEntity", no se pase este parametro y se haga automaticamente
    val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,//Tendra un titulo
    @ColumnInfo(name = "content")val content: String,//Tendra una descripcion
    @ColumnInfo(name = "priority")val priority: Priority,//Tendra una prioridad. [Esta se declaro, anteriormente en una enum class]
    @ColumnInfo(name = "time")val time: Long//Tendra un hora
//    @ColumnInfo(name = "time")val time: Timestamp = Timestamp.now()//Tendra un hora
)