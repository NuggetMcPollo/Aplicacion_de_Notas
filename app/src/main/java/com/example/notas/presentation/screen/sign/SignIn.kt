package com.example.notas.presentation.screen.sign

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notas.R
import com.example.notas.util.Constants.customTextSelectionColors
import com.example.notas.util.Constants.paddingScreen
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.notas.ui.theme.*

//Funcion que mostrara la pantalla para el login
@Composable
fun SignInScreen(
    signInViewModel: SignViewModel,//La funcion recibira el viewmodel y se hace nulable, para poder tener acceso dentro a la vista y asi no pida el parametro
    onNavToHomePage:() -> Unit,//La funcion recibira una "unidad" para navegar a la pagina "Principal"
    onNavToSignUpPage:() -> Unit,//La funcion recibira una "unidad" para navegar a la pagina del "Registro"
) {
    val signInUiState = signInViewModel.state//Se crea una variable que trera OJO: LOS ESTADOS DEL VIEWMODE "DATACLASS" a la UI
    val isError = signInUiState.loginError != null//Se crea una variable que almacenara el estado del LoginError, la cual se agarra de la variable "loginUiState" el cual posee el ViewModel en el parametro de "loginError"
    //Esto con el fin de verificar si hay un error

    val context = LocalContext.current//Se crea una variable la cual almecenara el contexto actual
    val drawable = R.drawable.wave //Wave asingado para esta pantalla
    //val title = "¿Ya tienes una cuenta?" //Se crea una variable que envia el titulo usado en este screen
    val title = stringResource(R.string.Title_SignIn) //Se crea una variable que envia el titulo usado en este screen
    val lottie = R.raw.login

    Box(
        modifier = Modifier
            .fillMaxSize()//Se asigna que sea tamaño completo de la pantalla
            .background(color = MaterialTheme.colors.BackgroundScreen)//Se llama al atributo "colors" y dentro de este se llama a "MaterialTheme" y se accede a los archivos declarados
        //Se le asigna un background a la PANTALLA COMPLETA
    ){
        if (isError){//Se pregunta, si hay un error "isError"
            Text(//Se coloca un texto
                text = signInUiState.loginError ?: "Error Desconocido",//Se muestra el error y en caso de ser nulo, se muestra por defecto "unknown error"
                color = Color.Red,//Se asigna el color
            )
        }
        //TODO: HEADER
        HeaderSignIn(
            modifier = Modifier
                .align(Alignment.TopCenter),
            drawable = drawable,
            title = title,
            lottie = lottie
        )
        //TODO: BODY
        BodySignIn(
            modifier = Modifier
                .align(Alignment.Center),
            signInUiState = signInUiState,
            singInViewModel = signInViewModel,
            context = context,
            onNavToHomePage = onNavToHomePage,
            onNavToSignUpPage = onNavToSignUpPage
        )
    }
}

/** HEADER **/

@Composable
fun HeaderSignIn(
    modifier: Modifier,//Recibe un "Modifier", en caso de querer modificar los componentes declarados dentro de la funcion
    @DrawableRes drawable: Int,//Recibe una variable de tipo Int, para colocar la parte del Wave. TODO: Para asegurarse de que sea Drawable se asigna el "@DrawableRes"
    title: String,
    @RawRes lottie: Int
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Spacer(
            modifier = Modifier
                .background(Orange)
                .fillMaxWidth()
                .size(50.dp)
        )
        RowLottie(
            title = title,
            lottie = lottie
        )
        Spacer(
            modifier = Modifier
                .background(Orange)
                .fillMaxWidth()
                .size(10.dp)
        )
        WaveSignIn(
            modifier = Modifier,//Se le asigna una modificacion de alineacion, pero del ultimo layout "Box"
            drawable = drawable//Se asigna el wave, correspondiente de la screen
        )
    }
}

