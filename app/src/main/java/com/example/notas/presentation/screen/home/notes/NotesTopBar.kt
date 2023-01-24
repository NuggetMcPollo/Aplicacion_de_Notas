package com.example.notas.presentation.screen.home.notes

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.notas.presentation.screen.home.HomeState
import com.example.notas.presentation.screen.home.NoteAction
import com.example.notas.presentation.screen.home.Priority
import com.example.notas.presentation.screen.home.SearchTopBarAction
import com.example.notas.ui.theme.Orange
import com.example.notas.presentation.screen.home.shared_component.PriorityItem
import com.example.notas.presentation.screen.sign.SignViewModel
import com.example.notas.ui.theme.BackgroundTextField
import com.example.notas.ui.theme.ColorTextTheme
import com.example.notas.ui.theme.Typography

//La funcion tendra dos tipos de topbar, el primero esta relacionado con las opciones a mostrar para las tereas y el segundo mostrara la busqueda
@Composable
fun NoteTopBar(
    notesViewModel: NotesViewModel,//La funcion recibira un valor de tipo "NotesViewModel", con el cual se traeran los datos del viewmodel aqui
    notesUiState: HomeState,
    onClickDrawer: () -> Unit
){
    when(notesUiState.searchTopBarAction){//Valida la variable recivida y pregunta en que estado esta...
        SearchTopBarAction.CLOSED ->{//Se pregunta "SearchAppBarAction", esta en estado "CLOSED"
            TopBarDefect(//Llama a la funcion "DefaultListBar"
                onNavigationDrawer={
                    onClickDrawer ()
                },
                onSearchClicked = {
                    notesViewModel.onChangeSearchTopBarAction(searchTopBarAction = SearchTopBarAction.OPENED)
                },
                onSortClicked = {
                    notesViewModel.persistSortState(it)
                },
                onDeleteAll = {
                    notesViewModel.onChangeNoteAction(noteAction = NoteAction.DELETE_ALL)
                }
            )
        }
        else -> {//Si no, quiere decir que esta en otro caso [OPENED o TRIGGERED]
            SearchAppBar(//Llama a la funcion "SearchAppBar"
                notesViewModel = notesViewModel,//La funcion recibira un valor de tipo "NotesViewModel", con el cual se traeran los datos del viewmodel aqui
                notesUiState = notesUiState,
                onCloseClicked = {
                    notesViewModel.onChangeSearchTopBarAction(searchTopBarAction = SearchTopBarAction.CLOSED)
                    notesViewModel.onChangeSearchTopBarTextField("")
                },
                onSearchClicked = {
                    notesViewModel.searchNotes(searchNotes = it)
                }
            )
        }
    }
}

/** Top Bar: Defecto **/
//Se crea la funcion que tendra el TopBar por DEFECTO
@Composable
fun TopBarDefect(
    onNavigationDrawer: () -> Unit,//La funcion recibe un parametro de tipo lambda, la cual servira para captar el click del Navigation Drawer
    onSearchClicked: () -> Unit,//Recibe un parametro de tipo lambda, la cual servira para captar el click de la busqueda
    onSortClicked: (Priority) -> Unit,//Recibe un parametro de tipo lambda, la cual servira para captar el click del ordenamiento
    onDeleteAll: () -> Unit//Recibe un parametro de tipo lambda, la cual servira para captar el click de eliminar todas las notas
){
    TopAppBar( //Se llama al componente "TopAppBar"
        title = {//Se le asigna un titulo
            Text(
                text = "Notas",//Se le asigna el texto
                color = Color.White//Se le asigna el color del texto, dependiendo el tema del celular
            )
        },
        navigationIcon = {//Con el atributo de "navigationIcon", se coloca el icono por defecto en la parte sup-izq, bien colocado
            IconButton(
                onClick = onNavigationDrawer
            ) {
                Image(
                    painter = painterResource(id = com.example.notas.R.drawable.nagatoro),
                    contentDescription = "Diosa nagatoro",//Se coloca una descripcion de lo que trata la imagen
                    modifier = Modifier//Se le asigna un modificador
                        .padding(all = 5.dp)
                        .clip(CircleShape)//.clip(RoundedCornerShape(25f)) se le coloca una circunferencia
                        .border(
                            1.dp,//Con un borde
                            Color.White,//Un color
                            CircleShape//Se le asigna a que se le va asignar el borde
                        )
                )
            }
        },
        backgroundColor = Orange,//Se le asigna un background al topbar, dependiendo el tema del celular
        //contentColor = Color.White,//Se asigna un color para tod0 el contenido que este dentro del "TopAppBar", en este caso el texto
        elevation = 4.dp,//Se agrega una elevacion para el efecto de la sombra
        actions = {//Se le asigna una accion, en la cual se tendran las opciones dentro del topbar
            ListAppBarActions(//Se llama a la funcion "ListAppBarActions" y se le manda los lambda que captaran los click
                onSearchClicked = onSearchClicked,//Se llama al parametro "onSearchClicked" y se le manda el lambda para buscar
                onSortClicked = onSortClicked,//Se llama al parametro "onSortClicked" y se le manda el lambda para ordenar
                onDeleteAllConfirmed = onDeleteAll//Se llama al parametro "onDeleteClicked" y se le manda el lambda para eliminar
            )
        },
    )
}

