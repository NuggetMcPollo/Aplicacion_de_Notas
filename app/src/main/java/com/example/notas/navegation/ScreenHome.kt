package com.example.notas.navegation

import androidx.navigation.NavHostController
import com.example.notas.presentation.screen.home.NoteAction

class ScreensHome (navController: NavHostController) {

    //Se crean dos variables, cada variable representara un pantalla composable
    //La "List-Composable" tendra un argumento el cual sera una "Action", la cual representara la accion que se le quiere hacer a la tarea
    //La "Task-Composable" tendra un argumento el cual sera una "Int", la cual representara el ID de la tarea seleccionada

    val details: (Int) -> Unit = { noteId ->//Se crea una variable la cual tomara el "Id", la cual pasara desde la "list-composable" y esta regresara una unidad...
        navController.navigate(route = "details_screen/$noteId")//...Despues usara ese mismo ID para navegar a "task-composable", para eso, el ID se pasara como un argumento a esta misma composable
    }

    val note: (NoteAction) -> Unit = { actionNote ->//Se crea una variable la cual tomara una "Action", la cual pasara desde el "task-composable" y esta regresara una unidad...
        navController.navigate(route = "home_screen/${actionNote}") {//...Despues usara esa misma accion para navegar a "list-composable", para eso, la accion se pasara como un argumento a esta misma composable
            popUpTo(route = Screen.Notes.route) {
                inclusive = true//Esto signifca que cuando se quiere navegar desde nuestra "Task-composable" a "list-composable" aparecera la pantalla pero eliminara nuestro componente que se selecciono de la pila
            }
        }
    }
}