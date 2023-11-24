package com.example.pr304

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pr304.ui.theme.PR304Theme
import kotlinx.coroutines.delay
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PR304Theme {
                // Crear una superficie con tamaño completo y color de fondo del tema
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(modificador: Modifier = Modifier) {
    // Estado para el número generado aleatoriamente
    var num by remember { mutableStateOf(generarNumeroAleatorio()) }

    // Estado para la entrada de usuario
    var numInput by remember { mutableStateOf("") }

    // Estado para el mensaje que se mostrará
    var mensaje by remember { mutableStateOf("") }

    // Columna que organiza los elementos de la interfaz de usuario verticalmente
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        // Texto que indica al usuario que intente adivinar un número
        Text(text = "Intenta adivinar un número del 1 al 10:")

        // Campo de texto para la entrada del usuario
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = numInput,
            onValueChange = {
                // Limitar la longitud de la entrada a 2 caracteres
                if (it.length <= 2) {
                    numInput = it
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        // Botón elevado para comprobar la adivinanza
        ElevatedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp, vertical = 4.dp),
            onClick = {
                val userInput = numInput.toIntOrNull()

                // Comparar la entrada del usuario con el número generado aleatoriamente
                mensaje = if (num == userInput) {
                    "¡Has acertado!"
                } else {
                    // Generar un nuevo número aleatorio si la adivinanza es incorrecta
                    num = generarNumeroAleatorio()
                    "No has acertado!"
                }
            }
        ) {
            Text(
                text = "Comprobar",
            )
        }

        // Mostrar el mensaje durante 1 segundo usando LaunchedEffect
        LaunchedEffect(mensaje){
            delay(1000)
            mensaje = ""
        }

        // Mostrar el mensaje si no está vacío
        if (mensaje.isNotEmpty()) {
            Text(text = mensaje)
        }
    }
}

// Función para generar un número aleatorio del 1 al 10
fun generarNumeroAleatorio(): Int {
    return Random.nextInt(1, 11)
}

// Composable para la vista previa de la interfaz de usuario
@Preview(showBackground = true, widthDp = 360)
@Composable
fun MyPreview() {
    PR304Theme(dynamicColor = false) {
        MainScreen(Modifier.fillMaxSize())
    }
}

