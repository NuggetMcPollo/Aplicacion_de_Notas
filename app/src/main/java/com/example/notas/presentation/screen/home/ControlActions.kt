package com.example.notas.presentation.screen.home

import androidx.compose.ui.graphics.Color
import com.example.notas.ui.theme.HighPriority
import com.example.notas.ui.theme.LowPriority
import com.example.notas.ui.theme.MediumPriority
import com.example.notas.ui.theme.NonePriority

//Se crea una Enum class, la cual servira para tener un control de las accion que se realizan de las notas
enum class NoteAction {
    NOTHING,//Nada

    ADD,//Agregar
    UPDATE,//Actualizar
    DELETE,//Eliminar
    UNDO,//Deshacer
    DELETE_ALL//Eliminar tod0
}

//Se crea una Enum class, la cual servira para tener un control de las prioridades de cada nota
enum class Priority (val color: Color){//Cada prioridad tendra un color para identificarlas
    Alta(HighPriority),//Prioridad Alta
    Media(MediumPriority),//Prioridad Media
    Baja(LowPriority),//Prioridad Baja
    Ninguna(NonePriority)//Ninguna Prioridad
}

//Se crea una Enum class, la cual servira para tener un control de cuando esta el SearchBar y las acciones dentro de el
enum class SearchTopBarAction {
    OPENED,//Abierto
    CLOSED,//Cerrado
    TRIGGERED//Disparado
}

//-------------------------------------------------------------------------- Parte 3 --------------------------------------------------------------------------//

//Se crea una extencion de la funcion "Action", NO NULABLE la cual convertira a string las acciones "ADD", "UPDATE"...
fun String?.toAction(): NoteAction {//Debera regresar un valor de tipo "Action"
    return if (this.isNullOrEmpty()){//regresa el resultado, preguntando... Si el valor recivido es igual a nulo o vacio "isNullOrEmpty"
        NoteAction.NOTHING//Entonces regresa
    } else {//Si no manda
        NoteAction.valueOf(this)//Con este this, se hace referencia al "String?" de arriba. El valor recibido
    }
}