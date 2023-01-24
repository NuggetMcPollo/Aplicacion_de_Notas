package com.example.notas.presentation.screen.home.details

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notas.data.models.NoteEntity
import com.example.notas.data.repository.NoteRepository
import com.example.notas.presentation.screen.home.HomeState
import com.example.notas.presentation.screen.home.HomeStateFlow
import com.example.notas.presentation.screen.home.Priority
import com.example.notas.util.Request
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

//En este viewmodel, se injectara el repositorio. Si esta haciendo un SharedViewModel, porque compartira multiples pantallas composables
@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: NoteRepository,//Se crea una variable con la cual se esta injectando el "ToDoRepository"
): ViewModel(){

    var state by mutableStateOf(HomeState()) //Se crea una variable la cual tendra un "mutableStateOf", pero en este caso de tipo "HomeState()" la cual es la clase que almacenadora de estados. Ya bien acomodados
    private set//Se agrega el "private set" para que la vista pueda acceda a los estados y NO LOS edite

    private val _selectedNotes: MutableStateFlow<NoteEntity?> = MutableStateFlow(null)
    val selectedNotes: StateFlow<NoteEntity?> = _selectedNotes

    private fun onUpdateDetails(id: Int, title: String, content: String, priority: Priority, time: Long){
        state = state.copy(
            id = id,
            title = title,
            content = content,
            priority = priority,
            time = time
        )
    }

    //Esta funcion se encargara de actualizar el titulo de la tarea
    fun updateTitle(title: String){//La funcion recibira un parametro de tipo String, el cual sera el nuevo titulo
        if (title.length < 20){//Se hace un bloque If, preguntando si "EL NUEVO TITULO" es MENOR QUE "EL NUMERO DE LA CONSTANTE = 20"
            state = state.copy(title = title)//Se le asignara el nuevo titulo al valor que tiene la tarea actual
        }
    }

    fun onChangeContent(content: String){//Se crea una funcion la cual recibira un string
        state = state.copy(content = content)//A la variable "state", se le cambiara el valor, asignandole el valor de "state.copy(searchTopBar = userName)"
    }
    fun onChangePriority(priority: Priority){//Se crea una funcion la cual recibira un string
        state = state.copy(priority = priority)//A la variable "state", se le cambiara el valor, asignandole el valor de "state.copy(searchTopBar = userName)"
    }

    //Esta funcion obtendra una tarea seleccionada desde la base de datos
    fun getSelectedTask(noteId: Int){//La funcion recibe un parametro de tipo "Int" el cual sera el ID de la tarea seleccionada, el cual se va a pasar desde esta funcion hacia la base de datos
        viewModelScope.launch {//Se llama a "viewModelScope" la cual es un alcance de corrutina vinculado al ciclo de vida del "ViewModel" el cual se comparte con varias screens
            repository.getSelectedNote(noteId = noteId).collect{ Note -> //Se llama a la funcion "getSelectedNote", la cual esta dentro de "repository" mandandole el id de la nota a
                //buscar y con el operador "collect" se traen los datos
                _selectedNotes.value = Note
            }
        }
    }

    //Esta funcion validara los campos dentro de la tarea
    fun validateFields(title: String, content: String): Boolean{//Regresara un valor boleano, el cual sera falso y verdadero
        return title.isNotEmpty() && content.isNotEmpty()//Devolvera verdadero si se cumplen ambas condiciones osea, si hay contenido en los dos campos
    }

    //Esta funcion verifica si se quiere "Actualiza", una nota o "Crear" una nota
    fun noteBlankOrUpdateDetails(selectNote: NoteEntity?){//La funcion recibira un parametro de tipo "ToDoTask" y podra ser nulable
        if(selectNote != null){//Se verifica si la tarea que se selecciono NO ES NULA. Eso quiere decir que se ha hecho clic en una de las tareas de la lista y se ha pasado bien el "ID"
            onUpdateDetails(
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

    fun displayToast(context: Context) {
        Toast.makeText(
            context,
            "Fields Empty.",
            Toast.LENGTH_SHORT
        ).show()
    }

}