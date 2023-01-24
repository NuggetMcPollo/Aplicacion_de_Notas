package com.example.notas.presentation.screen.home.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notas.data.models.NoteEntity
import com.example.notas.presentation.screen.home.*
import com.example.notas.ui.theme.ColorTextTheme
import com.example.notas.ui.theme.ItemNoteBackground
import com.example.notas.ui.theme.Orange
import com.example.notas.util.Request
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalAnimationApi//Se utiliza para el "AnimatedVisibility"
@ExperimentalMaterialApi
@Composable
/*La funcion mostrara un listado de notas, dependiendo las acciones realizadas dentro de la screen ya sea:
    -- Mostrar TODAS las notas
    -- Mostrar un listado de las notas buscadas,
    -- Mostrar un listado de las notas dependiendo su prioridad
 */
fun NotesContent(
    notesViewModel: NotesViewModel,//La funcion recibira un valor de tipo "NotesViewModel", con el cual se traeran los datos del viewmodel aqui
    notesUiState: HomeState,
    onSwipeToDelete: (NoteEntity, NoteAction) -> Unit,//La funcion recibira dos valores; uno de tipo "NoteAction", correspondiente a la accion realizada de la nota y otro de tipo "NoteEntity" correspondiente a la nota que hizo la accion
    navigateToTaskScreen: (noteId: Int) -> Unit//La funcion recibira un valor de tipo "Int", correspondiente al ID de la nota
){
    val allNotes by notesViewModel.allNotes.collectAsState()
    val searchNotes by notesViewModel.searchedNotes.collectAsState()
    val sortState by notesViewModel.sortState.collectAsState()
    val lowPriorityNotes by notesViewModel.lowPriorityNotes.collectAsState()
    val highPriorityNotes by notesViewModel.highPriorityNotes.collectAsState()

    //if (sortState is Request.Success){//Se hace un bloque if y se pregunta, si... Si, "sortState" es de tipo "RequestState.Success" de ser verdadero corre las opciones
    if (sortState is Request.Success){//Se hace un bloque if y se pregunta, si... Si, "sortState" es de tipo "RequestState.Success" de ser verdadero corre las opciones
        when {//Y se pregunta, cuando
            (sortState as Request.Success<Priority>).data == Priority.Ninguna -> {//Si "sortState" tiene el contenido de "Priority.NONE", despelgaran todas las tareas que hay
                if (allNotes is Request.Success) {//Se pregunta si "allTasks", es de tipo "RequestState.Success" y de ser asi...
                    HandleNotesContent(//Mostrara las tareas
                        notes = (allNotes as Request.Success<List<NoteEntity>>).data,//Se manda la variable "allNotes" accediendo a data, para poder mandarle los "datos" que necesita la Request
                        onSwipeToDelete = onSwipeToDelete,//Se manda la variable "onSwipeToDelete", para que pueda capturar la nota en caso de que se quiera eliminar una
                        navigateToTaskScreen = navigateToTaskScreen//Se manda la variable "navigateToTaskScreen", para que pueda capturar el ID de la nota en caso de que se quiera acceder a una
                    )
                }
            }
            (sortState as Request.Success<Priority>).data == Priority.Baja -> {//Si "sortState" tiene el contenido de "PriorityAction.Alta", se despelgaran las tareas en prioridad "Baja"
                HandleNotesContent(
                    notes = lowPriorityNotes,//Se manda la variable "highPriorityNotes" la cual posee la lista de las notas organizadas en prioridad "Baja"
                    onSwipeToDelete = onSwipeToDelete,//Se manda la variable "onSwipeToDelete", para que pueda capturar la nota en caso de que se quiera eliminar una
                    navigateToTaskScreen = navigateToTaskScreen//Se manda la variable "navigateToTaskScreen", para que pueda capturar el ID de la nota en caso de que se quiera acceder a una
                )
            }
            (sortState as Request.Success<Priority>).data == Priority.Alta -> {//Si "sortState" tiene el contenido de "PriorityAction.Alta", se despelgaran las tareas en prioridad "Alta"
                HandleNotesContent(
                    notes = highPriorityNotes,//Se manda la variable "highPriorityNotes" la cual posee la lista de las notas organizadas en prioridad "Alta"
                    onSwipeToDelete = onSwipeToDelete,//Se manda la variable "onSwipeToDelete", para que pueda capturar la nota en caso de que se quiera eliminar una
                    navigateToTaskScreen = navigateToTaskScreen//Se manda la variable "navigateToTaskScreen", para que pueda capturar el ID de la nota en caso de que se quiera acceder a una
                )
            }
            notesUiState.searchTopBarAction == SearchTopBarAction.TRIGGERED -> {// Si la variable "searchAppBarAction" tiene el valor de "TRIGGERED"
                // [Cuando se presiona el boton de busqueda dentro del TopBar]
                if (searchNotes is Request.Success) {//Se pregunta si "searchNotes", es de tipo "RequestState.Success"
                    HandleNotesContent(//Mostrara las tareas buscadas
                        notes = (searchNotes as Request.Success<List<NoteEntity>>).data,//Se manda la variable "searchNotes" accediendo a data, para poder mandarle los "datos" que necesita la Request
                        onSwipeToDelete = onSwipeToDelete,//Se manda la variable "onSwipeToDelete", para que pueda capturar la nota en caso de que se quiera eliminar una
                        navigateToTaskScreen = navigateToTaskScreen//Se manda la variable "navigateToTaskScreen", para que pueda capturar el ID de la nota en caso de que se quiera acceder a una
                    )
                }
            }
        }
    }
}

