package com.example.notas.navegation

//sealed class = Permite agrupar varias class u "ojects (SIEMPRE INICIAN EN MAYUSCULAS)"
/** En esta clase se declaran el nombre de las pantallas, las cuales se llamaran para poder navegar en el "navigationController". De este modo se evita que se pueda escribir mal el nombre al momento de llamarlas manualmente **/
sealed class Screen (val route: String) {//Recibe de parametro una variable de tipo String, de este modo cuando los objetos extiendan de "routes_login" necesitan poner el nombre de la pantalla. El cual sera el nombre de la pantalla
    /** Splash**/
    object Splash : Screen("splash_screen")
    /** SignIn - SignUp**/
    object SignIn : Screen("signIn_screen")
    object SignUp : Screen("signUp_screen")
    /** Notes - Details **/
    object Notes : Screen("home_screen/{action}")
    object Details : Screen("details_screen/{noteId}")

    /*
    /** Details: Se declara el object de la screen "details_screen" y se le asigna el nombre que tendra la pantalla como Key, pero en este caso recibira un parametro el cual servira para poder acceder a la proxima pantalla **/
    object Details : Screen("details_screen/{heroId}") {
        fun passHeroId(heroId: Int): String {//Se crea una funcion dentro del object, esta recibe un parametro el cual sera el "ID" del heroe y devolvera un string. Y la funcion retornara la un ruta ACTUALIZADA
            return "details_screen/$heroId"//El string que devolvera, sera la ruta ACTUALIZADA con el parametro que recibio
        }
    }

     */
}