package com.example.notas.navegation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.notas.presentation.screen.home.NoteAction
import com.example.notas.presentation.screen.home.details.DetailsScreen
import com.example.notas.presentation.screen.home.details.DetailsViewModel
import com.example.notas.presentation.screen.home.notes.NotesScreen
import com.example.notas.presentation.screen.home.notes.NotesViewModel
import com.example.notas.presentation.screen.home.toAction
import com.example.notas.presentation.screen.sign.SignInScreen
import com.example.notas.presentation.screen.sign.SignUpScreen
import com.example.notas.presentation.screen.sign.SignViewModel
import com.example.notas.presentation.screen.splash.SplashScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalAnimationApi
@Composable
fun NavGraph (
    navController: NavHostController,
    signViewModel: SignViewModel,//Tomara de parametro un valor de tipo "SharedViewModel", para poder acceder a los valores que se guardan dentro de el
    notesViewModel: NotesViewModel,//Tomara de parametro un valor de tipo "SharedViewModel", para poder acceder a los valores que se guardan dentro de el
    detailsViewModel: DetailsViewModel//Tomara de parametro un valor de tipo "SharedViewModel", para poder acceder a los valores que se guardan dentro de el
){

    //Con esta variable se hara un seguimiento de todas las funciones "composables" en toda nuestra aplicación, por eso mismo es remember
    val screen = remember(navController) {//Se le asigna el "navController" al remember, porque se quiere usar ese mismo "navController", dentro de la clase screen
        ScreensHome(navController = navController)
    }

    AnimatedNavHost(//Se esta llamando al metodo "NavHost" y dentro de este se le asinga...
        navController = navController,//Al parametro "navController" se le asigna la variable "navController" la cual posee el controlador de navegacion
        startDestination = Screen.Splash.route//Al parametro "startDestination" se le asigna la pantalla con la iniciara la aplicacion.
    ){//Para el cuerpo del "NavHost", se difinen dentro todos los destinos
        composable(
            route = Screen.Splash.route,
            exitTransition = {//Se agrega el parametro "exitTransition", para indicar como saldra. OSEA COMO PASARA DEL SPLASH AL LIST
                //Algo a tener en cuenta es que cada uno de los activos de transición tienen lambdas diferentes y cada una de esas Lambdas es una entrada de dónde vienes y hacia dónde te diriges.
                slideOutVertically(//Se va a usar un "slideOutVertically - Deslizar hacia fuera verticalmente"
                    targetOffsetY = {//Se puede renombrar la lambda como "initial - inicio", "target - objetivo" ->
                        //De modo que puedas escibir -------- if(target.destination.route == "list/{action}") -----{
                        // Preguntando si el "target.dest......" es igual a "list/{action}" la parte para dirigirse a la lista, entonces se puede aplicar cierta animacion de igual modo como else{
                        // }
                        //tambien se puede usar escibir -------- if(initial.destination.route == "list/{action}") -----{
                        //Preguntando si el destino inicial del que venimos es "list/{action}" entonces se aplica cierta animacion
                            fullHeight -> -fullHeight//Primero se renombra la lambda y despues se pone "-fullHeight - altura completa = -it"
                        /** La razón por la que no se deslizó completamente hacia arriba en la parte superior es porque este deslizamiento vertical contiene este desplazamiento de destino **/
                        /** La razón por la que no se deslizó completamente hacia arriba en la parte superior es porque este deslizamiento vertical contiene este desplazamiento de destino **/
                    },
                    animationSpec = tween(//Al parametro "animationSpec" se le asigna un twenn
                        durationMillis = 300//Se aplican 300 segundos
                    )
                )
            }
        ){
            SplashScreen(
                navController = navController,
                signViewModel = signViewModel,
                onNavToHomePage = {//Se llama a la screen "LoginScreen" y se le asigna al parametro "onNavToHomePage" la cual es la "unidad" para navegar a la pagina "Principal"
                    navController.navigate(route = "home_screen/${NoteAction.NOTHING}") {//Se manda el navegador con la ruta "NestedRoutes.Main.name"
                        launchSingleTop = true//Con "launchSingleTop" indicamos que solo se cree una sola instancia de esto dentro de la pila de respaldo y no multiples
                        popUpTo(route = Screen.SignIn.route) {//Si se preciona el boton de atras se quiere eliminar de la pila de resplado la ruta "LoginRoutes.SignIn.name"
                            inclusive = true//Para eso se pone en true "inclusive"
                        }
                    }
                },
                /*
                onNavToSignUpPage = {
                        navController.navigate(LoginRoutes.SignIn.name)
                }
                 */
            ){//Cuando el usuario presione atras, se quiere regresar a la screen SignIn
                navController.navigate(Screen.SignIn.route){
                    launchSingleTop = true//Con "launchSingleTop" indicamos que solo se cree una sola instancia de esto dentro de la pila de respaldo y no multiples
                    popUpTo(route = Screen.Splash.route) {//Si se preciona el boton de atras se quiere eliminar de la pila de resplado la ruta "LoginRoutes.SignIn.name"
                        inclusive = true//Para eso se pone en true "inclusive"
                    }
                }
            }
        }

        composable(
            route =  Screen.SignIn.route,
            exitTransition = {//Se agrega el parametro "exitTransition", para indicar como saldra. OSEA COMO PASARA DEL SPLASH AL LIST
                //Algo a tener en cuenta es que cada uno de los activos de transición tienen lambdas diferentes y cada una de esas Lambdas es una entrada de dónde vienes y hacia dónde te diriges.
                slideOutVertically(//Se va a usar un "slideOutVertically - Deslizar hacia fuera verticalmente"
                    targetOffsetY = {//Se puede renombrar la lambda como "initial - inicio", "target - objetivo" ->
                        //De modo que puedas escibir -------- if(target.destination.route == "list/{action}") -----{
                        // Preguntando si el "target.dest......" es igual a "list/{action}" la parte para dirigirse a la lista, entonces se puede aplicar cierta animacion de igual modo como else{
                        // }
                        //tambien se puede usar escibir -------- if(initial.destination.route == "list/{action}") -----{
                        //Preguntando si el destino inicial del que venimos es "list/{action}" entonces se aplica cierta animacion
                            fullHeight -> -fullHeight//Primero se renombra la lambda y despues se pone "-fullHeight - altura completa = -it"
                        /** La razón por la que no se deslizó completamente hacia arriba en la parte superior es porque este deslizamiento vertical contiene este desplazamiento de destino **/
                        /** La razón por la que no se deslizó completamente hacia arriba en la parte superior es porque este deslizamiento vertical contiene este desplazamiento de destino **/
                    },
                    animationSpec = tween(//Al parametro "animationSpec" se le asigna un twenn
                        durationMillis = 300//Se aplican 300 segundos
                    )
                )
            },
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = {
                            fullWidth -> -fullWidth//Primero se renombra la lambda y despues se pone "-fullWidth - anchura completa = -it"
                    },
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
            }
        ){
            SignInScreen(
                signInViewModel = signViewModel,
                onNavToHomePage = {//Se llama a la screen "LoginScreen" y se le asigna al parametro "onNavToHomePage" la cual es la "unidad" para navegar a la pagina "Principal"
                    navController.navigate(route = "home_screen/${NoteAction.NOTHING}") {//Se manda el navegador con la ruta "NestedRoutes.Main.name"
                        launchSingleTop = true//Con "launchSingleTop" indicamos que solo se cree una sola instancia de esto dentro de la pila de respaldo y no multiples
                        popUpTo(route = Screen.SignIn.route) {//Si se preciona el boton de atras se quiere eliminar de la pila de resplado la ruta "LoginRoutes.SignIn.name"
                            inclusive = true//Para eso se pone en true "inclusive"
                        }
                    }
                },
                /*
                onNavToSignUpPage = {
                        navController.navigate(LoginRoutes.SignIn.name)
                }
                 */
            ){//Cuando el usuario presione atras, se quiere regresar a la screen SignIn
                navController.navigate(Screen.SignUp.route){
                    launchSingleTop = true//Con "launchSingleTop" indicamos que solo se cree una sola instancia de esto dentro de la pila de respaldo y no multiples
                    popUpTo(route = Screen.SignUp.route) {//Si se preciona el boton de atras se quiere eliminar de la pila de resplado la ruta "LoginRoutes.SignIn.name"
                        inclusive = true//Para eso se pone en true "inclusive"
                    }
                }
            }
        }

        composable(
            route = Screen.SignUp.route,
            exitTransition = {//Se agrega el parametro "exitTransition", para indicar como saldra. OSEA COMO PASARA DEL SPLASH AL LIST
                //Algo a tener en cuenta es que cada uno de los activos de transición tienen lambdas diferentes y cada una de esas Lambdas es una entrada de dónde vienes y hacia dónde te diriges.
                slideOutVertically(//Se va a usar un "slideOutVertically - Deslizar hacia fuera verticalmente"
                    targetOffsetY = {//Se puede renombrar la lambda como "initial - inicio", "target - objetivo" ->
                        //De modo que puedas escibir -------- if(target.destination.route == "list/{action}") -----{
                        // Preguntando si el "target.dest......" es igual a "list/{action}" la parte para dirigirse a la lista, entonces se puede aplicar cierta animacion de igual modo como else{
                        // }
                        //tambien se puede usar escibir -------- if(initial.destination.route == "list/{action}") -----{
                        //Preguntando si el destino inicial del que venimos es "list/{action}" entonces se aplica cierta animacion
                            fullHeight -> -fullHeight//Primero se renombra la lambda y despues se pone "-fullHeight - altura completa = -it"
                        /** La razón por la que no se deslizó completamente hacia arriba en la parte superior es porque este deslizamiento vertical contiene este desplazamiento de destino **/
                        /** La razón por la que no se deslizó completamente hacia arriba en la parte superior es porque este deslizamiento vertical contiene este desplazamiento de destino **/
                    },
                    animationSpec = tween(//Al parametro "animationSpec" se le asigna un twenn
                        durationMillis = 300//Se aplican 300 segundos
                    )
                )
            },
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = {
                            fullWidth -> -fullWidth//Primero se renombra la lambda y despues se pone "-fullWidth - anchura completa = -it"
                    },
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
            }
        ){
            SignUpScreen(
                signUpViewModel = signViewModel,
                onNavToHomePage = {//Se llama a la screen "SignUpScreen" y se le asigna al parametro "onNavToHomePage" la cual es la "unidad" para navegar a la pagina "Principal"
                    navController.navigate(route = "home_screen/${NoteAction.NOTHING}") {//Se manda el navegador con la ruta "HomeRoutes.Home.name"
                        popUpTo(route = Screen.SignUp.route) {//Si se preciona el boton de atras se quiere eliminar de la pila de resplado la ruta "LoginRoutes.Signup.name"
                            inclusive = true//Para eso se pone en true "inclusive"
                        }
                    }
                },
                /*
                onNavToSignUpPage ={
                    navController.navigate(Screen.SignIn.route)
                }
                */
            ){//Cuando el usuario presione atras, se quiere regresar a la screen SignIn
                navController.navigate(Screen.SignIn.route)
            }
        }

        composable(
            route = Screen.Notes.route,
            arguments = listOf(
                navArgument("action"){//Se llama al atributo "navArgument" y se manda la clave, con la que se envia
                    type = NavType.StringType//Se asigna el tipo de dato que se quiere recibir
                }
            ),
            exitTransition = {//Se agrega el parametro "exitTransition", para indicar como saldra. OSEA COMO PASARA DEL SPLASH AL LIST
                //Algo a tener en cuenta es que cada uno de los activos de transición tienen lambdas diferentes y cada una de esas Lambdas es una entrada de dónde vienes y hacia dónde te diriges.
                slideOutVertically(//Se va a usar un "slideOutVertically - Deslizar hacia fuera verticalmente"
                    targetOffsetY = {//Se puede renombrar la lambda como "initial - inicio", "target - objetivo" ->
                        //De modo que puedas escibir -------- if(target.destination.route == "list/{action}") -----{
                        // Preguntando si el "target.dest......" es igual a "list/{action}" la parte para dirigirse a la lista, entonces se puede aplicar cierta animacion de igual modo como else{
                        // }
                        //tambien se puede usar escibir -------- if(initial.destination.route == "list/{action}") -----{
                        //Preguntando si el destino inicial del que venimos es "list/{action}" entonces se aplica cierta animacion
                            fullHeight -> -fullHeight//Primero se renombra la lambda y despues se pone "-fullHeight - altura completa = -it"
                        /** La razón por la que no se deslizó completamente hacia arriba en la parte superior es porque este deslizamiento vertical contiene este desplazamiento de destino **/
                        /** La razón por la que no se deslizó completamente hacia arriba en la parte superior es porque este deslizamiento vertical contiene este desplazamiento de destino **/
                    },
                    animationSpec = tween(//Al parametro "animationSpec" se le asigna un twenn
                        durationMillis = 300//Se aplican 300 segundos
                    )
                )
            },
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = {
                            fullWidth -> -fullWidth//Primero se renombra la lambda y despues se pone "-fullWidth - anchura completa = -it"
                    },
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
            }
        ){ navBackStackEntry ->//Se renombre la lambda

            val action = navBackStackEntry.arguments?.getString("action").toAction()

            var myAction by rememberSaveable { mutableStateOf(NoteAction.NOTHING) }//Se crea una variable de tipo "rememberSaveable", para soportar la recomposicion por orientacion y esta tendra un valor por defecto de "Action.NO_ACTION"

            //Se llama al metodo "LaunchedEffect", y una vez que se cumpla la condicion se pueda recomponer. Y eso es cuando la "myAction" cambie o sea enviada
            LaunchedEffect(key1 = myAction){
                if (action != myAction){//Se cambia y se pregunta, si lo que tiene "action" es diferente a lo que tiene "myAction". Ya que esta conservara un valor aun con la recomposicion. Si son diferentes
                    myAction = action//Se le asigna el valor que tiene "action" a "myAction" y entonces...
                    //sharedViewModel.action = action//Una vez que la accion cambie, se llamara a la variable "action.value" ubicada dentro del sharedviewmodel y se le asignara lo que tiene la accion realizada.
                    notesViewModel.onChangeNoteAction(noteAction = action)
                }
            }

            NotesScreen(
                notesViewModel = notesViewModel,
                signViewModel = signViewModel,
                navigateToNotesScreen = screen.details,
                noteAction = myAction,
                navToLoginPage = {
                    navController.navigate(Screen.SignIn.route){
                        launchSingleTop = true
                        popUpTo(0){//Y como no se quiere incluir nada a la pila se mandara un 0 a la pila dejandola limpia
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument("noteId"){//Se llama al atributo "navArgument" y se manda la clave, con la que se envia
                    type = NavType.IntType//Se asigna el tipo de dato que se quiere mandar
                }
            ),
            exitTransition = {//Se agrega el parametro "exitTransition", para indicar como saldra. OSEA COMO PASARA DEL SPLASH AL LIST
                //Algo a tener en cuenta es que cada uno de los activos de transición tienen lambdas diferentes y cada una de esas Lambdas es una entrada de dónde vienes y hacia dónde te diriges.
                slideOutVertically(//Se va a usar un "slideOutVertically - Deslizar hacia fuera verticalmente"
                    targetOffsetY = {//Se puede renombrar la lambda como "initial - inicio", "target - objetivo" ->
                        //De modo que puedas escibir -------- if(target.destination.route == "list/{action}") -----{
                        // Preguntando si el "target.dest......" es igual a "list/{action}" la parte para dirigirse a la lista, entonces se puede aplicar cierta animacion de igual modo como else{
                        // }
                        //tambien se puede usar escibir -------- if(initial.destination.route == "list/{action}") -----{
                        //Preguntando si el destino inicial del que venimos es "list/{action}" entonces se aplica cierta animacion
                            fullHeight -> -fullHeight//Primero se renombra la lambda y despues se pone "-fullHeight - altura completa = -it"
                        /** La razón por la que no se deslizó completamente hacia arriba en la parte superior es porque este deslizamiento vertical contiene este desplazamiento de destino **/
                    },
                    animationSpec = tween(//Al parametro "animationSpec" se le asigna un twenn
                        durationMillis = 300//Se aplican 300 segundos
                    )
                )
            },
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = {
                            fullWidth -> -fullWidth//Primero se renombra la lambda y despues se pone "-fullWidth - anchura completa = -it"
                    },
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
            }
        ){ navBackStackEntry ->
            val noteId = navBackStackEntry.arguments!!.getInt("noteId")//Se crea una variable, la cual almacenara el ID de la tarea y este lo obtendra el "arguments" desde la listComposable y llega como un INT porque
            //se esta pasando un valor entero desde la list screen "composable" a task screen "composable". Y para la llave, se necesita pasar el argumento de la llave, la cual es una constante "TASK_ARGUMENT_KEY"
            //Log.d("TasComposable", taskId.toString())//Se coloca para ver que manda

            //Se tiene la funcion "getSelectedTask" la cual esta siendo llamada desde el viewmodel directamente desde aqui. Y eso, NO SE DEBER HACER, no se deben llamara a funciones que esten engañando al
            // estado de nuestra aplicación, directamente desde funciones composable, en cambio estas deben ser llamadas dentro de un "LaunchedEffect"
            LaunchedEffect(key1 = noteId){//Dentro del "LaunchedEffect" esta cambiara cada que el "ID" cambie
                detailsViewModel.getSelectedTask(noteId = noteId)//Se llamada desde la variable "sharedViewModel" a la funcion "getSelectedTask" y se le manda el ID de la tarea, el cual se recibio desde la list-screen
                /** De este modo cuando se precione una de las tareas o incluso el boton flotante desde la list-screen, se va a pasar esa ID de la tarea a la task-composable y despues desde ahi se va a usar ese ID que se solicito
                 * de ToDoTask a la base de datos
                 */
            }

            val selectNote by detailsViewModel.selectedNotes.collectAsState()

            LaunchedEffect(key1 = selectNote){
                if (selectNote != null || noteId == -1){
                    detailsViewModel.noteBlankOrUpdateDetails(selectNote = selectNote)
                }
            }
            DetailsScreen(
                selectedNote = selectNote,
                navigateToNoteScreen = screen.note,
                detailsViewModel = detailsViewModel
            )
        }
    }
}