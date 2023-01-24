package com.example.notas.presentation.screen.home.notes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notas.R
import com.example.notas.presentation.screen.sign.BodySignUp
import com.example.notas.presentation.screen.sign.HeaderSignUp
import com.example.notas.presentation.screen.sign.SignViewModel
import com.example.notas.ui.theme.BackgroundScreen
import com.example.notas.ui.theme.ItemDrawer
import com.example.notas.ui.theme.Orange

@Composable
fun NotesDrawer(
    signViewModel: SignViewModel,
    navToLoginPage: () -> Unit,//La funcion recibira una "unidad" para navegar a la pagina del "login"
    onCloseDrawer: () -> Unit,
){//La funcion recibe una funcion lambada llamada "onCloseDrawer", la cual su funcion es ocultar contenido, cuando se preciona un icono (Se le asigna Unit para que sea funcion)
    /**OJO: No es que no, devuelva nada. Si no que su funcion como tal es recibir el click, el efecto se le agraga dentro de la funcion en donde se esta LLAMANDO**/
    val background = MaterialTheme.colors.BackgroundScreen
    Box(
        modifier = Modifier
            .fillMaxSize()//Se asigna que sea tamaño completo de la pantalla
            .background(background)//Se llama al atributo "colors" y dentro de este se llama a "MaterialTheme" y se accede a los archivos declarados
        //Se le asigna un background a la PANTALLA COMPLETA
    ){
        //TODO: HEADER
        HeaderDrawer(
            modifier = Modifier
                .align(Alignment.TopCenter),
            signViewModel = signViewModel,
            onCloseDrawer = onCloseDrawer,
            navToLoginPage = navToLoginPage
        )
        //TODO: FOOTER
        FooterDrawer(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }
}

/** HEADER **/
@Composable
fun HeaderDrawer(
    modifier: Modifier,
    signViewModel: SignViewModel,
    onCloseDrawer: () -> Unit,
    navToLoginPage: () -> Unit,//La funcion recibira una "unidad" para navegar a la pagina del "login"
){
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {//Ojo se coloca una columna, en la cual iran componentes uno tras otro. Y se coloca un padding
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = MaterialTheme.colors.ItemDrawer,
            modifier = Modifier
                .align(Alignment.End)
                .clickable {
                    onCloseDrawer()
                }
        )
        Spacer(modifier = Modifier.size(20.dp))
        Image(
            painter = painterResource(id = R.drawable.nagatoro),
            contentDescription = "Diosa nagatoro",//Se coloca una descripcion de lo que trata la imagen
            modifier = Modifier//Se le asigna un modificador
                .size(100.dp)
                .padding(all = 5.dp)
                .clip(CircleShape)//.clip(RoundedCornerShape(25f)) se le coloca una circunferencia
                .align(Alignment.CenterHorizontally)
                .border(
                    1.dp,//Con un borde
                    Orange,//Un color
                    CircleShape//Se le asigna a que se le va asignar el borde
                )
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "User: ${signViewModel.currentUser()}",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.size(20.dp))
        Row (
             verticalAlignment = Alignment.CenterVertically,
             modifier = Modifier
                 .background(Color.Transparent)
                 .padding(start = 5.dp)
                 .clickable{
                     signViewModel.signOut()
                     navToLoginPage.invoke()
                 }
        ){
            Icon(
                imageVector = Icons.Default.ExitToApp,//Se coloca el icono de X
                contentDescription = null,
            )
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                text = "Cerrar Sesión",
                modifier = Modifier
                    .padding(vertical = 8.dp)
            )
        }
    }
}

/** FOOTER **/
@Composable
fun FooterDrawer(
    modifier: Modifier,
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(
            text = "Notas by オサマ",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.size(5.dp))
        Text(
            text = "Copyright 2023© y más",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}