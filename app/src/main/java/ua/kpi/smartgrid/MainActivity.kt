package ua.kpi.smartgrid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ua.kpi.smartgrid.screens.LoginScreen
import ua.kpi.smartgrid.screens.MainScreen
import ua.kpi.smartgrid.ui.theme.SmartGridTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartGridTheme {
                // Ім'я оператора, який увійшов; null — ще не увійшов
                var userName by remember { mutableStateOf<String?>(null) }

                Surface(modifier = Modifier.fillMaxSize()) {
                    if (userName == null) {
                        // Спочатку — екран входу та реєстрації
                        LoginScreen(onLoginSuccess = { name -> userName = name })
                    } else {
                        // Після входу — головний екран застосунку
                        MainScreen(userName = userName!!, onLogout = { userName = null })
                    }
                }
            }
        }
    }
}
