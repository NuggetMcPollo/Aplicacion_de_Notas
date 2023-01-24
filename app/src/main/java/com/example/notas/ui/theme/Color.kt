package com.example.notas.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val LowPriority = Color(0xFF00D515)
val MediumPriority = Color(0xFFFFBC00)
val HighPriority = Color(0xFFFF0000)
val NonePriority = Color(0xD0979797)

val Mirage = Color(0xFF1D2335)
val MirageDark = Color(0xFF181D2D)
val Orange = Color(0xffff3b2f)
val MediumGray = Color(0x7E000000)


//Se crea una extencion de "Colors" dentro de una variable, y se accedera a esta extencion por el nombre de "BackgroundScreen"
val Colors.BackgroundScreen
    get() =
        if (isLight) Color.White//Si esta usando el tema de dia, el color de "BackgroundScreen", sera blanco
        else Mirage//De lo contrario sera...

//Se crea una extencion de "Colors" dentro de una variable, y se accedera a esta extencion por el nombre de "BackgroundScreen"
val Colors.BackgroundTextField
    get() =
        if (isLight) Color.White//Si esta usando el tema de dia, el color de "BackgroundScreen", sera blanco
        else MirageDark//De lo contrario sera...


//Se crea una extencion de "Colors" dentro de una variable, y se accedera a esta extencion por el nombre de "BackgroundScreen"
val Colors.BackgroundTextFieldLabel
    get() =
        if (isLight) MediumGray//Si esta usando el tema de dia, el color de "BackgroundScreen", sera blanco
        else Color.White//De lo contrario sera...

//Se crea una extencion de "Colors" dentro de una variable, y se accedera a esta extencion por el nombre de "BackgroundScreen"
val Colors.BackgroundAlertDialog
    get() =
        if (isLight) Color.White//Si esta usando el tema de dia, el color de "BackgroundScreen", sera blanco
        else MirageDark//De lo contrario sera...

//Se crea una extencion de "Colors" dentro de una variable, y se accedera a esta extencion por el nombre de "welcomeScreenBackgroundColor"
val Colors.ColorTextTheme
    get() =
        if (isLight) Color.Black//Si esta usando el tema de dia, el color de "welcomeScreenBackgroundColor", sera blanco
        else Color.White//De lo contrario sera...

//Se crea una extencion de "Colors" dentro de una variable, y se accedera a esta extencion por el nombre de "welcomeScreenBackgroundColor"
val Colors.ColorTextEmptyContent
    get() =
        if (isLight) Color.Black//Si esta usando el tema de dia, el color de "welcomeScreenBackgroundColor", sera blanco
        else Color.White//De lo contrario sera...

//Se crea una extencion de "Colors" dentro de una variable, y se accedera a esta extencion por el nombre de "welcomeScreenBackgroundColor"
val Colors.ItemDrawer
    get() =
        if (isLight) Color.Black//Si esta usando el tema de dia, el color de "welcomeScreenBackgroundColor", sera blanco
        else Color.White//De lo contrario sera...

//Se crea una extencion de "Colors" dentro de una variable, y se accedera a esta extencion por el nombre de "welcomeScreenBackgroundColor"
val Colors.ItemNoteBackground
    get() =
        if (isLight) Color.White//Si esta usando el tema de dia, el color de "welcomeScreenBackgroundColor", sera blanco
        else MirageDark//De lo contrario sera...

//Se crea una extencion de "Colors" dentro de una variable, y se accedera a esta extencion por el nombre de "welcomeScreenBackgroundColor"
val Colors.BackgroundFieldExisiting
    get() =
        if (isLight) Orange//Si esta usando el tema de dia, el color de "welcomeScreenBackgroundColor", sera blanco
        else Color.Black//De lo contrario sera...