package com.example.notas.presentation.screen.home.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notas.data.models.NoteEntity
import com.example.notas.presentation.screen.home.HomeState
import com.example.notas.presentation.screen.home.NoteAction
import com.example.notas.presentation.screen.home.Priority
import com.example.notas.presentation.screen.home.shared_component.PriorityDropDown

//Esta funcion representara la pantalla en donde se mostrara la tarea a mostrar, especifica
@Composable
fun DetailsContent(
    detailsViewModel: DetailsViewModel,
    detailsUiState: HomeState//La funcion recibe un valor de tipo "ToDoTask", el cual podra ser nulable
) {
    Column(//Se crea una columna
        modifier = Modifier
            .fillMaxSize()//Se le asigna un tamaño maximo
            /** OJO: el orden de ejecucion de este background, depende del orden en este caso es de ARRIBA hacia ABAJO **/
            /** OJO: el orden de ejecucion de este background, depende del orden en este caso es de ARRIBA hacia ABAJO **/
            .background(MaterialTheme.colors.background)//Se asigna un fondo
            .padding(all = 12.dp)//Se asigna un padding para que no este pegado a la pantalla
    ) {
        OutlinedTextField(//Se asigna un "OutlinedTextField"
            modifier = Modifier.fillMaxWidth(),//Se asigna un ancho completo
            value = detailsUiState.title,//Se asigna un titulo a la tarea
            onValueChange = {
                detailsViewModel.updateTitle(it)
            },//Se asigna la variable que va a estar cambiando para el titulo
            label = {//Se asigna un texto indicativo, este pasara arriba una vez que se seleccione
                Text(
                    text = "Titulo"
                )
            },
            textStyle = MaterialTheme.typography.body1,//Se asigna el estilo de la letra
            singleLine = true//Se asigna que el titulo solo sea de una sola linea
        )
        Divider(
            modifier = Modifier.height(8.dp),//Se asigna una division
            color = MaterialTheme.colors.background//Se asigna el color para esa division
        )
        PriorityDropDown(
            priority = detailsUiState.priority,//Se asigna la variable que contiene la prioridad
            onPrioritySelected = {//Se asigna la variable que va a estar cambiando para la prioridad
                detailsViewModel.onChangePriority(it)
            }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = detailsUiState.content,//Se asigna la descripcion de la tarea
            onValueChange = {//Se asigna la variable que va a estar cambiando para la descripcion
                detailsViewModel.onChangeContent(it)
            },
            label = {//Se asigna un texto indicativo, este pasara arriba una vez que se seleccione
                Text(
                    text = "Descripcion"
                )
            },
            textStyle = MaterialTheme.typography.body1//Se asigna el estilo de la letra
        )
    }
}