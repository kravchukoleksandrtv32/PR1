package ua.kpi.smartgrid.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Головний екран після входу: верхній рядок з ім'ям оператора
 * і нижнє меню з трьома розділами.
 */
@Composable
fun MainScreen(userName: String, onLogout: () -> Unit) {
    // Обрана вкладка: 0 — моніторинг, 1 — розрахунок, 2 — режими
    var selectedTab by remember { mutableIntStateOf(0) }

    // Навантаження мережі, кВт — спільне для моніторингу і режимів
    var gridLoad by remember { mutableIntStateOf(5200) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Filled.ElectricBolt, contentDescription = null) },
                    label = { Text("Моніторинг") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Filled.Calculate, contentDescription = null) },
                    label = { Text("Розрахунок") }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Filled.Tune, contentDescription = null) },
                    label = { Text("Режими") }
                )
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // Верхній рядок: оператор і кнопка виходу
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Оператор: $userName", modifier = Modifier.weight(1f))
                TextButton(onClick = onLogout) { Text("Вийти") }
            }

            // Вміст обраної вкладки
            when (selectedTab) {
                0 -> MonitoringScreen(gridLoad = gridLoad, onLoadChange = { gridLoad = it })
                1 -> CalculatorScreen()
                2 -> ModesScreen(gridLoad = gridLoad, onLoadChange = { gridLoad = it })
            }
        }
    }
}
