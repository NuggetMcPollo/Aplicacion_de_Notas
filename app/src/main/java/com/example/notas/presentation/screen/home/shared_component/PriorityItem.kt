package com.example.notas.presentation.screen.home.shared_component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notas.presentation.screen.home.Priority
import com.example.notas.ui.theme.Typography

//Este componente es usado dentro del drop menu, el cual se mostrara cuando se de click
@Composable
fun PriorityItem(priority: Priority){//La funcion recibira un valor de tipo "Priority", la cual va agarrar del enum class que se creo
    Row(//Se crea una fila
        verticalAlignment = Alignment.CenterVertically//Se alinea el contenido de forma central
    ) {
        Canvas(//Se crea un circulo, el cual representara el color de la prioridad de la tarea
            modifier = Modifier
                .size(16.dp)//Se asigna un tamaño
        ){
             drawCircle(color = priority.color)//Se llama al metodo "drawCircle" y dentro de este se asinga el color que va a tener, el cual lo obtendra de la prioridad que se la a pasar del parametro "priority"
        }
        Text(//Se crea un texto, el cual representara la prioridad de la tarea
            modifier = Modifier.padding(start = 12.dp),
            text = priority.name,//Se le asigna el texto a la prioridad
            style = Typography.subtitle2,//Se le asigna el estilo de la letra. ASEGURARSE TOMAR BIEN EL "IMPORT"
            color = MaterialTheme.colors.onSurface//Se le asinga el color
        )
    }
}

@Preview
@Composable
fun PriorityItemPreview(){
    PriorityItem(priority = Priority.Alta)
}
