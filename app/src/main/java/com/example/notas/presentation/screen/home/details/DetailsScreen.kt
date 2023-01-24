package com.example.notas.presentation.screen.home.details

import androidx.activity.compose.BackHandler
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.notas.data.models.NoteEntity
import com.example.notas.presentation.screen.home.NoteAction

@Composable
fun DetailsScreen(
    selectedNote: NoteEntity?,//La funcion recibe un valor de tipo "ToDoTask", el cual podra ser nulable
    detailsViewModel: DetailsViewModel,
    navigateToNoteScreen: (NoteAction) -> Unit
){
    val detailsUiState = detailsViewModel.state//Se crea una variable que trera OJO: LOS ESTADOS DEL VIEWMODE "DATACLASS" a la UI
    val context = LocalContext.current//De este modo se puede buscar el contexto desde la funcion composable y llamarla desde aqui

    BackHandler {//Esta funcion cuando es precionado el boton de atras, dentro del sistema en las direcciones
        navigateToNoteScreen(NoteAction.NOTHING)//Se manda a "navigateToListScreen" la accion "Action.NO_ACTION", de este modo no se hara nada
    }

    Scaffold(
        topBar = {
            DetailsTopBar(//Se llama al topbar que seleccionara uno de los dos topbar, dependiendo la situacion
                selectedNote = selectedNote,//Se manda la tarea seleccionada
                //navigateToListScreen = navigateToListScreen
                navigateToListScreen = { action ->//Se crea una lambda, la cual dara una accion
                    if (action == NoteAction.NOTHING){//Se hace dentro de un bloque if, si la accion, es igual a una "Action.NO_ACTION". Eso significa que solo se ha hecho clic en este botón de cerrar o en esta flecha hacia atrás
                        navigateToNoteScreen(action)//Solo navegamos hacia atras
                    }else{//Si no quiere decir, que la accion. Si tiene un motivo y se necesita validar los datos
                        if (
                            detailsViewModel.validateFields(
                                title = detailsUiState.title,
                                content = detailsUiState.content
                            )
                        ){//De ser true, quiere decir que si hay contenido adentro
                            navigateToNoteScreen(action)//Y solo se quiere navegar de regreso a la pantalla con esa acción especificada
                        }else{//Si no quiere decir que no hay contenido contenido en uno de los dos campos
                            detailsViewModel.displayToast(context = context)
                        }
                    }
                }
            )
        },
        content = {
            DetailsContent(
                detailsViewModel = detailsViewModel,
                detailsUiState = detailsUiState
            )
        }
    )
}