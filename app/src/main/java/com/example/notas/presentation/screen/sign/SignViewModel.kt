package com.example.notas.presentation.screen.sign

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notas.data.repository.AuthRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignViewModel (
    private val repository: AuthRepository = AuthRepository()
):ViewModel(){

    var state by mutableStateOf(SignState())//Se crea una variable la cual tendra un "mutableStateOf", pero en este caso de tipo "LoginState()" la cual es la clase que almacenadora de estados. Ya bien acomodados
        private set//Se agrega el "private set" para que la vista pueda acceda a los estados y NO LOS edite

    val hasUser: Boolean//Se crea una variable de tipo "Boolean", esta almecenara el resultado que devuelve la funcion "hasUser()", para eso "de diferente de nulo"; quiere decir que el usuario ha iniciado sesión. Y devolvera "True"
    get() = repository.hasUser()//Se llama al metodo "get" y se le asigna el valor de "repository.hasUser()"

    fun currentUser () =
        repository.currentUser?.email//Se crea una variable, a la cual se le asignara el usuario actual. Para eso entra a la variable "repository" dentro de "currentUser"

    //Se crean los eventos los cuales van a modificar los estados declarados en "state" dentro del Ui
    fun userChangeSignIn(userName: String) {//Se crea una funcion la cual recibira un string
        state = state.copy(emailSignIn = userName)//A la variable "state", se le cambiara el valor, asignandole el valor de "state.copy(userName = userName)"
    }

    fun passwordChangeSignIn(password: String) {
        state = state.copy(passwordSignIn = password)
    }

    fun userChangeSignup(userName: String) {
        state = state.copy(emailSignUp = userName)
    }

    fun passwordChangeSignup(password: String) {
        state = state.copy(passwordSignUp = password)
    }

    fun passwordConfirmChangeSignup(password: String) {
        state = state.copy(confirmPasswordSignUp = password)
    }

    fun onLoginChangedEnable(email: String, password: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 6
        /**
         *  1. Android provee la verificacion con el metodo "Patterns", se especifica que sea verificacion de "EMAIL_ADDRESS" con la variable que se le manda (email) y con .matcher se ejecuta.
         *                                                  &&
         *  2. Se especifica que el password tenga una logitud mayor de 6 para que se active**/
    }

    fun onPasswordAndPasswordConfirm(password: String, passwordConfirm: String): Boolean {
        return password == passwordConfirm
    }

    //Se crea una funcion la cual logeara al usuario
    fun loginUser(
        context: Context// Recibira un "Context", porque se quiere desplegar un toast message y para eso sirvira el contexto, a su vez....
    ) = viewModelScope.launch {//La funcion regresa el resultado dentro del "viewModelScope.launch"...
        try {//Se utiliza un bloque "try", ya que se quiere primero capturar los primeros errores, si los formularios son invalidos
            state = state.copy(isLoading = true)//El estado de "isLoading" se pasa a true, para mostar un progress
            state = state.copy(loginError = null)//El estado de "loginError" se pasa a loginError en null, no hay error
            repository.login(state.emailSignIn, state.passwordSignIn) {
                isSuccessful -> //Se renombra la lambda, la cual tendra el resultado de la funcion "createUser"
                    if (isSuccessful) {
                        Toast.makeText(
                            context,
                        "Login Exito",
                            Toast.LENGTH_SHORT
                        ).show()
                        state = state.copy(isSuccessSignIn = true)
                    } else {
                        Toast.makeText(
                            context,
                        "Login Fallido",
                            Toast.LENGTH_SHORT
                        ).show()
                        state = state.copy(isSuccessSignIn = false)
                    }
            }
        } catch (e: Exception) {//De haberse capturado un error, se manda el error
            state = state.copy(signUpError = e.localizedMessage)//El estado de "signUpError" se pasa a el error "(signUpError = e.localizedMessage)"
            e.printStackTrace()//Se imprime el error dentro del logcat
        } finally {//Se llama al metodo "finally", su funcion principal sera cambiar los estados finales del try y permitira desactivara el progress bar, despues de que ya se mostro todo lo que se debia de mostrar
            state = state.copy(isLoading = false)//El estado de "isLoading" se pasa a false
        }
    }

    //Se crea una funcion la cual creara al usuario, esta recibira un "Context", porque se quiere desplegar un toast message y para eso sirvira el contexto, a su vez....
    fun createUser(
        context: Context
    ) = viewModelScope.launch {//La funcion regresa el resultado dentro del "viewModelScope.launch"...
        try {//Se utiliza un bloque "try", ya que se quiere primero capturar los primeros errores, si los formularios son invalidos
            state = state.copy(isLoading = true)//El estado de "isLoading" se pasa a true
            state = state.copy(signUpError = null)//El estado de "signUpError" seguira en null
            repository.createUser(state.emailSignUp, state.passwordSignUp){//De haber pasado los procesos anteriores, se llamara a la funcion que creara al usuario
                isSuccessful -> //Se renombra la lambda, la cual tendra el resultado de la funcion "createUser"
                    if (isSuccessful) {//Se pregunta, si "isSuccessful" es true [Quiere decir que se creo bien el usuario]
                        Toast.makeText(//Se llama al toast
                            context,//Se manda el contexto
                        "Registro exitoso",//Se manda el mensaje a mostrar
                            Toast.LENGTH_SHORT//Se muestra la duracion
                        ).show()
                    state = state.copy(isSuccessSignIn = true)//El estado de "isSuccessLogin" se pasa a true
                    } else {//De lo contrario, "isSuccessful" es false [Quiere decir que se NO se creo bien el usuario]
                        Toast.makeText(//Se llama al toast
                            context,//Se manda el contexto
                        "Registro Fallido",//Se manda el mensaje a mostrar
                            Toast.LENGTH_SHORT//Se muestra la duracion
                        ).show()
                    state = state.copy(isSuccessSignIn = false)//El estado de "isSuccessLogin" se pasa a false
                }
            }
        } catch (e: Exception) {//De haberse capturado otro error, se manda el error
            state = state.copy(signUpError = e.localizedMessage)//El estado de "signUpError" se pasa a el error "(signUpError = e.localizedMessage)"
            e.printStackTrace()//Se imprime el error dentro del logcat
        } finally {//Se llama al metodo "finally", su funcion principal sera cambiar los estados finales del try y permitira desactivara el progress bar, despues de que ya se mostro todo lo que se debia de mostrar
            state = state.copy(isLoading = false)//El estado de "isLoading" se pasa a false
        }
    }

    //Se crea una funcion que permita cerrar session
    fun signOut() = Firebase.auth.signOut()//El resultado de la funcion sera de la llamada de "Firebase.auth.signOut()"
}