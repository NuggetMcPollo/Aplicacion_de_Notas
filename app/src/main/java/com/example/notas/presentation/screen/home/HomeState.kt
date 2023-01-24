package com.example.notas.presentation.screen.home

data class HomeState(
    /** Details Screen **/
    val id: Int = 0,//Se agrega una variable de tipo "Int", para identificar la nota. LA CUAL POR DEFECTO SERA 0
    val title: String = "", //Se agrega una variable de tipo "String", para el titulo de la nota. LA CUAL POR DEFECTO SERA ""
    val content: String = "",//Se agrega una variable de tipo "String", para el contenido de la nota. LA CUAL POR DEFECTO SERA ""
    val priority: Priority = Priority.Baja, //Se crea una variable de tipo "Priority", la cual servira indicar la prioridad de la nota. LA CUAL POR DEFECTO SERA BAJA
    val time: Long = 0,//Se agrega una variable de tipo "Timestamp", para asignar la fecha en que se creo nota. LA CUAL POR DEFECTO SERA "Timestamp.now()"

    /** Notes Screen **/
    val searchTopBarTextField: String = "",//Se crea una variable de tipo "String", la cual servira para guardar el contenido dentro del SearchBar, cuando se trate de buscar algo. LA CUAL POR DEFECTO SERA ""
    val searchTopBarAction: SearchTopBarAction = SearchTopBarAction.CLOSED,

    /** Action **/
    val noteAction: NoteAction = NoteAction.NOTHING
)