package com.example.notas.util

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object Constants {

    const val Note_Table = "note_table"
    const val Note_Database = "note_database"

    /** El uso de esta variable es para proporcionar colores peronalizados dentro de los TextField**/
    val customTextSelectionColors = TextSelectionColors(
        handleColor = Color.White,//Se asigna el color del indicador del texto
        backgroundColor = Color.White.copy(alpha = 0.4f)//Se asigna el fondo del texto que se selecciono y se agrega un poco mas transparente
    )

    /** El uso de esta variable es para proporcionar un padding global concreto a las screen y sus compomentes no esten pegados completamente a las orillas**/
    val paddingScreen = 15.dp


    const val PREFERENCE_NAME = "todo_preferences"
    const val PREFERENCE_KEY = "sort_state"

}