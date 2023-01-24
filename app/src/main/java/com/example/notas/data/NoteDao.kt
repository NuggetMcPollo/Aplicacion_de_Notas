package com.example.notas.data

import androidx.room.*
import com.example.notas.data.models.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    /** En este caso se escribe un Query que leera todas las tareas de la tabla de la base de datos. Selecciona T0DO de la tabla "note_table" y ordenalo de manera ASCENDENTE deacuerdo al parametro "id", el cual es su "Primery Key" y [traelo] **/
    //Esta funcion leera todas las notas desde la tabla de la base de datos
    @Query("SELECT * FROM note_table ORDER BY id ASC")//La anotacion "@Query", permitira especificar nuestra propia query personalizada dentro de los parametros de esta anotacion.
    fun getAllNotes(): Flow<List<NoteEntity>>//La funcion deberia regresar el resultado de la Query. la cual sera una lista de "ToDoTask". Se esta usando el Flow, porque basicamente es un flujo de datos asíncrono

    /**  En este caso se escribe un Query que traera UNA tarea de la tabla de la base de datos. Selecciona T0DO de la tabla "note_table" y donde el parametro "id" sea igual al "noteId" [parametro mandando a la funcion] y traerlo **/
    //Esta funcion obtendra una sola nota desde la tabla de la base de datos
    @Query("SELECT * FROM note_table WHERE id = :noteId")//La anotacion "@Query", permitira especificar nuestra propia query personalizada dentro de los parametros de esta anotacion.
    fun getSelectedNote(noteId: Int): Flow<NoteEntity>//La funcion recibe un valor de tipo "Int", el cual debera concordar con la busqueda de la tarea. Deberia regresar el resultado de la Query. la cual sera un valor de tipo "ToDoTask". Se esta usando el Flow, porque basicamente es un flujo de datos asíncrono

    /**  En este caso se escribe un Query que eliminara toda la tabla. Selecciona desde la tabla "note_table" y eliminala **/
    //Esta funcion eliminara todas las notas
    @Query("DELETE FROM note_table")//La anotacion "@Query", permitira especificar nuestra propia query personalizada dentro de los parametros de esta anotacion.
    suspend fun deleteAllNotes()//La funcion no deberia regresar nada del resultado de la Query. Simplemente se indico que se elminiara T0DO

    /**  En este caso se escribe un Query que buscara en la tabla. Selecciona T0DO de la tabla "note_table" y donde el parametro "title" o "content" [NOMBRE DE LAS COLUMNAS], sea igual a lo que se paso por parametro "searchQuery" y [traelo] **/
    //Esta funcion buscara dentro de la base de datos por titulo o descripcion de la tarea
    @Query("SELECT * FROM note_table WHERE title LIKE :searchNotes OR content LIKE :searchNotes")//La anotacion "@Query", permitira especificar nuestra propia query personalizada dentro de los parametros de esta anotacion.
    fun searchNotesDatabase (searchNotes: String): Flow<List<NoteEntity>>//La funcion deberia regresar el resultado de la Query. la cual sera una lista de "ToDoTask". Se esta usando el Flow, porque basicamente es un flujo de datos asíncrono

    //Esta funcion agregara o insertara una nueva nota a la base de datos
    @Insert(onConflict = OnConflictStrategy.IGNORE)//Se coloca un IGNORE en caso de conflicto pero se puede colocar un REPLACE, para que asi no haya problemas
    suspend fun addNote(note: NoteEntity)//La funcion sera de tipo "suspend", porque correra dentro de una corrutina. Recibe un parametro de tipo "ToDoTask", el cual sera la tarea a agregar pero no regresa nada

    //Esta funcion actualizara una nota a la base de datos
    @Update
    suspend fun updateNote(note: NoteEntity)//La funcion sera de tipo "suspend", porque correra dentro de una corrutina. Recibe un parametro de tipo "ToDoTask", el cual sera la tarea a actualizar pero no regresa nada

    //Esta funcion elimina una nota a la base de datos
    @Delete
    suspend fun deleteNote(note: NoteEntity)//La funcion sera de tipo "suspend", porque correra dentro de una corrutina. Recibe un parametro de tipo "ToDoTask", el cual sera la tarea a eliminar pero no regresa nada

    //TODO: EL PORQUE LAS DOS PRIMERAS NO USAN "SUSPEND", ES PORQUE UTILIZAN FLOW, EL CUAL POR DEFECTO CORREN DENTRO DE CORRUTINAS

    /**  En este caso se escribe un Query que ordenera la tabla, deacuerdo a la prioridad "Baja".
     *
     *  Selecciona T0DA la tabla "todo_table" y ordenara los resultados de la base de datos por el parametro "priority", asi que...
     *
     *      Primero se obtendra tod0 el parametro "priority", donde la columna contenga el caracter "L%"; despues...
     *      Buscara todas las tareas del parametro "priority", donde la columna contenga el caracter "L%"; por ultimo...
     *      Ordenara todas las tareas del parametro "priority", donde la columna contenga el caracter "H%"
     *
     *  "L%" significa que buscará la tarea que contiene prioridad que comienza con la letra L
     *  "M%" significa que buscará la tarea que contiene prioridad que comienza con la letra M
     *  "H%" significa que buscará la tarea que contiene prioridad que comienza con la letra H
     **/
    //Esta funcion ordenara por prioridad las tareas "Baja - Media - Alta", por defecto la prioridad sera guardada como un string en la base de datos como [Low, Medium, High]
    @Query(//La anotacion "@Query", permitira especificar nuestra propia query personalizada dentro de los parametros de esta anotacion.
        """
        SELECT * FROM note_table ORDER BY
    CASE
        WHEN priority LIKE 'L%' THEN 1
        WHEN priority LIKE 'M%' THEN 2
        WHEN priority LIKE 'H%' THEN 3
    END
    """
    )
    fun sortByLowPriority(): Flow<List<NoteEntity>>//La funcion deberia regresar el resultado de la Query. la cual sera una lista de "ToDoTask". Se esta usando el Flow, porque basicamente es un flujo de datos asíncrono


    /**  En este caso se escribe un Query que ordenera la tabla, deacuerdo a la prioridad "Baja".
     *
     *  Selecciona T0DA la tabla "todo_table" y ordenara los resultados de la base de datos por el parametro "priority", asi que...
     *
     *      Primero se obtendra tod0 el parametro "priority", donde la columna contenga el caracter "L%"; despues...
     *      Buscara todas las tareas del parametro "priority", donde la columna contenga el caracter "L%"; por ultimo...
     *      Ordenara todas las tareas del parametro "priority", donde la columna contenga el caracter "H%"
     *
     *  "L%" significa que buscará la tarea que contiene prioridad que comienza con la letra L
     *  "M%" significa que buscará la tarea que contiene prioridad que comienza con la letra M
     *  "H%" significa que buscará la tarea que contiene prioridad que comienza con la letra H
     **/
    //Esta funcion ordenara por prioridad de tareas "Alta - Media -Baja", por defecto la prioridad sera guardada como un string en la base de datos como [Low, Medium, High]
    @Query(//La anotacion "@Query", permitira especificar nuestra propia query personalizada dentro de los parametros de esta anotacion.
        """
        SELECT * FROM note_table ORDER BY
    CASE
        WHEN priority LIKE 'H%' THEN 1
        WHEN priority LIKE 'M%' THEN 2
        WHEN priority LIKE 'L%' THEN 3
    END
    """
    )
    fun sortByHighPriority(): Flow<List<NoteEntity>>//La funcion deberia regresar el resultado de la Query. la cual sera una lista de "ToDoTask". Se esta usando el Flow, porque basicamente es un flujo de datos asíncrono
}