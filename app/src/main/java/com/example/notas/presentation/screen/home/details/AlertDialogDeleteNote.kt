package com.example.notas.presentation.screen.home.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
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

@Composable
fun AlertDialogDeleteNote(
    title: String,//La funcion recibira un valor de tipo "String", referente a la tarea que se va a borrar
    message: String,//La funcion recibira un valor de tipo "String" referente al mensaje que se mostrara de la tarea a borrar
    onDismiss: () -> Unit,//La funcion recibira un Unit, y esta servira para saber cuando abrir o cerrar el dialogo
    onNoClicked: () -> Unit,//La funcion recibira un Unit, para capturar el click del "No" y el dialogo se cerrara
    onYesClicked: () -> Unit,//La funcion recibira un Unit, para capturar el click del "Si", el dialogo se cerrara, y se hara a la accion
    properties: DialogProperties = DialogProperties()
){
    Dialog(
        onDismissRequest = onDismiss,
        properties = properties
    ) {
        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(20.dp)
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable(onClick = onDismiss)
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = title,
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = message,
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
                            backgroundColor = Color.DarkGray,
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