package com.example.notas.presentation.screen.home.details

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.DialogProperties
import com.example.notas.data.models.NoteEntity
import com.example.notas.presentation.screen.home.NoteAction
import com.example.notas.ui.theme.BackgroundFieldExisiting
import com.example.notas.ui.theme.BackgroundScreen
import com.example.notas.ui.theme.Orange

//Esta funcion tendra dos diferentes bar
@Composable
fun DetailsTopBar(
    selectedNote: NoteEntity?,//La funcion recibe un valor de tipo "ToDoTask", el cual podra ser nulable, devido a que no se recibe ninguna tarea nueva
    navigateToListScreen: (NoteAction) -> Unit
){
    if (selectedNote == null){//Se pregunta si la variable "selectedTask", contiene un valor nulo. Quiere decir que esta vacia y se a precionado el boton flotante y se llama a la funcion...
        NewNoteTopBar(//"NewTaskAppBar", la cual muestra la tarea existente
            navigateToListScreen = navigateToListScreen
        )
    }else{//Si no quiere decir que si tiene un valor y ese es la tarea seleccionada y se llama a la funcion...
        ExistingNoteTopBar(//"ExistingTaskAppBar", la cual muestra la tarea existente
            selectedNote = selectedNote,
            navigateToListScreen = navigateToListScreen
        )
    }
}

//Esta funcion creara un nuevo bar, en donde se creara una tarea. Etsa funcion se mostrara cuando se presione el boton flotante
@Composable
fun NewNoteTopBar(
    navigateToListScreen: (NoteAction) -> Unit
){
    TopAppBar(//Se llama a la funcion que pondra el TopBar
        navigationIcon = {//Y se coloca un icono de navegacion
            BackAction(//Se llama a la funcion donde se diseño el icono de la accion
                onBackClicked = navigateToListScreen//Se pasa el navegador donde se pasara la accion realizada
            )
        },
        title = {
            Text(
                text = "Agregar Nota",//Se agrega el titulo de bar
                color = Color.White//Se asigna el color
            )
        },
        backgroundColor = Orange,//Se asigna el background
        actions = {//Se asigna al parametro de "actions", las posibles acciones dentro del topbar
            AddAction(//Se llama a la funcion donde se diseño el icono de la accion
                onAddClicked = navigateToListScreen//Se pasa el navegador donde se pasara la accion realizada
            )
        }

    )
}

//Se crea una funcion la cual servira para colocar la accion de volver
@Composable
fun BackAction(
    onBackClicked: (NoteAction) -> Unit//Recibe un lambda, con la cual sera de tipo "Action" y esta devolver una unidad, que en este caso. Cuando se presione el boton de atras se querra pasar una accion
){
    IconButton(onClick = { onBackClicked (NoteAction.NOTHING) }) {//Se coloca un "IconButton" y cuando se presione se pasara una accion la cual sera "NO_ACTION", porque cuando se presiona no se hara nada y solo volvera al screen previo
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back Arrow",
            tint = Color.White
        )
    }
}

@Composable
fun AddAction(
    onAddClicked: (NoteAction) -> Unit//Recibe un lambda, con la cual sera de tipo "Action" y esta devolver una unidad, que en este caso. Cuando se presione el boton de agregar se querra pasar una accion
){
    IconButton(onClick = { onAddClicked (NoteAction.ADD) }) {//Se coloca un "IconButton" y cuando se presione se pasara una accion la cual sera "ADD", porque cuando se presiona + se agregara una tarea nueva
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = "Add Task",
            tint = Color.White
        )
    }
}

@Composable
fun ExistingNoteTopBar(
    selectedNote: NoteEntity,//La funcion recibira un parametro de tipo "ToDoTask" y esta servira para mostrar y alterar los atributos de la tarea seleccionada
    navigateToListScreen: (NoteAction) -> Unit
){
    TopAppBar(//Se llama a la funcion que pondra el TopBar
        navigationIcon = {//Y se coloca un icono de navegacion
            CloseAction(onCloseClicked = navigateToListScreen)
        },
        title = {
            Text(
                text = selectedNote.title,//Se agrega el titulo del topbar, el cual se agarrara de la variable que pasa la tarea
                color = Color.White,//Se asigna el color
                maxLines = 1,//Se coloca el parametro de aceptar una linea
                overflow = TextOverflow.Ellipsis//Y en caso de ser mucho texto solo mostrara ...
            )
        },
        backgroundColor = MaterialTheme.colors.BackgroundFieldExisiting,//Se asigna el background
        actions = {//Se asigna al parametro de "actions", las posibles acciones dentro del topbar
            ExistingNoteTopBarActions(//Se llama a la funcion la cual observara la accion a realizar
                selectedNote = selectedNote,//Se manda la tarea seleccionada
                navigateToListScreen = navigateToListScreen//Se manda el navegador
            )
        }
    )
}

