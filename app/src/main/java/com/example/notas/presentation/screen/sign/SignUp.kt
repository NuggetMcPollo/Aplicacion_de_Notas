package com.example.notas.presentation.screen.sign

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notas.R
import com.example.notas.ui.theme.*
import com.example.notas.util.Constants

//Funcion que mostrara la pantalla para el login
@Composable
fun SignUpScreen(
    signUpViewModel: SignViewModel,//La funcion recibira el viewmodel, para poder tener acceso a las funciones dentro a la vista y asi no pida el parametro
    onNavToHomePage:() -> Unit,//La funcion recibira una "unidad" para navegar a la pagina "Principal"
    onNavToSignUpPage:() -> Unit,//La funcion recibira una "unidad" para navegar a la pagina del "Registro"
) {
    val signUpUiState = signUpViewModel.state//Se crea una variable que trera OJO: LOS ESTADOS DEL VIEWMODE "DATACLASS" a la UI
    val isError = signUpUiState.loginError != null//Se crea una variable que almacenara el estado del LoginError, la cual se agarra de la variable "loginUiState" el cual posee el ViewModel en el parametro de "loginError"
    //Esto con el fin de verificar si hay un error

    val context = LocalContext.current//Se crea una variable la cual almecenara el contexto actual
    val drawable = R.drawable.wave //Wave asingado para esta pantalla
    val icon = Icons.Filled.Close //Se crea una variable que envia el icono usado en este screen
    val title = "Registro" //Se crea una variable que envia el titulo usado en este screen

    Box(
        modifier = Modifier
            .fillMaxSize()//Se asigna que sea tamaño completo de la pantalla
            .background(color = MaterialTheme.colors.BackgroundScreen)//Se llama al atributo "colors" y dentro de este se llama a "MaterialTheme" y se accede a los archivos declarados
        //Se le asigna un background a la PANTALLA COMPLETA
    ){
        if (isError){//Se pregunta, si hay un error "isError"
            Text(//Se coloca un texto
                text = signUpUiState.loginError ?: "Error Desconocido",//Se muestra el error y en caso de ser nulo, se muestra por defecto "unknown error"
                color = Color.Red,//Se asigna el color
            )
        }
        //TODO: HEADER
        HeaderSignUp(
            modifier = Modifier
                .align(Alignment.TopCenter),
            drawable = drawable,
            title = title,
            icon = icon,
            onNavToSignUpPage = onNavToSignUpPage
        )
        //TODO: BODY
        BodySignUp(
            modifier = Modifier
                .align(Alignment.Center),
            signUpUiState = signUpUiState,
            signUpViewModel = signUpViewModel,
            context = context,
            onNavToHomePage = onNavToHomePage,
        )
    }
}

/** HEADER **/
@Composable
fun HeaderSignUp(
    modifier: Modifier,//Recibe un "Modifier", en caso de querer modificar los componentes declarados dentro de la funcion
    @DrawableRes drawable: Int,//Recibe una variable de tipo Int, para colocar la parte del Wave. TODO: Para asegurarse de que sea Drawable se asigna el "@DrawableRes"
    title: String,
    icon: ImageVector,//Recibe una variable de tipo "ImageVector", el cual se colocara dependiendo la pantalla correspondiente
    onNavToSignUpPage: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()//Se asigna un ANCHO COMPLETO
    ){
        Box(
            modifier = Modifier
                .background(Orange)
                .fillMaxWidth()//Se asigna un ANCHO COMPLETO
                .height(60.dp)
                .padding(Constants.paddingScreen)
        ){
            IconSignUp(
                modifier = Modifier
                    .align(Alignment.TopStart),
                icon = icon,
                onNavToSignUpPage = onNavToSignUpPage
            )
        }
        Box(
            modifier = Modifier
                .background(Orange)
                .fillMaxWidth()//Se asigna un ANCHO COMPLETO
                .height(60.dp)
                .padding(Constants.paddingScreen)
        ){
            TitleSignUp(
                modifier = Modifier
                    .align(Alignment.Center),
                title = title,
            )
        }
        WaveSignUp(
            modifier = Modifier,
            drawable = drawable
        )
    }
}