@ExperimentalAnimationApi//Se utiliza para el "AnimatedVisibility"
@ExperimentalMaterialApi
@Composable
//Esta funcion mostrara la lista de notas correspondiente
fun HandleNotesContent(
    notes: List<NoteEntity>,//La funcion recibira una lista de tipo "NoteEntity"
    onSwipeToDelete: (NoteEntity, NoteAction) -> Unit,//La funcion recibira variable, la cual poseera dos parametros la "Action" a realizar [Eliminar] y la "Tarea" que la realizo
    navigateToTaskScreen: (noteId: Int) -> Unit//La funcion recibira un valor de tipo "Int", correspondiente a la nota que va a navegar
) {
    if(notes.isEmpty()){//Se pregunta si la variable "notes", la cual posse el listado de notas esta "Vacia"
        EmptyContent()//Se muestra la funcion "EmptyContent"
    } else {//Si no, quiere decir que hay contenido y lo muestra
        DisplayNotes(
            notes = notes,//Se pasa la variable que tiene el listado de Notas
            onSwipeToDelete = onSwipeToDelete,//Se pasa la variable, la cual poseera dos parametros la "Action" a realizar [Eliminar] y la "Tarea" que la realizo
            navigateToNoteScreen = navigateToTaskScreen//Se pasa la variable "navigateToTaskScreen", para que pueda capturar el ID de la nota en caso de que se quiera acceder a una
        )
    }
}



