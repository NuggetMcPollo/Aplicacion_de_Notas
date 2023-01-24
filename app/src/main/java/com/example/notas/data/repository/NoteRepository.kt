package com.example.notas.data.repository

import com.example.notas.data.NoteDao
import com.example.notas.data.models.NoteEntity
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//Dentro de esta clase se inyectara el Dao
@ViewModelScoped//Esta anotacion le dice a la instancia "ToDoRepository" que estará viva mientras el "View Model" con el que comparte datos injectados esta viva
/** Ahora, como Dagger sabe que debe injectar "NoteDao", eso es porque dentro de nuestro "DataBaseModule" se ha creado una funcion la cual regresa un tipo [ = database.NoteDao() ]  con esto
basicamente la libreria esta usando ese tipo de retorno para decidir, cuál de esas todas las funciónes que proporciona el object "DatabaseModule" debe inyectarse en el repositorio. En
este caso [NoteDao]
 **/
class NoteRepository @Inject constructor(//Con la anotacion "@INJECT", se le esta inyectando las funciones al repositorio, esto se consegue con la libreria hilt.
    private val noteDao: NoteDao//Dentro de este repositorio se usara "noteDao", para acceder a todas esas funciones que ya tiene ESA interfaz.
) {
//**************** MAS TARDE ESTE REPOSITORIO SERA INJECTADO DENTRO DEL VIEWMODEL, Y DESDE EL VIEWMODEL, SE VA TENER ACCESO A TODOS LOS "SQL QUERYS" LOS CUALES YA SE HICIERON ***********************//

    val getAllNotes: Flow<List<NoteEntity>> = noteDao.getAllNotes()//Se crea una variable "getAllNotes" y esta sera de tipo "Flow<List<NoteEntity>>" [DEBE DE RETORNAR EL MISMO TIPO QUE SE DECLARO EN EL "NoteDao"]
    val sortByHighPriority: Flow<List<NoteEntity>> = noteDao.sortByHighPriority()//Se crea una variable "sortByHighPriority" y esta sera de tipo "Flow<List<NoteEntity>>" [DEBE DE RETORNAR EL MISMO TIPO QUE SE DECLARO EN EL "NoteDao"]
    val sortByLowPriority: Flow<List<NoteEntity>> = noteDao.sortByLowPriority()//Se crea una variable "sortByLowPriority" y esta sera de tipo "Flow<List<NoteEntity>>" [DEBE DE RETORNAR EL MISMO TIPO QUE SE DECLARO EN EL "NoteDao"]

    fun getSelectedNote(noteId: Int): Flow<NoteEntity>{//Se crea una funcion la cual recibira un valor de tipo "Int" y esta retornara un "Flow<NoteEntity>"
        return noteDao.getSelectedNote(noteId = noteId)//Regresara la funcion que retorna un valor de tipo "getSelectedTask" y se le PASARA el valor que recibio la funcion. Y esta asu vez regresara la tarea con el mismo ID que se le envio
    }

    fun searchNotesDatabase(searchNotes: String): Flow<List<NoteEntity>> {//Se crea una funcion la cual recibira un valor de tipo "String", esta servira para buscar una tarea y esta retornara un "Flow<ToDoTask>"
        return noteDao.searchNotesDatabase(searchNotes = searchNotes)//Regresara la funcion que retorna un valor de tipo "searchDatabase" y se le PASARA el valor que recibio la funcion. Y esta asu vez regresara la tarea con el mismo string similar
    }

    /** RECUERDA QUE LAS PRIMERAS NO FUERON SUSPEND PORQUE REGRESABAN UN VALOR DE TIPO "FLOW" LA CUAL YA ES ASINCRONA POR DEFECTO **/

    suspend fun addNote(noteEntity: NoteEntity) {//Se crea una funcion la cual recibira un valor de tipo "ToDoTask"
        noteDao.addNote(note = noteEntity)//Se le mandara a "toDoDao.addTask(toDoTask = toDoTask)", la tarea que recibio
    }

    suspend fun updateNote(noteEntity: NoteEntity) {//Se crea una funcion la cual recibira un valor de tipo "ToDoTask"
        noteDao.updateNote(note = noteEntity)//Se le mandara a "toDoDao.addTask(toDoTask = toDoTask)", la tarea que recibio
    }

    suspend fun deleteNote(noteEntity: NoteEntity) {//Se crea una funcion la cual recibira un valor de tipo "ToDoTask"
        noteDao.deleteNote(note = noteEntity)//Se le mandara a "toDoDao.addTask(toDoTask = toDoTask)", la tarea que recibio
    }

    suspend fun deleteAllNotes() {//Se crea una funcion la cual eliminara TODAS las tareas
        noteDao.deleteAllNotes()//Llamara a la funcion que hace el Query de eliminar TODAS LAS TAREAS
    }

}