package com.example.notas.presentation.screen.home.notes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.notas.ui.theme.BackgroundAlertDialog
import com.example.notas.ui.theme.Orange

@Composable
fun AlertDialogDeleteAll(
    onDismiss: () -> Unit,//La funcion recibira un Unit, y esta servira para saber cuando abrir o cerrar el dialogo
    onNoClicked: () -> Unit,//La funcion recibira un Unit, para capturar el click del "No" y el dialogo se cerrara
    onYesClicked: () -> Unit,//La funcion recibira un Unit, para capturar el click del "Si", el dialogo se cerrara, y se hara a la accion
    properties: DialogProperties = DialogProperties()//La funcion tendra un valor de propiedades, las cuales se podran alterar si uno quiere
){
    val background = MaterialTheme.colors.BackgroundAlertDialog
    val spec = LottieCompositionSpec.RawRes(resId = com.example.notas.R.raw.warning)
    val composition by rememberLottieComposition(spec = spec)
    Dialog(
        onDismissRequest = onDismiss,//Se pregunta el estado de la variable si es "true", el dialogo se abre
        properties = properties
    ) {
        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(
                width = 1.dp,
                color = Orange
            ),
            modifier = Modifier
                .background(Color.Transparent)
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(background)
                    .padding(16.dp)
            ) {
                LottieAnimation(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(80.dp),
                    speed = 2f,
                    composition = composition,
                    iterations = LottieConstants.IterateForever
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = "¿Eliminar todas las tareas?",
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = "¿Estas seguro de que desea eliminar todas las tareas?",
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(40.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = onNoClicked,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent,
                            contentColor = Color.Red
                        )
                    ){
                        Text(text = "Cancelar")
                    }
                    Button(
                        onClick = onYesClicked,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Orange,
                            contentColor = Color.White
                        )
                    ){
                        Text(text = "OK")
                    }
                }
            }
        }
    }
}