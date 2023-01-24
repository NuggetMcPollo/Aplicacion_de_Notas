package com.example.notas.di

import android.content.Context
import androidx.room.Room
import com.example.notas.data.NoteDatabase
import com.example.notas.util.Constants.Note_Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module//Se le agrega la anotacion "@Module" ya que solo ella "no proveerá nada."
@InstallIn(SingletonComponent::class)//Con "SingletonComponent", se crean las instancias una sola vez y si se usa en otro lado se comparte la misma instancia, pues esta abarca toda la aplicacion, asi en lugar de usar
// dos, tres o cuatro entidades de retrofit, se crea una sola y se reutiliza en todos lados
object DatabaseModule {

    @Provides//Con la anotacion "@Provides", se encarga de decir que esta funcion sera una dependencia que va a proveer, en este caso el Dao.
    @Singleton//Con la anotacion "@Singleton", se indica que es un "SingletonComponent", esta define que solo sera una sola instancia a través de toda la aplicación "(SingletonComponent::class)"
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(//La funcion regresa un valor de tipo "Room.databaseBuilder" la cual, en si pasara la base de datos
        context,//Se pasa el contexto
        NoteDatabase::class.java,//Se pasa la base de datos
        Note_Database//Se pasa el nombre de la base de datos
    ).build()//Finalmente con .build se provee la base de datos

    //Se quiere crear la dependencia o provedor del "Dao"
    @Provides//Con la anotacion "@Provides", se encarga de decir que esta funcion sera una dependencia que va a proveer, en este caso el Dao.
    @Singleton//Con la anotacion "@Singleton", se indica que es un "SingletonComponent", esta define que solo sera una sola instancia a través de toda la aplicación "(SingletonComponent::class)"
    fun provideDao(
        database: NoteDatabase//Esta funcion obtendra un valor de tipo "ToDoDatabase"
    ) = database.noteDao()

    /**RECUERDA QUE POR LO GENERAL LO QUE PROVEE VA UNA DESPUES DE LA OTRA, SE NECESITA LA DATABASE PARA EL DAO**/

}