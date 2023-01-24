package com.example.notas

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp//Con la anotacion "@HiltAndroidApp" indica que se usara Hilt en la aplicacion
/**OJO: RECUERDA AGREGAR EN EL MANIFEST Y SE INDICA EN DONDE SE QUIEREN INYECTAR LAS DEPENDENCIAS */
class MyApp: Application(){//Se crea una clase que herede de "Application()"
}