@Composable
fun IconSignUp(
    modifier: Modifier,
    icon: ImageVector,//Recibe una variable de tipo "ImageVector", el cual se colocara dependiendo la pantalla correspondiente
    onNavToSignUpPage: () -> Unit
){
    IconButton(
        modifier = modifier,
        onClick = {
            onNavToSignUpPage.invoke()
        }
    ){
        Icon(
            imageVector = icon,//Se coloca el icono
            contentDescription = null,
            tint = Color.White,//Se le asigna el color para el icono
        )
    }
}

@Composable
fun TitleSignUp(
    modifier: Modifier,
    title: String
) {
    Text(
        text = title,//Se le asinga el titulo que se manda para la pantalla correspondiente
        fontSize = 20.sp, //Se le asigna el tamaño del titulo
        fontWeight = FontWeight.SemiBold, //Se le asigna el formato del titulo
        color = Color.White, //Se le asigna el color para el titulo
        modifier = modifier
    )
}

@Composable
fun WaveSignUp(
    modifier: Modifier,
    @DrawableRes drawable: Int,
) {
    Image(
        painter = painterResource(id = drawable),//Se le asinga el wave que se manda para la pantalla correspondiente
        contentDescription = null,//Se agrega la modificacion por defecto
        modifier = modifier//Se asigna el modificador predeterminado
    )
}

/** BODY **/
@Composable
fun BodySignUp(
    modifier: Modifier,
    signUpUiState: SignState,
    signUpViewModel: SignViewModel,
    context: Context,
    onNavToHomePage: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(Constants.paddingScreen)
            .fillMaxWidth()//Se asigna que abarque EL LARGO COMPLETAMENTE
    ){
        Spacer(
            modifier = Modifier.size(50.dp)
        )
        EmailSignUp(
            signUpUiState = signUpUiState,
            singUpViewModel = signUpViewModel
        )
        Spacer(
            modifier = Modifier.size(30.dp)
        )
        PasswordSignUp(
            signUpUiState = signUpUiState,
            singUpViewModel = signUpViewModel
        )
        Spacer(
            modifier = Modifier.size(30.dp)
        )
        ConfirmPasswordSignUp(
            signUpUiState = signUpUiState,
            singUpViewModel = signUpViewModel
        )
        Spacer(
            modifier = Modifier.size(50.dp)
        )
        ButtonSignUp(
            signUpUiState = signUpUiState,
            singUpViewModel = signUpViewModel,
            context = context
        )
        if (signUpUiState.isLoading){
            CircularProgressIndicator()
        }
        LaunchedEffect(key1 = signUpViewModel.hasUser){//Se utiliza un "signUpViewModel", la cual solo re recompondra una vez y eso es cuando el usuario se logee con exito, lo cual navegara a la pagina "HOME"
            if (signUpViewModel.hasUser){//Se pregunta si "signUpViewModel.hasUser" es igual a true
                onNavToHomePage.invoke() //Se llama a la lambda "onNavToSignUpPage" con el metodo ".invoke()" la cual permitira navegar a la pagina de "Home"
            }
        }
    }
}

