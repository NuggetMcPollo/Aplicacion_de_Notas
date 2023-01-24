package com.example.notas.presentation.screen.home.notes

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.example.notas.presentation.screen.home.HomeStateFlow
import com.example.notas.presentation.screen.home.NoteAction
import com.example.notas.presentation.screen.sign.SignViewModel
import com.example.notas.ui.theme.Orange
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun NotesScreen(
    signViewModel: SignViewModel,
    notesViewModel: NotesViewModel,//La funcion recibira el viewmodel, para poder tener acceso a las funciones dentro a la vista y asi no pida el parametro
    navigateToNotesScreen: (noteId: Int) -> Unit,//Tomara un parametro el cual es el lambda del "TaskScreen", la cual servira para navegar a la pantalla de tareas [con el ID de cada tarea].
    noteAction: NoteAction,//La funcion recibira un paramtero de tipo "Action"
    navToLoginPage: () -> Unit//La funcion recibira una "unidad" para navegar a la pagina del "login"
){
    val notesUiState = notesViewModel.state//Se crea una variable que trera OJO: LOS ESTADOS DEL VIEWMODE "DATACLASS" a la UI

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    /* Se coloca un "LaunchedEffect" y cuando la accion cambie, esta se recompondra, la cual sucedera solo...
       --Se pase la accion desde el DetailsScreen
       --Cuando se precione el boton flotante
     */
    LaunchedEffect(key1 = noteAction){
        notesViewModel.handleDatabaseActions(action = noteAction)//A la funcion "handleDatabaseActions" y se le pasara la accion y solo despues se dispara este launched
    }

    //Cuando la funcion principal, se dispare se llamara a esta funcion. Pero dentro de esta funcion solo hay una linea que se dispara de golpe la otra ya tiene un launchedeffect. La cual, es la que tiene el parametro "handleDatabaseAction"
    DisplaySnackBar(
        notesViewModel = notesViewModel,
        scaffoldState = scaffoldState,
        onComplete =  {
            notesViewModel.onChangeNoteAction(it)
        },
        onUndoClicked = {
            notesViewModel.onChangeNoteAction(it)
        },
        noteTitle = notesUiState.title,
        noteAction = noteAction
    )

    Scaffold(//Se llama al componente "Scaffold" y lo empezamos a rellenar
        scaffoldState = scaffoldState,//Sin este parametro no se podria, desplegar el "DisplaySnackBar"
        topBar = {//Se llama al componente "topBar", el cual se ubicara en la parte de arriba del "Scaffold"
            NoteTopBar(//Se llama a la funcion "ListAppBar", la cual tiene los dos tipos topbar desarrollado
                onClickDrawer = {//Se llama al atributo "onCloseDrawer" declarado dentro de la funcion "MyTopAppBar", pero para que funcione...
                    coroutineScope.launch {//Esto necesita una corrutina, por eso mismo se pasa la variable que se declaro
                        scaffoldState.drawerState.open()//Se llama la variable "scaffoldState", asignandole el "drawerState" y se le pasa el ".open()" diciendole que pueda abrise y cerrarse "
                    }
                },
                notesViewModel = notesViewModel,
                notesUiState = notesUiState,
            )
        },
        content = {
            //Se pasaran las tareas buscadas y el estado del disparador de buscar, para despues dentro del composable, decidir si se quieren mostrar todas las tareas o la busqueda de las tareas
            NotesContent(
                notesUiState = notesUiState,
                notesViewModel = notesViewModel,
                onSwipeToDelete = { note, noteAction ->
                    notesViewModel.noteBlankOrUpdateNotes(selectNote = note)
                    notesViewModel.onChangeNoteAction(noteAction = noteAction)
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                },
                navigateToTaskScreen = navigateToNotesScreen
            )
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToNotesScreen)
        },
        drawerContent = {
            NotesDrawer(
                signViewModel = signViewModel,
                navToLoginPage = navToLoginPage
            ){
                coroutineScope.launch{//Esto necesita una corrutina, por eso mismo se pasa la variable que se declaro
                    scaffoldState.drawerState.close()//Se llama la variable "scaffoldState", asignandole el "drawerState" y se le pasa el atributo que necesita "String"
                }
            }
        },
        drawerGesturesEnabled = false//Por defecto es true, la cual su funcion es aparecer el menu si arrastra con el menu
    )

}

//Esta funcion mostrar la accion realizada de la tarea y mostrara un mensaje de confirmacion dependiendo la accion
@Composable
fun DisplaySnackBar(
    notesViewModel: NotesViewModel,//La funcion recibira el viewmodel, para poder tener acceso a las funciones dentro a la vista y asi no pida el parametro
    scaffoldState: ScaffoldState,//La funcion recibira un "scaffoldState", la cual servira para desplegar la funcion
    onComplete: (NoteAction) -> Unit,//
    onUndoClicked: (NoteAction) -> Unit,
    noteTitle: String,//Recibira un titulo, correspondiente a la tarea
    noteAction: NoteAction//Recibira un valor de tipo "Action"
){
    val scope = rememberCoroutineScope()//Se crea una nueva variable la cual almacenara un "rememberCoroutineScope", porque se va a lanzar una coroutine dentro de la funcion

    LaunchedEffect(key1 = noteAction){//Se coloca la logica dentro de un "LaunchedEffect" pues una vez que se cumpla la condicion se lanzara y eso es cuando, reciba una accion la funcion
        if (noteAction != NoteAction.NOTHING){//Se revisa si la accion es diferente de una "Action.NO_ACTION", eso querra decir que se realizo otra cosa
            scope.launch {//Se lanza tod0 dentro de una "scope.launch" puesto que estara sucediendo en momentos asincronos
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(//No te preocupes si no se usa la variable
                    message = notesViewModel.setMessage(noteAction = noteAction, noteTitle = noteTitle),
                    actionLabel = notesViewModel.setActionLabel(noteAction = noteAction),
                )
                notesViewModel.undoDeletedTask(
                    noteAction = noteAction,
                    snackBarState = snackBarResult,
                    onUndoClicked = onUndoClicked
                )
            }
            onComplete(NoteAction.NOTHING)//Cuando se finalize el coroutine scope, se manda la lambda. Junto con la Accion
        }
    }
}

@Composable
fun ListFab(
    onFabClicked: (taskId:Int) -> Unit//La funcion recibira el parametro de navegar a la pantalla de tareas
){
    FloatingActionButton(
        onClick = {
            onFabClicked(-1)
        },
        backgroundColor = Orange
    ){
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add Button",
            tint = Color.White
        )
    }
}