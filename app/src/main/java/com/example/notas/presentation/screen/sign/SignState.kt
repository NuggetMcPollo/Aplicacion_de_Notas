package com.example.notas.presentation.screen.sign

data class SignState (
    val emailSignIn: String = "",//Se crea una variable, para el usuario cuando se va a logear; esta por defecto tendra un valor vacio
    val passwordSignIn: String = "",//Se crea una variable, para la contreseña cuando se va a logear; esta por defecto tendra un valor vacio

    val emailSignUp: String = "",//Se crea una variable, para el usuario cuando se va a registrar; esta por defecto tendra un valor vacio
    val passwordSignUp: String = "",//Se crea una variable, para la contreseña cuando se va a registrar; esta por defecto tendra un valor vacio
    val confirmPasswordSignUp: String = "",//Se crea una variable, para confirmar la contreseña cuando se va a registrar; esta por defecto tendra un valor vacio

    val isLoading: Boolean = false,//Se crea una variable, que servira de estado cuando carga la pantalla mientras espera una respuesta; esta por defecto tendra un valor false
    val isSuccessSignIn: Boolean = false,//Se crea una variable, que servira para verififcar si el login fue exitoso; esta por defecto tendra un valor false

    val signUpError: String? = null,//Se crea una variable, que servira para verififcar si el registro fue fallido; esta por defecto tendra un valor nulo
    val loginError: String? = null,//Se crea una variable, que servira para verififcar si el login fue fallido; esta por defecto tendra un valor nulo


)