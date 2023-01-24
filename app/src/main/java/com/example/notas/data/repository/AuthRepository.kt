package com.example.notas.data.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepository{

    val currentUser: FirebaseUser? = Firebase.auth.currentUser//Se crea una variable la cual guardara el usuario actual del momento, este sera de tipo "FirebaseUser?" y podra ser
    //de tipo nulo. A esta variable se le asignara el objeto "Firebase" la cual es [solo una variable en kotlin], se llama a "auth" y se obtendra el usuario actual "currentUser"
    //el cual; solo devolverá el usuario actual

    //Se crea una funcion la cual revisa si el usuario, esta logeado o no.
    fun hasUser(): Boolean = Firebase.auth.currentUser != null//Esta no recibira nada, pero devolvera un boleano. Para eso, preguntara si "Firebase.auth.currentUser" es diferente
    //de nulo, entonces; quiere decir que el usuario ha iniciado sesión. Y devolvera "True"

    //Se crea una funcion, la cual creara el usuario dentro de "Firebase", esta sera de tipo suspend, porque correra en el momento.
    suspend fun createUser(
        email:String,//Recibira un valor de tipo string, para el email
        password:String,//Recibira un valor de tipo string, para el password
        onComplete:(Boolean) ->Unit//Recibira una Unit, y devolvera un valor boleano
    ) = withContext(Dispatchers.IO){ //Se quiere cambiar de hilos entre el "Hilo principal" devido a que se esperara una respuesta con el firebase, para eso se usara "withContext(Dispatchers.IO)"
        Firebase.auth//Se quiere crear un nuevo usuario, para eso se llama al objeto "Firebase.auth" y se selecciona...
            .createUserWithEmailAndPassword(email, password)//"createUserWithEmailAndPassword" y se le pasa los valores recividos
            .addOnCompleteListener { result -> //Se agrega un "addOnCompleteListener"
                if (result.isSuccessful){//Y pregunta, si el registro del usuario del momento "result" fue exitoso
                    onComplete.invoke(true)//Se llama a la Unit "onComplete" [Declara arriba] y con "invoke" le mandamos que fue true
                }else{//De lo contrario
                    onComplete.invoke(false)//Se llama a la Unit "onComplete" [Declara arriba] y con "invoke" le mandamos que fue false
                }
            }.await()//Se llama a la funcion "await()" ya que no se quiere bloquear el hilo principal
    }

    //Se crea una funcion, la permitira al usuario logearse dentro de "Firebase", esta sera de tipo suspend, porque correra en el momento.
    suspend fun login(
        email:String,//Recibira un valor de tipo string, para el email
        password:String,//Recibira un valor de tipo string, para el password
        onComplete:(Boolean) ->Unit//Recibira una Unit, y devolvera un valor boleano
    ) = withContext(Dispatchers.IO){//Se quiere cambiar de hilos entre el "Hilo principal" al "Hilo Io", para eso se usara "withContext(Dispatchers.IO)"
        Firebase.auth//Se quiere crear un nuevo usuario, para eso se llama al objeto "Firebase.auth" y se selecciona...
            .signInWithEmailAndPassword(email, password)//"signInWithEmailAndPassword" y se le pasa los valores recividos
            .addOnCompleteListener { result -> //Se agrega un "addOnCompleteListener"
                if (result.isSuccessful){//Y pregunta, si el registro del usuario del momento "it" fue exitoso
                    onComplete.invoke(true)//Se llama a la Unit "onComplete" [Declara arriba] y con "invoke" le mandamos que fue true
                }else{//De lo contrario
                    onComplete.invoke(false)//Se llama a la Unit "onComplete" [Declara arriba] y con "invoke" le mandamos que fue false
                }
            }.await()//Se llama a la funcion "await()" ya que no se quiere bloquear el hilo principal
    }

    //Se crea una funcion que permita cerrar session
    fun signOut() = Firebase.auth.signOut()//El resultado de la funcion sera de la llamada de "Firebase.auth.signOut()"
}