@Composable
fun EmailSignUp(
    signUpUiState: SignState,
    singUpViewModel: SignViewModel
) {
    Column{//OJO, es importante meterlo dentro de algo, porque por si solo ocupa la pantalla completa al ser el unico componente. SI ES QUE SOLO SE DECLARA ESTE
        CompositionLocalProvider(LocalTextSelectionColors provides Constants.customTextSelectionColors) {//La seleccion de colores utilizados para el background de la selección de texto por texto y los sus componentes los proporciona "LocalTextSelectionColors.current.", como se quiere personalizar, se usan los que estan declarados dentro de la variable "customTextSelectionColors"
            OutlinedTextField(//Se indica que sera de tipo "OutlinedTextField", OJO: Lo demas es igual a los "TextFields"
                value = signUpUiState.emailSignUp,//Se asinga al valor, lo que tiene la variable "loginUiState" en el apartado de "userName" y en caso de ser nulo. Tendra un valor por defecto de ""
                onValueChange = {//Se le asigna al valor cambiante, lo que tiene...
                    singUpViewModel.userChangeSignup(it)//La variable "singInViewModel" en la funcion "userChangeSignIn" y se le manda el valor que esta cambiando "it"
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
fun PasswordSignUp(
    signUpUiState: SignState,
    singUpViewModel: SignViewModel
) {
    var passwordVisibility by rememberSaveable {
        mutableStateOf(false)
    }
    Column{//OJO, es importante meterlo dentro de algo, porque por si solo ocupa la pantalla completa al ser el unico componente. SI ES QUE SOLO SE DECLARA ESTE
        CompositionLocalProvider(LocalTextSelectionColors provides Constants.customTextSelectionColors) {//La seleccion de colores utilizados para el background de la selección de texto por texto y los sus componentes los proporciona "LocalTextSelectionColors.current.", como se quiere personalizar, se usan los que estan declarados dentro de la variable "customTextSelectionColors"
            OutlinedTextField(//Se indica que sera de tipo "OutlinedTextField", OJO: Lo demas es igual a los "TextFields"
                value = signUpUiState.passwordSignUp,//Se asinga al valor, lo que tiene la variable "loginUiState" en el apartado de "userName" y en caso de ser nulo. Tendra un valor por defecto de ""
                onValueChange = {
                    singUpViewModel.passwordChangeSignup(it)//La variable "singInViewModel" en la funcion "userChangeSignIn" y se le manda el valor que esta cambiando "it"
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
                            contentDescription = null,
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
fun ConfirmPasswordSignUp(
    signUpUiState: SignState,
    singUpViewModel: SignViewModel
) {
    var passwordVisibility by rememberSaveable {
        mutableStateOf(false)
    }
    Column(){//OJO, es importante meterlo dentro de algo, porque por si solo ocupa la pantalla completa al ser el unico componente. SI ES QUE SOLO SE DECLARA ESTE
        CompositionLocalProvider(LocalTextSelectionColors provides Constants.customTextSelectionColors) {//La seleccion de colores utilizados para el background de la selección de texto por texto y los sus componentes los proporciona "LocalTextSelectionColors.current.", como se quiere personalizar, se usan los que estan declarados dentro de la variable "customTextSelectionColors"
            OutlinedTextField(//Se indica que sera de tipo "OutlinedTextField", OJO: Lo demas es igual a los "TextFields"
                value = signUpUiState.confirmPasswordSignUp,//Se asinga al valor, lo que tiene la variable "loginUiState" en el apartado de "userName" y en caso de ser nulo. Tendra un valor por defecto de ""
                onValueChange = {
                    singUpViewModel.passwordConfirmChangeSignup(it)//La variable "singInViewModel" en la funcion "userChangeSignIn" y se le manda el valor que esta cambiando "it"
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
                            contentDescription = null,
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
                        text = "Confirmar Contraseña",
                        color = MaterialTheme.colors.BackgroundTextFieldLabel,
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
fun ButtonSignUp(
    signUpUiState: SignState,
    singUpViewModel: SignViewModel,
    context: Context
) {
    Button(
        onClick = {
            singUpViewModel.createUser(context) //Se llamara a la variable "loginViewModel" accediendo a la funcion "loginUser" y se le pasara el contexto
        },
        enabled = singUpViewModel.onPasswordAndPasswordConfirm(
            signUpUiState.passwordSignUp,
            signUpUiState.confirmPasswordSignUp
        ) && singUpViewModel.onLoginChangedEnable(
            signUpUiState.emailSignUp,
            signUpUiState.passwordSignUp
        ),
        modifier = Modifier
            .fillMaxWidth()//Se le asinga una anchura. OJO, como no se le mando un "modifier", este sera padre y por ende sera Modifier
            .padding(horizontal = 125.dp),//Se asigna un padding horizontal para que estan aun mas corto
        shape = RoundedCornerShape(15.dp),//Se asigna bordes que redonde el "OutlinedTextField"
        colors = ButtonDefaults.buttonColors(//Se personaliza el boton, cuando este o no seleccionado
            backgroundColor = Color(0xFFff3b2f),
            disabledBackgroundColor = Color(0x80FF3B2F),
            //contentColor = Color.White,
            //disabledContentColor = MediumGray
        )
    ){
        Text(
            text = "Registrarse",//Se asigna el texto
            fontSize = 12.sp,//Se asigna el tamaño
            color = Color(0xFFFFFFFF)//Se asigna el color
        )
    }
}