//Se crea una funcion la cual servira para cerrar la accion de volver
@Composable
fun CloseAction(
    onCloseClicked: (NoteAction) -> Unit//Recibe un lambda, con la cual sera de tipo "Action" y esta devolver una unidad, que en este caso. Cuando se presione el boton de atras se querra pasar una accion
){
    IconButton(onClick = { onCloseClicked (NoteAction.NOTHING) }) {//Se coloca un "IconButton" y cuando se presione se pasara una accion la cual sera "NO_ACTION", porque cuando se presiona no se hara nada y solo volvera al screen previo
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = null,
            tint = Color.White
        )
    }
}

//Se crea el componente que tendra las opciones del topbar de la nota ya existente
@Composable
fun ExistingNoteTopBarActions(
    selectedNote: NoteEntity,//La funcion recibira un parametro de tipo "ToDoTask" y esta servira para mostrar y alterar los atributos de la tarea seleccionada
    navigateToListScreen: (NoteAction) -> Unit
){
    var showDialog by rememberSaveable {
        mutableStateOf(false)
    }//Se crea una variable para recordar el estado de la ventana de dialogo

    if (showDialog){
        AlertDialogDeleteNote(
            title = stringResource(//Se manda el titulo
                id = com.example.notas.R.string.Delete_Note,
                selectedNote.title//La funcion recibira un valor de tipo "String", referente a la tarea que se va a borrar
            ),
            message = stringResource(//Se manda el titulo
                id = com.example.notas.R.string.Delete_Note_confirmation,
                selectedNote.title//La funcion recibira un valor de tipo "String", referente a la tarea que se va a borrar
            ),
            onDismiss = { showDialog = false } ,//La funcion recibira un Unit, y esta servira para saber cuando abrir o cerrar el dialogo
            onNoClicked = { showDialog = false },//La funcion recibira un Unit, para capturar el click del "No" y el dialogo se cerrara
            onYesClicked = {
                navigateToListScreen(NoteAction.DELETE)
                showDialog = false
            },//La funcion recibira un Unit, para capturar el click del "Si", el dialogo se cerrara, y se eliminaran las notas
        )
    }
    //Se quiere disparar el dialogo, cuando se precione la accion de eliminar
    DeleteAction(onDeleteClicked = {
        //Presionando el boton de eliminar
        showDialog = true//Se cambiara el estado del dialogo a true
    })
    UpdateAction(onUpdateClicked = navigateToListScreen)
}

//Se crea una funcion la cual servira para cerrar la accion de volver
@Composable
fun DeleteAction(
    onDeleteClicked: () -> Unit//Recibe un lambda, con la cual sera de tipo "Action" y esta devolver una unidad, que en este caso. Cuando se presione el boton de atras se querra pasar una accion
){
    IconButton(onClick = { onDeleteClicked () }) {//Se coloca un "IconButton" y cuando se presione se pasara una accion la cual sera "NO_ACTION", porque cuando se presiona no se hara nada y solo volvera al screen previo
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = "Delete Task",
            tint = Color.White
        )
    }
}

//Se crea una funcion la cual servira para cerrar la accion de volver
@Composable
fun UpdateAction(
    onUpdateClicked: (NoteAction) -> Unit//Recibe un lambda, con la cual sera de tipo "Action" y esta devolver una unidad, que en este caso. Cuando se presione el boton de atras se querra pasar una accion
){
    IconButton(onClick = { onUpdateClicked (NoteAction.UPDATE) }) {//Se coloca un "IconButton" y cuando se presione se pasara una accion la cual sera "NO_ACTION", porque cuando se presiona no se hara nada y solo volvera al screen previo
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = "Update Task",
            tint = Color.White
        )
    }
}