@Composable
fun RowLottie(
    title: String,
    @RawRes lottie: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Orange),
        horizontalArrangement = Arrangement.SpaceAround,//Se pone el contenido a los extremos con un espacio
        verticalAlignment = Alignment.CenterVertically//Se centra el contenido DENTRO del Row
    ){//Se asigna una fila, declarando que ocupe el ancho completo y se centre
        Text(
            text = title,//Se le asinga el titulo que se manda para la pantalla correspondiente
            fontSize = 25.sp, //Se le asigna el tamaño del titulo
            fontWeight = FontWeight.SemiBold, //Se le asigna el formato del titulo
            color = Color.White, //Se le asigna el color para el titulo
        )
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(resId = lottie))
        LottieAnimation(
            modifier = Modifier
                .size(80.dp),
            speed = 1.5f,
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
    }
}

@Composable
fun WaveSignIn(
    modifier: Modifier,
    @DrawableRes drawable: Int,
) {
    Image(
        painter = painterResource(id = drawable),//Se le asinga el wave que se manda para la pantalla correspondiente
        contentDescription = "Wave top",//Se agrega la modificacion por defecto
        modifier = modifier//Se asigna el modificador predeterminado
    )
}

/** BODY **/

@Composable
fun BodySignIn(
    modifier: Modifier,
    signInUiState: SignState,
    singInViewModel: SignViewModel,
    context: Context,
    onNavToHomePage:() -> Unit,//La funcion recibira una "unidad" para navegar a la pagina "Principal"
    onNavToSignUpPage:() -> Unit,//La funcion recibira una "unidad" para navegar a la pagina del "Registro"
) {
    Column(
        modifier = modifier
            .padding(paddingScreen)
            .fillMaxWidth()//Se asigna que abarque EL LARGO COMPLETAMENTE
    ){
        Spacer(
            modifier = Modifier.size(225.dp)
        )
        EmailSignIn(
            signInUiState = signInUiState,
            singInViewModel = singInViewModel
        )
        Spacer(
            modifier = Modifier.size(30.dp)
        )
        PasswordSignIn(
            signInUiState = signInUiState,
            singInViewModel = singInViewModel
        )
        Spacer(
            modifier = Modifier.size(30.dp)
        )
        ButtonSignIn(
            signInUiState = signInUiState,
            singInViewModel = singInViewModel,
            context = context
        )
        Spacer(
            modifier = Modifier.size(50.dp)
        )
        LetterSignUp(
            onNavToSignUpPage = onNavToSignUpPage
        )
        if (signInUiState.isLoading){
            CircularProgressIndicator()
        }
        LaunchedEffect(key1 = singInViewModel.hasUser){//Se utiliza un "singInViewModel", la cual solo re recompondra una vez y eso es cuando el usuario se logee con exito, lo cual navegara a la pagina "HOME"
            if (singInViewModel.hasUser){//Se pregunta si "singInViewModel.hasUser" es igual a true
                onNavToHomePage.invoke() //Se llama a la lambda "onNavToSignUpPage" con el metodo ".invoke()" la cual permitira navegar a la pagina de "Home"
            }
        }
    }
}