@ExperimentalAnimationApi//Se utiliza para el "AnimatedVisibility"
@ExperimentalMaterialApi
@Composable
//Esta funcion mostrara las tareas que hay
fun DisplayNotes(
    notes: List<NoteEntity>,//La funcion recibira una lista de tipo "NoteEntity"
    onSwipeToDelete: (NoteEntity, NoteAction) -> Unit,//La funcion recibira variable, la cual poseera dos parametros la "Action" a realizar [Eliminar] y la "Tarea" que la realizo
    navigateToNoteScreen: (noteId: Int) -> Unit//La funcion recibira un valor de tipo "Int", correspondiente a la nota que va a navegar
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {//Se llama al metodo "LazyColumn", el cual permitira mostrar las listas de poco en poco
        items(//Se llama al metodo "items" y se le pasa el listado de notas. OJO
            items = notes,//Al parametro "items", se le pasa la variable que posee la lista de las notas
            key = { note -> //Al parametro "key", se le asigna lo que tiene al momento el "Item" de la lista en el apartado "id"
                note.id
            }/** OJO: Básicamente, le metodo "items", permite tener un control de los elementos mostrados dentro de "LazyColumn", para eso se le mandan los datos correspondientes a sus respectivos
            parametros. ¿Que quiere decir esto? El metodo "items", tiene 3 parametros
             -- items: En el cual se le manda la lista a mostrar
             -- key: La cual servira, como el identificador UNICO para cada elemento dentro de la lista. Al especificar que la clave única del item sera el "ID", se puede asegurar que esta clave será única.
            **/
        ) { note ->//Como tal el metodo "items", devuelve items de un listado. Estos pueden ser renombrados como note
            val dismissState = rememberDismissState()//Se crea una variable, la cual permitira recordar el estado inicial de la "posicion" de cada uno de los elementos de la lista
            val dismissDirection = dismissState.dismissDirection//Se crea una variable y se le asigna el contenido de "dismissState" accediendo al apartado de "dismissDirection",
            // lo cual permitira guardar el estado de la direccion del deslizado al momento
            val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)//Se crea una variable que guardar el estado de la "posicion" del item en caso de querer eliminarlo
            if (isDismissed && dismissDirection == DismissDirection.EndToStart) {//Se pregunta, si... y si, son True
                val scope = rememberCoroutineScope()//Se crea una variable para recordar el "rememberCoroutineScope"
                SideEffect {
                    scope.launch {//Se coloca dentro de un launch, porque tod0 corre dentro de corrutinas
                        delay(300)//Se agrega un retraso de 300 correspondiente a la duracion de la animacion de salida, para que una vez que termine la animacion de salida. Se elimine por completo la tarea
                        onSwipeToDelete(note,NoteAction.DELETE)//Se llama a la funcion "onSwipeToDelete" y se manda la accion realizada. Y de pasa la tarea quien la realizo
                    }
                }
            }
            val degreesIcon by animateFloatAsState(//Se crea una variable para animar el icono de eliminar, el cual se le delega a "animateFloatAsState"
                if (dismissState.targetValue == DismissValue.Default)//Se pregunta si "dismissState.targetValue" y "DismissValue.Default", son iguales [Los dos estan en la misma posicion]
                    0f//Se le asigna 0
                else//Si no cumple eso, signica que se esta moviendo un item
                    -45f//Se le asigna -45
            )
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
/** Se quiere animar los componentes del lazy-column, de modo que aparezcan como si bajaran, para esto algo a tener en cuenta es que Compose, trabaja por RECOMPOSICION, esto quiere
 *  decir que tod0 que algo cambie dentro de la UI si no esta dentro del ViewModel se estara lanzando una y otra vez, para eso se crea una variable que solo cambiara una vez y
 *  aguantara la recomposicion.
**/
            var itemAppeared by remember { mutableStateOf(false) }//Se crea una variable para recordar el estado de los items cuando bajen por primera vez en la screen
            LaunchedEffect(key1 = true){//Se indica con un "LaunchedEffect" en true, para que solo se recomponga una vez
                itemAppeared = true//El estado del la variable cambia a true para indicar que ya aparecio
            }
            AnimatedVisibility(
                visible = itemAppeared && !isDismissed,//El parametro de "visible" se le indica cuando debe ser visible la animacion y eso es cuando "itemAppeared" que es, cuando
                //se lanza por primera vez y cuando "!isDismissed" sea difente de su estado declarado originalmente osea, diferente de "DismissDirection.EndToStart"
                enter = expandVertically(//En el paramtero "enter", se especifica la animacion con la entrada, la cual sera patrones de "expandVertically - expancion verticalmente"
                    animationSpec = tween(//En el parametro "animationSpec", la cual indicara
                        durationMillis = 300//Se especifica cuanto durara la animacion
                    )
                ),
                exit = shrinkVertically(//En el paramtero "exit", se especifica la animacion con la saldra, la cual sera patrones de "shrinkVertically - encoger verticalmente"
                    animationSpec = tween(//En el parametro "animationSpec", la cual indicara
                        durationMillis = 300//Se especifica cuanto durara la animacion
                    )
                )
            ) {//Dentro del cuerpo del "AnimatedVisibility" ira el "SwipeToDismiss"
                SwipeToDismiss(//Se llama a la funcion "SwipeToDismiss", la cual permitira borrar un elemento cuando sea deslizado
                    state = dismissState,//Al parametro "state", se le pasa el estado inicial con el cual estara el item
                    directions = setOf(//Al parametro "directions", se le asigna hacia donde deberia desplazarse el item para ser borrado
                        DismissDirection.EndToStart//Hacia "DismissDirection.EndToStart"
                    ),
                    dismissThresholds = {//Al parametro "dismissThresholds", se le asigna la fraccion necesaria del item a deslizar para ya se pueda borrar el item
                        FractionalThreshold(fraction = 0.2f)
                    },
                    background = {//En el parametro "background" se especifica...
                        RedBackground(degreesIcon = degreesIcon)//El background el cual posee el icono de basura
                    },
                    dismissContent = {//Al parametro "dismissContent", se le asigna el formato que tendra cada item de los items que contendra el LazyColumn.
                        // Se podria decir que el "NoteItem" esta dentro del "SwipeToDismiss"
                        NoteItem(//Se llama a la funcion "NoteItem"
                            noteEntity = note,//Se le manda la "nota" a desplegar
                            navigateToNoteScreen = navigateToNoteScreen//Se le pasara el identificador unico para navegar en la pantalla de la nota seleccionada
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun RedBackground(degreesIcon: Float) {//La funcion tomara un parametro de tipo "Float", esto con el fin de hacer una animacion de rotacion sobre el icono de delete
    Box(//Se declara una caja
        modifier = Modifier
            .fillMaxSize()//Se asinga el tamaño completo, PERO ESTE SERA DENTRO DE LA TARJETA
            .background(Color.Red)//Se asigna un background
            .padding(horizontal = 24.dp),//Se asigna un padding para que no este tan pegado a la orilla
        contentAlignment = Alignment.CenterEnd//Se centra el contenido
    ) {
        Icon(//Se llama a un icono
            modifier = Modifier.rotate(degrees = degreesIcon),//Se asigna un modificador de rotacion y se le asigna la variable "degrees", la cual estara cambiando
            imageVector = Icons.Filled.Delete,//Se adigna el icono
            contentDescription = "Delete Icon",//Se asinga la descripcion del icono
            tint = Color.White//Se asigna el color
        )
    }
}

@ExperimentalMaterialApi
@Composable
//Esta funcion representara la nota como item dentro del LazyColumn
fun NoteItem(
    noteEntity: NoteEntity,//Recibira un parametro de tipo "NoteEntity"
    navigateToNoteScreen: (noteId: Int) -> Unit//Recibira un parametro de tipo "Int", el cual servira para nevegar a la tarea que se selecciono
) {
    Card(
        elevation = 8.dp,
        shape = RoundedCornerShape(5.dp),
        border = BorderStroke(
            width = 1.dp,
            color = Orange
        ),
        modifier = Modifier
            .background(Color.Transparent),
    ){
        Surface(
            modifier = Modifier
                .fillMaxWidth(),//Se asigna un ancho completo
            color = MaterialTheme.colors.ItemNoteBackground,//Se le asigna un color
            shape = RectangleShape,//Se define una forma de la superficie así como su sombra. Solo se muestra una sombra si la elevación es mayor que cero.
            elevation = 2.dp,//Se asigna una elevacion
            onClick = {//Se asigna un click, porque cuando se presione un item de una tarea. Se va a quere navegar a el task screen
                navigateToNoteScreen(noteEntity.id)//Se pasa el "id" de la tarea, ese "id" lo debera de tomar desde "noteEntity.id"
            }
        ) {//Se describe como luciera la tarea
            Column(//Se asigna una columna
                modifier = Modifier
                    .padding(all = 12.dp)//Se asigna un padding
                    .fillMaxWidth(),//Se asigna un ancho completo
            ) {
                Row {//Se crea una fila
                    Text(//Tendra un texto
                        modifier = Modifier.weight(8f),//Se indica el peso DENTRO DE LA FILA
                        text = noteEntity.title,//Se asigna el titulo
                        color = MaterialTheme.colors.ColorTextTheme,//Se asigna el color
                        style = MaterialTheme.typography.h5,//Se asigna el estilo de la letra
                        fontWeight = FontWeight.Bold,//Se asinga el grosor
                        maxLines = 1//Se asigna el numero total de lineas
                    )
                    Box(//Abra un caia
                        modifier = Modifier
                            .fillMaxWidth()//Se asinga el ancho complet
                            .weight(1f),//Se asigna el peso DENTRO DE LA FILA
                        contentAlignment = Alignment.TopEnd//Se centre al final de arriba
                    ) {
                        Canvas(//Se asigna un canvas
                            modifier = Modifier
                                .size(16.dp)//Se asigna un tamaño, no es necesario poner ancho "width" y alto "height" porque con size se hace automaticamente
                        ) {
                            drawCircle(
                                color = noteEntity.priority.color//Se asigna el color de la tarea
                            )
                        }
                    }
                }
                Row {//Se crea una fila
                    Text(
                        modifier = Modifier.fillMaxWidth(),//Se asigna un ancho completo
                        text = noteEntity.content,//Se asigna la descripcion
                        color = MaterialTheme.colors.ColorTextTheme,
                        style = MaterialTheme.typography.subtitle1,
                        maxLines = 2,//Se asigna el numero total de lineas
                        overflow = TextOverflow.Ellipsis//Se indica que tenga un desbordamiento el texto, en caso de ser demasiado largo con ... se indica que sigue
                    )
                    Box(//Abra un caia
                        modifier = Modifier
                            .fillMaxWidth()//Se asinga el ancho complet
                            .weight(1f),//Se asigna el peso DENTRO DE LA FILA
                        contentAlignment = Alignment.TopEnd//Se centre al final de arriba
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),//Se asigna un ancho completo
                            text = "NADA",//Se asigna la descripcion
                            color = MaterialTheme.colors.ColorTextTheme,
                            style = MaterialTheme.typography.subtitle1,
                            maxLines = 1,//Se asigna el numero total de lineas
                            overflow = TextOverflow.Ellipsis//Se indica que tenga un desbordamiento el texto, en caso de ser demasiado largo con ... se indica que sigue
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
@Preview
private fun TaskItemPreview() {
    NoteItem(
        noteEntity = NoteEntity(
                id = 0,
                title = "Title",
                content = "Some random text",
                priority = Priority.Media,
                time = 1551
            ),
            navigateToNoteScreen = {}
    )

}

@Composable
@Preview
private fun RedBackgroundPreview() {
    Column(modifier = Modifier.height(80.dp)) {
        RedBackground(degreesIcon = 0f)
    }
}