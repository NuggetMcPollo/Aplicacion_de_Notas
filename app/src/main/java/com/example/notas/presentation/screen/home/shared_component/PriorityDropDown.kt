package com.example.notas.presentation.screen.home.shared_component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.notas.presentation.screen.home.Priority

@Composable
fun PriorityDropDown(
    priority: Priority,//Recibira un parametro de tipo "Priority", en el cual se selecciona una de las opciones
    onPrioritySelected: (Priority) -> Unit//Recibira un parametro, la cual tomara un parametro
) {
    var expanded by remember { mutableStateOf(false) }//Se crea una variable para guardar el estado del "DropDown", el cual se va a extender para mostrar las posibles prioridades. Se coloca por defecto "false" para indicar que esta cerraod
    val angle: Float by animateFloatAsState(//Se crea una variable de tipo "Float" y se usara un "by" animateFloatAsState
        targetValue = if (expanded) 180f else 0f//Se le asigna al paremetro "targetValue" una condicional, si "expanded" es "true" [esta expandida], si entonces el objeto se cambiara a 180, si no, esta cerrada. El valor sera 0
    )

    var parentSize by remember { mutableStateOf(IntSize.Zero) }//Se crea una variable para almacenar el tamaño del componente

    Row(//Se declara una fila
        verticalAlignment = Alignment.CenterVertically,//Se asigna un centrado
        modifier = Modifier
            .fillMaxWidth()//Se asigna un ancho completo
            .height(60.dp)//Se asigna un largo, predeterminado
            .onGloballyPositioned {//Se agrega el modificador "onGloballyPositioned" y dentro de este modificador, se extrae el tamaño del padre, osea "Row"
                parentSize = it.size//Se extrae el tamaño del padre "it" y se le asigna a la variable "parentSize"
            }
            .background(MaterialTheme.colors.background)//Se asigna un fondo para la fila
            .clickable { expanded = true }//Se asigna un valor clickable, y una vez cuando se precione, el estado de la variable "expanded", pasara a true
            .border(//Se asigna un borde
                width = 1.dp,//De 1 dp
                color = MaterialTheme.colors.onSurface.copy(
                    alpha = ContentAlpha.disabled//Se asigna un degradado, que simule que este apagado completo
                ),
                shape = MaterialTheme.shapes.small//Se agrega un curveado delgado
            )
    ) {
        Canvas(//Se asinga un canvas
            modifier = Modifier
                .size(16.dp)//Se asigna el tamaño
                .weight(weight = 1f)//Se asinga el peso DENTRO DE LA FILA
        ) {
            drawCircle(color = priority.color)//Se asigna el color, el cual sera tomado por la prioridad
        }
        Text(
            modifier = Modifier
                .weight(weight = 8f),//Se indica un pesp DENTRO DE LA FILA
            text = priority.name,//Se asigna el nombre de la prioridad
            style = MaterialTheme.typography.subtitle2//Se asigna el estilo
        )
        IconButton(//Se asigna un "IconButton"
            modifier = Modifier
                .alpha(ContentAlpha.medium)//Se asigna un alpha, para indicar que esta medio oscuro
                .rotate(degrees = angle)//Se le asigna una rotacion, esta cambiara dependiendo la variable "angle"
                .weight(weight = 1.5f),//Se asigna un peso DENTRO DE LA FILA
            onClick = { expanded = true }//Se asigna un clickeo dentro del icono, y una vez cuando se precione, el estado de la variable "expanded", pasara a true
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,//Se asigna el icono
                contentDescription = "Drop Down Arrow"//Se asigna la descripcion del icono
            )
        }
        DropdownMenu(//Se llama a la funcion "DropdownMenu"
            modifier = Modifier
                //.fillMaxWidth(fraction = 0.94)//Se agrega para que no este desplegado completamente a la pantalla, si no deje el espacio que tiene asignado
                .width(with(//Se le asigna el modificador de ancho y se llama a la funcion "with"
                    LocalDensity.current) {
                    parentSize.width.toDp()//La funcion "toDp" convierte exitosamente ese valor pixel a Dp
                }
                ),
            expanded = expanded,//Se le asigna el estado inicial del Drop
            onDismissRequest = { expanded = false }//En caso de salirse, el estado pasara a falso
        ) {
            Priority.values().slice(0..2).forEach { priority ->//Se llama a la clase "Priority" y se extraen los valores ".values()" = [Priority.values()] esta funcion recibira un array de todas las prioridades
                //Pero ojo solo se quieren los primeros 3 asi que con [.slice(0..2)] se llaman a las 0,1,2 3 primeras opciones y por cada ".forEach" opcion
                DropdownMenuItem(//Se desplegara un "DropdownMenuItem", la cual tendra...
                    onClick = {//Un boton de click
                        expanded = false//Se oibdra en falso
                        onPrioritySelected(priority)//Y se mandra la prioridad de la lambda
                    }
                ) {
                    PriorityItem(priority = priority)//En prioridad se asingara la prioridad
                }
            }
        }
    }
}

@Composable
@Preview
private fun PriorityDropDownPreview() {
    PriorityDropDown(
        priority = Priority.Baja,
        onPrioritySelected = {}
    )
}