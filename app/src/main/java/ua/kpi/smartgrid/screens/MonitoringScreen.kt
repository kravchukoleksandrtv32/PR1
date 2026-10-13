package ua.kpi.smartgrid.screens

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.kpi.smartgrid.data.Calculator
import kotlin.random.Random

/**
 * Моніторинг мережі (практичне заняття 1).
 *
 * @param gridLoad поточне навантаження мережі, кВт
 * @param onLoadChange змінює навантаження (стан зберігається в MainScreen)
 */
@Composable
fun MonitoringScreen(gridLoad: Int, onLoadChange: (Int) -> Unit) {
    // Частота мережі, Гц
    var frequency by remember { mutableStateOf(50.0) }

    // Плавна зміна числа при оновленні
    val animatedLoad by animateIntAsState(targetValue = gridLoad, label = "LoadAnimation")

    // Завантаження підстанції потужністю 10 000 кВт
    val loadPercent = Calculator().calculateLoadPercent(gridLoad.toDouble(), 10000.0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FD)) // світло-блакитний фон
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Hello Smart Grid", fontSize = 26.sp, color = MaterialTheme.colorScheme.primary)
        Text(text = "Система розподілу електроенергії")

        Spacer(modifier = Modifier.height(24.dp))

        // Показники: Row — підпис і значення в одному рядку
        Row {
            Text(text = "Навантаження: ", fontSize = 18.sp)
            Text(text = "$animatedLoad кВт", fontSize = 18.sp, color = MaterialTheme.colorScheme.secondary)
        }
        Row {
            Text(text = "Частота: ", fontSize = 18.sp)
            Text(text = "%.2f Гц".format(frequency), fontSize = 18.sp, color = MaterialTheme.colorScheme.secondary)
        }
        Row {
            Text(text = "Завантаження: ", fontSize = 18.sp)
            Text(text = "%.0f %%".format(loadPercent), fontSize = 18.sp, color = MaterialTheme.colorScheme.secondary)
        }
        Text(text = "Статус: ${getLoadStatus(loadPercent)}", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            // Симуляція нових даних з мережі
            onLoadChange(Random.nextInt(1000, 9000))
            frequency = 49.8 + Random.nextDouble() * 0.4
        }) {
            Text("Оновити дані")
        }
    }
}

/**
 * Статус завантаження: до 80 % — норма, до 100 % — високе, більше — перевантаження.
 */
fun getLoadStatus(percent: Double): String {
    return when {
        percent <= 80 -> "Норма"
        percent <= 100 -> "Високе навантаження"
        else -> "Перевантаження!"
    }
}