//Se crea el componente que tendra las opciones del topbar
@Composable
fun ListAppBarActions(
    onSearchClicked: () -> Unit,//Recibe un parametro de tipo lambda, la cual servira para captar el click de la busqueda
    onSortClicked: (Priority) -> Unit,//Recibe un parametro de tipo lambda, la cual servira para captar el click del ordenamiento
    onDeleteAllConfirmed: () -> Unit//Recibe un parametro de tipo lambda, la cual servira para captar el click de eliminar
){
    var showDialog by rememberSaveable { mutableStateOf(false) }//Se crea una variable para recordar el estado de la ventana de dialogo

    if (showDialog){
        AlertDialogDeleteAll(
            onDismiss = { showDialog = false } ,//La funcion recibira un Unit, y esta servira para saber cuando abrir o cerrar el dialogo
            onNoClicked = { showDialog = false },//La funcion recibira un Unit, para capturar el click del "No" y el dialogo se cerrara
            onYesClicked = {
                onDeleteAllConfirmed ()
                showDialog = false
            },//La funcion recibira un Unit, para capturar el click del "Si", el dialogo se cerrara, y se eliminaran las notas
            properties = DialogProperties(//La funcion tiene valores predeterminador pero aqui se alteran para asegurarse de que escogera una opcion
                dismissOnBackPress = false,//Se coloca en falso, si se presiona atras por parte del dispositivo; lo que hara que no se salga el anuncio
                dismissOnClickOutside = false//Se coloca en falso, si se presiona afuera del dialogo; lo que hara que no se salga el anuncio
            ),
        )
    }
    SearchAction(onSearchClicked = onSearchClicked)//Se llama a la funcion que tiene la opcion de "BUSCAR"
    SortAction(onSortClicked = onSortClicked)//Se llama a la funcion cuando se preciona que tiene la opcion de "ORDENAR"
    DeleteAllAction(
        onDeleteAllConfirmed = { //Se llama a la funcion cuando se preciona que tiene la opcion de "ELIMINAR"
            showDialog = true//Pero entonces se abrira el dialogo
        }
    )
}

//Se crea un componente que contiene la opcion de buscar
@Composable
fun SearchAction(
    onSearchClicked: () -> Unit//Recibe un parametro de tipo lambda, la cual servira para captar el click de las opcion BUSCAR
){
    IconButton(//Se asigna un "IconButton"
        onClick = { //Cada que se de click dentro de este
            onSearchClicked()//Se le asinga la lambda, que captara el click de la opcion
        }
    ){
        Icon(//Se asigna el "icono"
            imageVector = Icons.Filled.Search,//Se asigna el icono del boton
            contentDescription = null,//Se asigna la descripcion por defecto
            tint = Color.White//Se asigna el color del icono, dependiendo el tema del celular
        )
    }
}

//Se crea un componente que contiene la opcion de ordenar las tareas
@Composable
fun SortAction(
    onSortClicked: (Priority) -> Unit//Recibe un parametro de tipo lambda, la cual servira para captar el click de las opcion a ordenar
){
    var expanded by remember { mutableStateOf(false) }//Se crea una variable para almacenar el estado de Dropdown
    IconButton(
        onClick = {//Cuando se clicke el icono, se quiere mostrar un dropdown menu y dentro de este, se quiere mostrar los 3 items drop
            expanded = true//Cuando se clicke, se pasara la variable a true y por ende se mostrara
        }
    ){
        Icon(//Se asigna el "icono"
            imageVector = Icons.Filled.Menu,//Se asigna el icono del boton
            contentDescription = null,//Se asigna la descripcion por defecto
            tint = Color.White//Se asigna el color del icono, dependiendo el tema del celular
        )
        DropdownMenu(
            expanded = expanded,//Se pasa el valor de la variable el cual estara en falso y mientras este asi, no se mostrar. Pero cuaando cambie se mostra el contenido
            onDismissRequest = {
                expanded = false//Cuando se presione fuera de menu, se cancelara la solicitud se ponda en falso
            }
        ){
            Priority.values().slice(setOf(0,2,3)).forEach { priority ->//Se llama a la clase "Priority" y se extraen los valores ".values()" = [Priority.values()] esta funcion recibira un array de todas las prioridades
                //Pero ojo solo se quieren items en especifico, entonces con "setOf" se mandan los que se quieren [(setOf(0,2,3)] y por cada ".forEach" opcion
                DropdownMenuItem(//Se desplegara un "DropdownMenuItem", la cual tendra...
                    onClick = {//Un boton de click
                        expanded = false//Se oibdra en falso
                        onSortClicked(priority)//Y se mandra la prioridad de la lambda renombrada
                    }
                ) {
                    PriorityItem(priority = priority)//En prioridad se asingara la prioridad
                }
            }
        }
    }
}

