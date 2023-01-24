package com.example.notas.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.notas.presentation.screen.home.Priority
import com.example.notas.util.Constants.PREFERENCE_KEY
import com.example.notas.util.Constants.PREFERENCE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

//Se declara una extencion de propiedad, sobre el objeto context "Context.dataStore" y se usa el "by" para llamar al "preferencesDataStore" y se pasa el nombre de la datastore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

@ViewModelScoped//Se agrega la anotacion "@ViewModelScoped", porque se va injectar esta "DataStoreRepository" dentro del viewmodel
class DataStoreRepository @Inject constructor(//Se injecta dentro del constructor el contexto
    @ApplicationContext private val context: Context//Se indica con la anotacion "@ApplicationContext", para injectar el contexto
) {
    //Se crea un object, para especificar la llave actual para el estado de origen
    private object PreferenceKeys {
        //Se puede usar "intPreferencesKey" o algun otro tipo de valor que se quiera salvar
        val sortKey = stringPreferencesKey(name = PREFERENCE_KEY)//Se crea una variable, a la cual se le va asignar "stringPreferencesKey" porque en este caso se quiere guardar un simple valor string y se pasa el nombre de la llave
        //Basicamente, se quiere almacenar solo un valor de tipo string usando esta llave que se ha especificado en la constante. Y esa sera la "llave - key" o el "actual valor" el cual se va a obtener desde la funcion
    }

    private val dataStore = context.dataStore//Se crea una variable para almacenar el "context.dataStore" la cual se obtiene de instanciar la clase bien

    // Se crea una funcion para guardar o persistir el ordenamiento del estado, como ya se declaro la llave, se necesita usar esa llave para guardar el valor especifico
    /** Esta funcion guarda o persite el "nombre" de la prioridad que se esta pasando desde el parametro de esta funcion **/
    suspend fun persistSortState(priority: Priority) {//La funcion recbira un valor de tipo "Priority"
        dataStore.edit { preference ->//Se llama a la variable "dataStore" y se le agrega la funcion "edit". Con esto se guardara el valor en la datastore
            preference[PreferenceKeys.sortKey] = priority.name//Se llama a la lambda renombrada y se le especifica la llave. Que en este caso esta dentro del objecto "PreferenceKeys.sortKey". A esto se le asigna la prioridad
            //recibida obteniendole el nombre
        }
    }

    //Se crea una variable que pueda leer el orden del estado
    val readSortState: Flow<String> = dataStore.data//Se crea una variable de tipo "Flow" y esta aceptara un valor de tipo string. Se le asignara el valor de "dataStore.data"
        .catch { exception ->//Se llama a la funcion "catch" para poder atrapar si hay una excepcion
            if (exception is IOException) {//Se hace un bloque if, y se pregunta si hay una excepcion y esta es de tipo "IOException"
                emit(emptyPreferences())//Se quiere emitira "emptyPreferences() - Preferencias vacias"
            } else {//En caso de que haya una excepcion y no sea de tipo "IOException"
                throw exception//Se "lanzara" la excepción
            }
        }
        .map { preferences ->//Se llama a la funcion "map"
            val sortState = preferences[PreferenceKeys.sortKey] ?: Priority.Ninguna.name//Se crea una variable, a la cual se le asignara la lambda, para obtener el valor especificando aqui de la "llave - key". El cual esta dentro
            // del objecto "PreferenceKeys.sortKey". Y se asinga el operador elvys, con el cual basicamente, si no hay ningun valor guardado dentro del data-store se quiere devolver la prioridad "NONE"
            sortState//Se añade el valor "sortState"
        }
}