package com.example.notas.presentation.screen.splash

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.notas.presentation.screen.sign.SignViewModel
import com.example.notas.ui.theme.BackgroundScreen
import com.example.notas.ui.theme.ColorTextTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    signViewModel: SignViewModel,//La funcion recibira el viewmodel y se hace nulable, para poder tener acceso dentro a la vista y asi no pida el parametro
    onNavToHomePage:() -> Unit,//La funcion recibira una "unidad" para navegar a la pagina "Principal"
    onNavToSignInPage:() -> Unit,//La funcion recibira una "unidad" para navegar a la pagina del "Registro"
){

    val background = MaterialTheme.colors.BackgroundScreen//Se crea una variable que traera el background a usar
    val letterColor = MaterialTheme.colors.ColorTextTheme//Se crea una variable que traera el background a usar

    val userSignIn = signViewModel.hasUser//Se crea una variable que devuelve un true si esta logeado el usuario

    var startAnimation by remember { mutableStateOf(false) }//Se crea una variable para indicar el inicio de animacion

    val offsetStateText by animateDpAsState(//Se crea una variable para animar el estado de posicion
        targetValue = if (startAnimation) 0.dp else 100.dp,//Al parametro "targetValue - valor objetivo" se le asigna un bloque if y se dice si "startAnimation" es "true"
        // entonces se le asigna un valor de 0.dp de lo contrario 100.dp
        animationSpec = tween(
            durationMillis = 1000
        )
    )

    val offsetStateImage by animateDpAsState(//Se crea una variable para animar el estado de posicion
        targetValue = if (startAnimation) 0.dp else (-100).dp,//Al parametro "targetValue - valor objetivo" se le asigna un bloque if y se dice si "startAnimation" es "true"
        // entonces se le asigna un valor de 0.dp de lo contrario 100.dp
        animationSpec = tween(
            durationMillis = 1000
        )
    )

    val alphaState by animateFloatAsState(//Se crea una variable para animar el estado del desvanecimiento
        targetValue = if (startAnimation) 1f else 0f,//Al parametro "targetValue - valor objetivo" se le asigna un bloque if y se dice si "startAnimation" es true entonces se le asigna un valor de 1f de lo contrario 0f
        animationSpec = tween(
            durationMillis = 1000
        )
    )

    LaunchedEffect(key1 = true) {//Se llama a la funcion "LaunchedEffect" y se le asigna el valor de true, esto significa que para este lanzamiento el efecto activará el contenido.
        //Pero solo la primera vez que se llame al splash
        startAnimation = true//Se inicia la animacion
        delay(3000)//Se coloca un delay, para despues del tiempo
        navController.popBackStack()
        if (userSignIn) {
            onNavToHomePage.invoke()
        } else {
            onNavToSignInPage.invoke()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()//Se asigna que sea tamaño completo de la pantalla
            .background(background)//Se pasa la variable "background" para que esta cambie dependiendo el tema
    ){
        //TODO: BODY
        BodySplash(
            modifier = Modifier.align(Alignment.Center),
            offsetStateImage = offsetStateImage,
            offsetStateText = offsetStateText,
            alphaState = alphaState,
            letterColor = letterColor
        )
        //TODO: FOOTER
        FooterSplash(
            modifier = Modifier.align(Alignment.BottomEnd),
            offsetStateText = offsetStateText,
            alphaState = alphaState,
            letterColor = letterColor
        )
    }
}

/** BODY **/
@Composable
fun BodySplash(
    modifier: Modifier,
    offsetStateImage: Dp,
    offsetStateText: Dp,
    alphaState: Float,
    letterColor: Color
){
    Column(
        modifier = modifier
            .fillMaxWidth(),//Se asigna el ANCHO COMPLETO
    ){
        Image(
            modifier = Modifier
                .size(250.dp)//Se asigna un tamaño, el cual abarcara largo y ancho
                .offset(y = offsetStateImage)//Se coloca "y", porque se quiere animar de arriba hacia el centro
                .alpha(alpha = alphaState)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = com.example.notas.R.drawable.logo_app),//Se asigna el logo
            contentDescription = null
        )
        Spacer(
            modifier = Modifier
            .size(10.dp)//Se asigna un espacio
        )
        Text(
            modifier = Modifier
                .offset(y = offsetStateText)//Se coloca "y", porque se quiere animar de arriba hacia el centro
                .alpha(alpha = alphaState)
                .align(Alignment.CenterHorizontally),
            text = "By オサマ",
            color = letterColor
        )
    }
}

/** FOOTER **/
@Composable
fun FooterSplash(
    modifier: Modifier,
    offsetStateText: Dp,
    alphaState: Float,
    letterColor: Color
) {
    Text(
        modifier = modifier
            .offset(y = offsetStateText)//Se coloca "y", porque se quiere animar de arriba hacia el centro
            .padding(end = 15.dp, bottom = 5.dp)
            .alpha(alpha = alphaState),
        text = "From the collection Jr.",
        color = letterColor,
        fontSize = MaterialTheme.typography.body1.fontSize,//Se asigna el tamaño
        maxLines = 1//Se asigna el numero maximo de lineas sobre about (SON 7)
    )
}