package com.example.notas.presentation.screen.home.notes

import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notas.data.models.NoteEntity
import com.example.notas.data.repository.DataStoreRepository
import com.example.notas.data.repository.NoteRepository
import com.example.notas.presentation.screen.home.*
import com.example.notas.util.Request
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

//En este viewmodel, se injectara el repositorio. Si esta haciendo un SharedViewModel, porque compartira multiples pantallas composables
@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NoteRepository,//Se crea una variable con la cual se esta injectando el "ToDoRepository"
    private val dataStoreRepository: DataStoreRepository//Se crea una variable con la cual se esta injectando el "DataStoreRepository"
): ViewModel(){

    var state by mutableStateOf(HomeState())//Se crea una variable la cual tendra un "mutableStateOf", pero en este caso de tipo "HomeState()" la cual es la clase que almacenadora de estados. Ya bien acomodados
        private set//Se agrega el "private set" para que la vista pueda acceda a los estados y NO LOS edite

    private fun onUpdateNotes(id: Int, title: String, content: String, priority: Priority, time: Long){
        state = state.copy(
            id = id,
            title = title,
            content = content,
            priority = priority,
            time = time
        )
    }

    //Se crean los eventos los cuales van a modificar los estados declarados en "state" dentro del Ui
    fun onChangeSearchTopBarTextField(search: String){//Se crea una funcion la cual recibira un string
        state = state.copy(searchTopBarTextField = search)//A la variable "state", se le cambiara el valor, asignandole el valor de "state.copy(searchTopBar = userName)"
    }
    fun onChangeSearchTopBarAction(searchTopBarAction: SearchTopBarAction){//Se crea una funcion la cual recibira un string
        state = state.copy(searchTopBarAction = searchTopBarAction)//A la variable "state", se le cambiara el valor, asignandole el valor de "state.copy(searchTopBar = userName)"
    }
    fun onChangeNoteAction(noteAction: NoteAction){//Se crea una funcion la cual recibira un string
        state = state.copy(noteAction = noteAction)//A la variable "state", se le cambiara el valor, asignandole el valor de "state.copy(searchTopBar = userName)"
    }

    /* private val _stateFlow = MutableStateFlow(HomeStateFlow())
    val stateFlow = _stateFlow.asStateFlow()*/

    private val _allNotes = MutableStateFlow<Request<List<NoteEntity>>>(Request.Static)
    val allNotes: StateFlow<Request<List<NoteEntity>>> = _allNotes

    private val _searchedNotes = MutableStateFlow<Request<List<NoteEntity>>>(Request.Static)
    val searchedNotes: StateFlow<Request<List<NoteEntity>>> = _searchedNotes

    private val _sortState = MutableStateFlow<Request<Priority>>(Request.Static)
    val sortState: StateFlow<Request<Priority>> = _sortState


    init {//Dentro del init, se llaman a las funciones para que estas inicien cuando se lanze el viewmodel por primera vez
        getAllNotes()
        readSortState()
    }

    //Esta crea una funcion que cuando sea llamada, recuperará todas las tareas que tenemos dentro de nuestra tabla de base de datos
    private fun getAllNotes() {
        _allNotes.value = Request.Loading
        try {//Estara en un bloque try, en caso de cualquier error
            viewModelScope.launch {//Se llama a "viewModelScope" la cual es un alcance de corrutina vinculado al ciclo de vida del "ViewModel" el cual se comparte con varias screens
                repository.getAllNotes.collect {//Se llama a la variable "getAllNotes", la cual esta dentro de "repository" y con el operador "collect" se traen los datos
                    Notes ->
                    /** Este operador procesa todos los valores emitidos y manejar una excepción que pudiera ocurrir durante el procesamiento **/
                    _allNotes.value = Request.Success(Notes)
                }
            }
        }catch (e:Exception){//Si hay un error
            _allNotes.value = Request.Error(e)
        }/** LA VARIABLE: "allNotes" tendra todos los datos **/
    }

    //Esta función cuando se DISPARADA, buscara todas las tareas que tenemos dentro de nuestra tabla de base de datos, con las similitudes que se indican, y asignará estos datos a la variable "_searchedTasks"
    fun searchNotes(searchNotes: String) {//La funcion recibira un parametro, la cual es la consulta a realizar
        _searchedNotes.value = Request.Loading
        try {//Estara en un bloque try, en caso de cualquier error
            viewModelScope.launch {//Se llama a "viewModelScope" la cual es un alcance de corrutina vinculado al ciclo de vida del "ViewModel" el cual se comparte con varias screens
                repository.searchNotesDatabase(searchNotes = "%$searchNotes%").collect{//Se llama a la funcion "searchNotesDatabase", la cual esta dentro de "repository"
                    // mandandole la busqueda a realizar y con el operador "collect" se traen los datos
                    /** Este operador procesa todos los valores emitidos y manejar una excepción que pudiera ocurrir durante el procesamiento **/
                    searchNotes ->
                        _searchedNotes.value = Request.Success(searchNotes)
                }/** OJO IMPORTANTE: RECUERDA AGREGAR EN LA VARIBALE DE BUSQUEDA LOS SIMBOLOS "%$%" DENTRO PUES SE ESTA USANDO UN [OR] DENTRO DE LA BUSQUEDA, Y SE CAMBIA DEPENDIENDO LA BUSQUEDA REVISA ESO**/
            }
        }catch (e:Exception){//Si hay un error
            _searchedNotes.value = Request.Error(e)
        }
        state = state.copy(searchTopBarAction = SearchTopBarAction.TRIGGERED)//Se actualizara la variable "searchBarAction"
    }

    //Esta funcion verifica si se quiere "Actualiza", una nota o "Crear" una nota
    fun noteBlankOrUpdateNotes(selectNote: NoteEntity?){//La funcion recibira un parametro de tipo "ToDoTask" y podra ser nulable
        if(selectNote != null){//Se verifica si la tarea que se selecciono NO ES NULA. Eso quiere decir que se ha hecho clic en una de las tareas de la lista y se ha pasado bien el "ID"
            onUpdateNotes(
                id = selectNote.id,
                title = selectNote.title,
                content = selectNote.content,
                priority = selectNote.priority,
                time = selectNote.time
            )
        }else{//Si no, quiere decir que la tarea seleccionada es nula. Eso quiere decir que se preciono el boton flotante
            state = HomeState()
        }
    }

    //Esta funcion agregara una tarea
    private fun addNote(){
        viewModelScope.launch (Dispatchers.IO){//Se llama a "viewModelScope" la cual es un alcance de corrutina vinculado al ciclo de vida del "ViewModel" el cual se comparte con varias screens. Se le agrega "Dispatchers.IO"
            val noteEntity = NoteEntity(
                //OJO: NO SE ESPECIFICA EL ID, YA QUE ESTE SERA GENERADO AUTOMATICAMENTE POR EL SISTEMA
                title = state.title,
                content = state.content,
                priority = state.priority,
                time = state.time
            )
            repository.addNote(noteEntity = noteEntity)//Se llama al repositorio, accediendo a la funcion "addTask" y mandandole la tarea que se acaba de crear
        }
        state = state.copy(searchTopBarAction = SearchTopBarAction.CLOSED)//Si se agrega una nueva tarea se tiene que poner el estado de busqueda en cerrado
    }

    //Esta funcion actualizara una tarea
    private fun updateNote(){
        viewModelScope.launch (Dispatchers.IO) {
            val noteEntity = NoteEntity(
                id = state.id,//La razón por la que se necesita especificar el ID, es porque se necesita actualizar la misma tarea que hemos seleccionado y asi podemos especificar lo mismo para la tarea, usando el mismo ID
                title = state.title,
                content = state.content,
                priority = state.priority,
                time = state.time
            )
            repository.updateNote(noteEntity = noteEntity)//Se llama al repositorio, accediendo a la funcion "updateTask" y mandandole la tarea que se acaba de seleccionar
        }
    }

    //Esta funcion eliminara una tarea
    private fun deleteNote(){
        viewModelScope.launch (Dispatchers.IO) {
            val noteEntity = NoteEntity(
                id = state.id,//La razón por la que se necesita especificar el ID, es porque se necesita actualizar la misma tarea que hemos seleccionado y asi podemos especificar lo mismo para la tarea, usando el mismo ID
                title = state.title,
                content = state.content,
                priority = state.priority,
                time = state.time
            )
            repository.deleteNote(noteEntity = noteEntity)//Se llama al repositorio, accediendo a la funcion "updateTask" y mandandole la tarea que se acaba de seleccionar
        }
    }

    //Esta funcion eliminara todas las tareas
    private fun deleteAllNotes(){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteAllNotes()//Se llama al repositorio, accediendo a la funcion "deleteAllTasks". Esta no recibe nada, solo se activa la funcion
        }
    }

    //Esta funcion, tomara la accion correspondiente a lo que se haga. y se llamara desd el List-Screen
    fun handleDatabaseActions(action: NoteAction){
        when(action){//Cuando la accion sea...
            NoteAction.ADD ->{//Agregar
                addNote()//Llamara a la funcion
            }
            NoteAction.UPDATE ->{//Actualizar
                updateNote()//Llamara a la funcion
            }
            NoteAction.DELETE ->{//Eliminar
                deleteNote()//Llamara a la funcion
            }
            NoteAction.UNDO ->{//Deshacer
                addNote()
            }
            NoteAction.DELETE_ALL ->{
                deleteAllNotes()
            }
            else -> {

            }
        }
        //this.action.value = Action.NO_ACTION//Cada vez que se termine el bloque "When", se quiero cambiar el valor de la variable accion a su valor predeterminado
        //SE BORRO LA LINEA DE ARRIBA PORQUE AHORA SE ESTA REINICIANDO EL VALOR DE ACCION DESDE EL LIST-SCREEN CADA VEZ QUE SE ACTIVA EL LAMBDA "ONCOMPLETE"
    }
    /* Este operador generalmente se usa con los operadores onEach , onCompletion y [catch] para procesar todos los valores emitidos
       y manejar una excepción que podría ocurrir en el ascendente de flow o durante el procesamiento, por ejemplo:

       .onEach { value -> process(value) }
       .catch { e -> handleException(e) }
       .collect() // Disparara el flow de coleccion
     */

    //Se crea una variable para traer el resultado de "sortByLowPriority" desde el repositorio
    val lowPriorityNotes: StateFlow<List<NoteEntity>> = //Esta variable sera expuesta dentro del los composable, donde ahi sera observada y sera notificada desde el "aqui"
        repository.sortByLowPriority.stateIn(//Se le asignara el resultado de "sortByLowPriority" y con el agregado de la funcion "stateIn", se convierte este en un "StateFlow"
            scope = viewModelScope,//Dentro de la funcion "stateIn" se le asigna al parametro "scope - alcanze", el "viewModelScope"; el cual es el alcance de
            // corrutina vinculado al ciclo de vida del "ViewModel" el cual se comparte con varias screens
            started = SharingStarted.WhileSubscribed(),//Dentro de la funcion "stateIn" se le asigna al parametro "started - inicio" el
            initialValue = emptyList()//Dentro de la funcion "stateIn" se le asigna al parametro "initialValue - valor inicial", el valor con el que iniciara
            //ESTA VARIABLE "lowPriorityNotes" EL CUAL SERA "emptyList()"
        )

    //Se crea una variable para traer el resultado de "sortByLowPriority" desde el repositorio
    val highPriorityNotes: StateFlow<List<NoteEntity>> = //Esta variable sera expuesta dentro del los composable, donde ahi sera observada y sera notificada desde el "aqui"
        repository.sortByHighPriority.stateIn(//Se le asignara el resultado de "sortByLowPriority" y con el agregado de la funcion "stateIn", se convierte este en un "StateFlow"
            scope = viewModelScope,//Dentro de la funcion "stateIn" se le asigna al parametro "scope - alcanze", el "viewModelScope"; el cual es el alcance de
            // corrutina vinculado al ciclo de vida del "ViewModel" el cual se comparte con varias screens
            started = SharingStarted.WhileSubscribed(),//Dentro de la funcion "stateIn" se le asigna al parametro "started - inicio" el
            initialValue = emptyList()//Dentro de la funcion "stateIn" se le asigna al parametro "initialValue - valor inicial", el valor con el que iniciara
            //ESTA VARIABLE "lowPriorityNotes" EL CUAL SERA "emptyList()"
        )

    //Se crea una funcion que mostrara un mensaje dependiendo la situacion
    fun setMessage(noteAction: NoteAction, noteTitle: String): String{//La funcion recibe la accion y el nombre de la tarea. Esta regresara un string, el cual sera el mensaje
        return when(noteAction){//Se pregunta, la accion es igual....
            NoteAction.DELETE_ALL -> "All Task Removed."//"Action.DELETE_ALL", de ser asi, entonces manda el mensaje
            else -> "${noteAction.name}: $noteTitle"//De no se asi, entonces para el mensaje, se obtendra la referencia de la accion "NOMBRE" y se enlazara con el titulo de la tarea seleccionada o escrita
        }
    }

    //Se crea una funcion, la cual mostrara un mensaje dependiendo la accion
    fun setActionLabel(noteAction: NoteAction): String {//Devolvera un string
        return if(noteAction.name == "DELETE"){//Pregunta, si la accion recivida es igual "DELETE", de ser asi...
            "UNDO"//Escribe "Deshacer"
        }else{//Si no es, muestra...
            "OK"//
        }
    }

    //Se crea una funcion que deshara la tarea eliminada
    fun undoDeletedTask(
        noteAction: NoteAction,//La funcion recibira un parametro de tipo "Action"
        snackBarState: SnackbarResult,//La funcion recibira un parametro de tipo "SnackbarResult"
        onUndoClicked: (NoteAction) -> Unit//La funcion recibira un lambda para capturar el click de deshacer
    ){
        if (snackBarState == SnackbarResult.ActionPerformed//Se pregunta si "snackBarState" es igual a "SnackbarResult.ActionPerformed", esto significa que se ha hecho clic en la acción en el Snackbar antes de que transcurriera el tiempo de espera
            && noteAction == NoteAction.DELETE){//Y aparte la acion es igual a "Action.DELETE"
            onUndoClicked(NoteAction.UNDO)//Se pasara la accion de "Action.UNDO", deshacer
        }
    }

    //Se crea una funcion que leera el orden del estado
    private fun readSortState(){
        _sortState.value = Request.Loading
        try {//Estara en un bloque try, en caso de cualquier error
            viewModelScope.launch {//Se llama a "viewModelScope" la cual es un alcance de corrutina vinculado al ciclo de vida del "ViewModel" el cual se comparte con varias screens
                dataStoreRepository.readSortState//Se llama a la variable "readSortState", la cual puede leer el orden del estado
                    .map {//Se llama a la funcion "map"
                        Priority.valueOf(it)//se llama a "Priority.valueOf" y se le manda la prioridad "it"
                    }
                    .collect{  data ->//Se quiere recoger el valor y como se puede ver el valor que se va a obtener del data-store, es el string actual. Y esa string se necesita converir a prioridad ARRIBA ESTA ESO EN MAP
                        //Se necesita recoger la prioridad instanciada de este string, que es lo que se hizo en el .map
                        _sortState.value = Request.Success(data)
                    }
            }
        }catch (e:Exception){//Si hay un error
            _sortState.value = Request.Error(e)
        }
    }

    //Se crea una funcion que guardar o persistira el valor dentro del data-store, para eso se le pasara la prioridad recivida
    fun persistSortState(priorityAction: Priority){//La funcion recibira de parametro un valor de tipo "Priority"
        viewModelScope.launch(Dispatchers.IO) {//Se llama a "viewModelScope" la cual es un alcance de corrutina vinculado al ciclo de vida del "ViewModel" el cual se comparte con varias screens. Se le agrega "Dispatchers.IO"
            dataStoreRepository.persistSortState(priority = priorityAction)//Se llama a la variable que contiene el dataStore "dataStoreRepository", junto con la funcion que interesa "persistSortState" y se le manda la variable
            //recivida y a partir de ahí, se va a obtener o extraer el "nombre" de esa prioridad para luego almacenarlo en el "data store preference" con la "llave - key" específica, que se ha establecido en el "preferences object"
        }
    }
}