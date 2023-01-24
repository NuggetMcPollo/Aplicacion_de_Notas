package com.example.notas.presentation.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notas.R
import com.example.notas.ui.theme.ColorTextEmptyContent
import com.example.notas.ui.theme.MediumGray

//El proposito principal de la funcnion es dejarle saber a los usarios que la base de datos esta vacia y eso es el porque no pueden ver ninguna tarea dentro de la lista
@Composable
fun EmptyContent() {
    Column(//Se crea una columna
        modifier = Modifier
            .fillMaxSize()//Se asigna un tamaño que ocupe la pantalla completa
            .background(MaterialTheme.colors.background),//Se asigna el color del background
        verticalArrangement = Arrangement.Center,//Se asigna una horientacion
        horizontalAlignment = Alignment.CenterHorizontally//Se asigna una horientacion
    ) {
        Image(
            modifier = Modifier.size(200.dp),//Se asigna el tamaño
            painter = painterResource(id = R.drawable.empty),//Se asigna el recurso
            contentDescription = null
        )
        Text(
            text = "Nada que mostrar",//Se asigna un texto
            color = MaterialTheme.colors.ColorTextEmptyContent,//Se asigna el color del texto
            fontWeight = FontWeight.Bold,//Se asigna el formato del icono
            fontSize = MaterialTheme.typography.h6.fontSize//Se asigna el tamaño
        )
    }
}

@Composable
@Preview
private fun EmptyContentPreview(){
    EmptyContent()
}