@Composable
fun EmailSignIn(
    signInUiState: SignState,
    singInViewModel: SignViewModel
) {
    Column{//OJO, es importante meterlo dentro de algo, porque por si solo ocupa la pantalla completa al ser el unico componente. SI ES QUE SOLO SE DECLARA ESTE
        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {//La seleccion de colores utilizados para el background de la selección de texto por texto y los sus componentes los proporciona "LocalTextSelectionColors.current.", como se quiere personalizar, se usan los que estan declarados dentro de la variable "customTextSelectionColors"
            OutlinedTextField(//Se indica que sera de tipo "OutlinedTextField", OJO: Lo demas es igual a los "TextFields"
                value = signInUiState.emailSignIn,//Se asinga al valor, lo que tiene la variable "loginUiState" en el apartado de "userName" y en caso de ser nulo. Tendra un valor por defecto de ""
                onValueChange = {//Se le asigna al valor cambiante, lo que tiene...
                    singInViewModel.userChangeSignIn(it)//La variable "singInViewModel" en la funcion "userChangeSignIn" y se le manda el valor que esta cambiando "it"
                },
                maxLines = 1,//Se indica que solo pueda ocupar una linea
                singleLine = true,//Se indica que en lugar de precionar enter de un salto de linea. Se acepte lo que escribio
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),//Se indicia que de este modo que se muestre un teclado que provea de teclas para colocar un email
                shape = RoundedCornerShape(20.dp),//Se asigna bordes que redonde el "OutlinedTextField"
                modifier = Modifier
                    .fillMaxWidth() //Se le asinga el parametro "fillMaxWidth", el cual hara que ocupe el ancho completo de lo que esta declarado
                    .padding(horizontal = 20.dp),//Se asigna un padding horizontal para que estan aun mas corto
                leadingIcon = {//Al atributo "leadingIcon", permite asignar el icono inicial dentro del OutlinedTextField
                    Icon(
                        imageVector = Icons.Filled.Email,//Se selecciona el icono de google
                        contentDescription = "Email", //Se le asigna la descripcion por defecto
                        tint = Orange
                    )
                },
                label = {//Se coloca un label, para indicarle al usuario que se pone algo dentro
                    Text(
                        text = "Email",
                        color = MaterialTheme.colors.BackgroundTextFieldLabel
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(//Se indica los colores al momento se seleccionar
                    backgroundColor = MaterialTheme.colors.BackgroundTextField, //Se cambia el fondo dentro del OutlinedTextField
                    textColor = MaterialTheme.colors.ColorTextTheme, //Se cambia el color del texto dentro de OutlinedTextField
                    cursorColor = Orange, //Se cambia el color del cursor dentro del OutlinedTextField
                    focusedBorderColor = Orange,//Seleccionado
                    unfocusedBorderColor = Orange, //No seleccionado
                )
            )
        }
    }
}

@Composable
fun PasswordSignIn(
    signInUiState: SignState,
    singInViewModel: SignViewModel
) {
    var passwordVisibility by rememberSaveable {
        mutableStateOf(false)
    }
    Column{//OJO, es importante meterlo dentro de algo, porque por si solo ocupa la pantalla completa al ser el unico componente. SI ES QUE SOLO SE DECLARA ESTE
        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {//La seleccion de colores utilizados para el background de la selección de texto por texto y los sus componentes los proporciona "LocalTextSelectionColors.current.", como se quiere personalizar, se usan los que estan declarados dentro de la variable "customTextSelectionColors"
            OutlinedTextField(//Se indica que sera de tipo "OutlinedTextField", OJO: Lo demas es igual a los "TextFields"
                value = signInUiState.passwordSignIn,//Se asinga al valor, lo que tiene la variable "loginUiState" en el apartado de "userName" y en caso de ser nulo. Tendra un valor por defecto de ""
                onValueChange = {
                    singInViewModel.passwordChangeSignIn(it)//La variable "singInViewModel" en la funcion "userChangeSignIn" y se le manda el valor que esta cambiando "it"
                },//Al atributo "onValueChange", se le asigna el parametro it
                maxLines = 1,//Se indica que solo pueda ocupar una linea
                singleLine = true,//Se indica que en lugar de precionar enter de un salto de linea. Se acepte lo que escribio
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),//Se indicia que de este modo que se muestre un teclado que provea de teclas para colocar un password
                shape = RoundedCornerShape(20.dp),//Se asigna bordes que redonde el "OutlinedTextField"
                modifier = Modifier
                    .fillMaxWidth() //Se le asinga el parametro "fillMaxWidth", el cual hara que ocupe el ancho completo de lo que esta declarado
                    .padding(horizontal = 20.dp),//Se asigna un padding horizontal para que estan aun mas corto
                leadingIcon = {//Al atributo "leadingIcon", permite asignar el icono inicial dentro del OutlinedTextField
                    Icon(
                        imageVector = Icons.Filled.Lock,//Se selecciona el icono de google
                        contentDescription = "Password", //Se le asigna la descripcion por defecto
                        tint = Orange
                    )
                },
                trailingIcon = {//Con "trailingIcon", se asigna el boton de mostrar o no contraseña. OJO, solo servira para el icono, para que funcione con el texto, es otra cosa para eso...
                    val imagen = if (passwordVisibility){//Si, "passwordVisibility" esta en false, no se muestra la imagen se cumple una condicion.
                        Icons.Filled.VisibilityOff
                    }else{//Si no, se muestra otra imagen
                        Icons.Filled.Visibility
                    }
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {//Se indica que si es false cuando se presione se pondra true y viseversa
                        Icon(
                            imageVector = imagen,
                            contentDescription = "show password",
                            tint = Orange
                        )
                    }
                },
                visualTransformation = if (passwordVisibility){//Con el metodo "visualTransformation", se indicia que si (passwordVisibility), es true
                    VisualTransformation.None//el motodo no hara nada
                }else{ //De ser falso "passwordVisibility"
                    PasswordVisualTransformation() //El metodo transformara el texto para que se vea a password
                },
                label = {//Se coloca un label, para indicarle al usuario que se pone algo dentro
                    Text(
                        text = "Contraseña",
                        color = MaterialTheme.colors.BackgroundTextFieldLabel
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(//Se indica los colores al momento se seleccionar
                    backgroundColor = MaterialTheme.colors.BackgroundTextField, //Se cambia el fondo dentro del OutlinedTextField
                    textColor = MaterialTheme.colors.ColorTextTheme, //Se cambia el color del texto dentro de OutlinedTextField
                    cursorColor = Orange, //Se cambia el color del cursor dentro del OutlinedTextField
                    focusedBorderColor = Orange,//Seleccionado
                    unfocusedBorderColor = Orange, //No seleccionado
                )
            )
        }
    }
}

@Composable
fun ButtonSignIn(
    signInUiState: SignState,
    singInViewModel: SignViewModel,
    context: Context
) {
    Button(
        onClick = {
            singInViewModel.loginUser(context) //Se llamara a la variable "loginViewModel" accediendo a la funcion "loginUser" y se le pasara el contexto
        },
        enabled = singInViewModel.onLoginChangedEnable(//
            signInUiState.emailSignIn,
            signInUiState.passwordSignIn
        ),
        modifier = Modifier
            .fillMaxWidth()//Se le asinga una anchura. OJO, como no se le mando un "modifier", este sera padre y por ende sera Modifier
            .padding(horizontal = 125.dp),//Se asigna un padding horizontal para que estan aun mas corto
        shape = RoundedCornerShape(15.dp),//Se asigna bordes que redonde el "OutlinedTextField"
        colors = ButtonDefaults.buttonColors(//Se personaliza el boton, cuando este o no seleccionado
            backgroundColor = Color(0xFFff3b2f),
            disabledBackgroundColor = Color(0x80FF3B2F),
            //disabledContentColor = MediumGray,
            //contentColor = Color.White
        )
    ){
        Text(
            text = "Ingresar",//Se asigna el texto
            fontSize = 12.sp,//Se asigna el tamaño
            color = Color(0xFFFFFFFF)//Se asigna el color
        )
    }
}

@Composable
fun LetterSignUp(
    onNavToSignUpPage:() -> Unit,//La funcion recibira una "unidad" para navegar a la pagina del "Registro"
){
    Row(
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "¿Eres nuevo?", //Se le asigna el texto que ira dentro
            fontSize = 15.sp, //Se le asigna el tamaño del texto
            fontWeight = FontWeight.Normal, //Se le asigna el formato del texto
            color = Color.Black, //Se le asigna el color del texto
            modifier = Modifier) //Se le asingna la alineacion que tiene por defecto
        Spacer(
            modifier = Modifier
                .size(5.dp)
        )
        Text(
            text = "Registrate Ahora", //Se le asigna el texto que ira dentro
            fontSize = 15.sp, //Se le asigna el tamaño del texto
            fontWeight = FontWeight.Bold, //Se le asigna el formato del texto
            color = Color.Black, //Se le asigna el color del texto
            modifier = Modifier//Se le asingna la alineacion que tiene por defecto
                .clickable {
                    onNavToSignUpPage.invoke() //Se llama a la lambda "onNavToSignUpPage" con el metodo ".invoke()" la cual permitira navegar a la pagina del "Registro"
            }
        )
    }
}