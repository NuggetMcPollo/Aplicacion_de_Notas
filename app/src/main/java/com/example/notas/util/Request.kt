package com.example.notas.util

//El principal motivo de este sealed es mostrar los estados, al momento de cargar los datos de la base de datos
sealed class Request<out T> {
    object Static : Request<Nothing>()//Se crea el objeto "Idle", el cual hereda de "RequestState" y sera de tipo <Nothing>, eso es porque no vamos a tener ningún dato
    object Loading : Request<Nothing>()//Se crea el objeto "Loading", el cual hereda de "RequestState" y sera de tipo <Nothing>, eso es porque no vamos a tener ningún dato
    data class Success<T>(val data: T) : Request<T>()//Se crea un data class llamada "Success" la cual tiene un dato de tipo T, el cual hereda de "RequestState" y sera de tipo <T>, el cual sera el valor que recibio
    data class Error(val error: Throwable) : Request<Nothing>()//Se crea un data class llamada "Error" la cual tiene un dato de tipo Throwable, y eso es porque obtendra un error y, el cual hereda de "RequestState"
// y sera de tipo <Nothing>, eso es porque no vamos a tener ningún dato
}