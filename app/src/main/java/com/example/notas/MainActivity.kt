package com.example.notas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.notas.navegation.NavGraph
import com.example.notas.presentation.screen.home.details.DetailsViewModel
import com.example.notas.presentation.screen.home.notes.NotesViewModel
import com.example.notas.presentation.screen.sign.SignViewModel
import com.example.notas.ui.theme.MediumGray
import com.example.notas.ui.theme.Mirage
import com.example.notas.ui.theme.NotasTheme
import com.example.notas.ui.theme.Orange
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@AndroidEntryPoint//Con la anotacion "@AndroidEntryPoint" se indica que aqui se inyecta las dependencias. Si uno tiene muchas activitys, esta anotacion debe de ir en todas ellas.
// Y si tiene un broadcast resiver o un service tambien iria un "AndroidEntryPoint" esta anotacion iria basicamente en los componentes android
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController//Se crea una variable, la cual posera el NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val signViewModel by viewModels<SignViewModel>()
        val notesViewModel: NotesViewModel by viewModels()
        val detailsViewModel: DetailsViewModel by viewModels()

        setContent {
            NotasTheme {
                val systemUiController = rememberSystemUiController()//Se crea una variable que recuerde el ui del systema, HACER LA IMPORTACION
                if (isSystemInDarkTheme()) {// Quiere decir que el tema oscuro esta ENCENDIDO
                    systemUiController.setSystemBarsColor(
                        color = Orange
                    )
                    systemUiController.setNavigationBarColor(
                        color = Color.Black
                    )
                }else{// Quiere decir que el tema oscuro esta APAGADO
                    systemUiController.setSystemBarsColor(
                        color = Orange
                    )
                    systemUiController.setNavigationBarColor(
                        color = Color.White
                    )
                }
                navController = rememberAnimatedNavController() //Para navegar se necesita un controlador de navegacion la cual esta dentro de la funcion "rememberNavController()".
                // por eso mismo se le asigna a la variable que posee un "NavHostController"
                NavGraph(//Desde el main activity se esta llamando a la funcion composable "SetupNavigation", la cual posee las screens
                    navController = navController,//Se le manda el "navcontroller"
                    signViewModel = signViewModel,//Se le manda el "sharedViewModel"
                    notesViewModel = notesViewModel,
                    detailsViewModel = detailsViewModel
                )
            }
        }
    }
}