//Se crea un componente que contiene la opcion de eliminar
@Composable
fun DeleteAllAction(
    onDeleteAllConfirmed: () -> Unit//Recibe un parametro de tipo lambda, la cual servira para captar el click de eliminar
){
    var expanded by remember { mutableStateOf(false) }//Se crea una variable para almacenar el estado de Dropdown
    IconButton(
        onClick = {//Cuando se clicke el icono, se quiere mostrar un dropdown menu y dentro de este, se quiere mostrar los 3 items drop
            expanded = true//Cuando se clicke, se pasara la variable a true y por ende se mostrara
        }
    ){
        Icon(//Se asigna el "icono"
            imageVector = Icons.Filled.DeleteForever,//Se asigna el icono del boton
            contentDescription = null,//Se asigna la descripcion por defecto
            tint = Color.White//Se asigna el color del icono, dependiendo el tema del celular
        )
        DropdownMenu(
            expanded = expanded,//Se pasa el valor de la variable el cual estara en falso y mientras este asi, no se mostrar. Pero cuaando cambie se mostra el contenido
            onDismissRequest = {
                expanded = false//Cuando se presione fuera de menu, se cancelara la solicitud se ponda en falso
            }
        ){
            DropdownMenuItem(
                onClick = {//Cuando se clicke un icono
                    expanded = false//Se pondra en falso, lo cual hara que se encoja
                    onDeleteAllConfirmed ()//Se manda a la lambda
                }
            ){
                Text(
                    modifier = Modifier.padding(start = 12.dp),//Se asigna un padding
                    text = "Eliminar todo",//Se asigna la descripcion del texto
                    style = Typography.subtitle2//Se asigna un estilo de letra
                )
            }
        }
    }
}

/** Top Bar: Search **/
//Se crea el componente que tendra el topbar de buscar
@Composable
fun SearchAppBar(
    notesViewModel: NotesViewModel,//La funcion recibira un valor de tipo "NotesViewModel", con el cual se traeran los datos del viewmodel aqui
    notesUiState: HomeState,
    onCloseClicked: () -> Unit,//Se recibira una lambda la cual se llamara cuando se precione el boton de X
    onSearchClicked: (String) -> Unit//Recibira un lambda, que cuando se llame tomara al string que tiene dentro. Y posteriormente ese string lo buscara dentro de la base de datos
){
    Surface(
        modifier = Modifier
            .fillMaxWidth()//Se asigna un ancho completo
            .height(56.dp),//Se asigna un largo especifico
        elevation = AppBarDefaults.TopAppBarElevation,//Se asigna una elevacion, que ya esta predefinida por el sistema
        color = Orange//Se asigna un background
    ) {
        TextField(//Se llama al TextField
            value = notesUiState.searchTopBarTextField,//Se le asinga el texto, el cual sera el definito
            onValueChange = {//Se le asigna la variable que estara cambiando dependiendo lo que se escriba
                notesViewModel.onChangeSearchTopBarTextField(it)
            },
            placeholder = {//Se asigna un "placeholder" el cual sera el texto "fantasma"
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),//Se asigna un alpha "medio" de modo que se note "medio"
                    text = "Search",//Se asigna el texto
                    color =  Color.White//Se asinga el color
                )
            },
            textStyle = TextStyle(
                color = Color.White,//Se asigna el color de la letra cuando se escribe dentro del textField
                fontSize = MaterialTheme.typography.subtitle1.fontSize//Se asigna el estilo de la letra cuando se escribe dentro del textField
            ),
            singleLine = true,//Se especifica que solo sera una linea y no podra dar saltos de linea
            leadingIcon = {//Se especifica que tendra un icono al inicio del texto (sirve como referencia)
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.disabled),//Se asigna un alpha "deshabilitado" de modo que se note "apagado"
                    onClick = {

                    }
                ){
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {//Se especifica que tendra un icono al final del texto (sirve para borrar toda la informacion que tiene [cuando hay] o borrar cerrar la pantalla)
                IconButton(//Cuando se presione el boton X
                    onClick = {
                        if (notesUiState.searchTopBarTextField.isNotEmpty()){//Se pregunta, si el texto NO ESTA VACIO
                            notesViewModel.onChangeSearchTopBarTextField("")
                        }else{//Si no tiene nada
                            onCloseClicked()//Se cierra
                        }
                    }
                ){
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            },
            keyboardOptions = KeyboardOptions(//Se especifica que dentro del teclado haya un boton de busqueda en lugar de salto de linea
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(//Se especifica que cuando se precione el boton de busqueda dentro del teclado
                onSearch = {
                    onSearchClicked(notesUiState.searchTopBarTextField)//Se mande la lambda con el texto "final" captado
                }
            ),
            colors = TextFieldDefaults.textFieldColors(//Se asigna una configuracion dentro del Textfield para la letra
                cursorColor = MaterialTheme.colors.BackgroundTextField,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                textColor = MaterialTheme.colors.ColorTextTheme, //Se cambia el color del texto dentro de OutlinedTextField

            )
        )
    }
}