package ua.kpi.smartgrid.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.kpi.smartgrid.data.User
import ua.kpi.smartgrid.data.UserStorage
import ua.kpi.smartgrid.data.validateLogin
import ua.kpi.smartgrid.data.validateRegistration

/**
 * Екран входу та реєстрації оператора (практичне заняття 4).
 *
 * @param onLoginSuccess викликається з ім'ям користувача після успішного входу
 */
@Composable
fun LoginScreen(onLoginSuccess: (String) -> Unit) {
    var isLoginMode by remember { mutableStateOf(true) } // true — вхід, false — реєстрація
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AuthHeader(isLoginMode)

        // Поля форми; ім'я та підтвердження пароля — лише при реєстрації
        if (!isLoginMode) AuthTextField(name, { name = it }, "Ім'я")
        AuthTextField(email, { email = it }, "Електронна пошта")
        AuthTextField(password, { password = it }, "Пароль", isPassword = true)
        if (!isLoginMode) AuthTextField(confirm, { confirm = it }, "Підтвердження пароля", isPassword = true)

        Spacer(modifier = Modifier.height(16.dp))

        AuthButton(
            text = if (isLoginMode) "Увійти" else "Зареєструватися",
            isLoading = isLoading,
            onClick = {
                // 1. Перевіряємо дані
                val error = if (isLoginMode) validateLogin(email, password)
                            else validateRegistration(name, email, password, confirm)
                if (error != null) {
                    isError = true
                    message = error
                    return@AuthButton
                }
                // 2. Імітуємо запит до сервера з індикатором завантаження
                scope.launch {
                    isLoading = true
                    delay(1000)
                    isLoading = false
                    if (isLoginMode) {
                        val user = UserStorage.login(email, password)
                        if (user != null) {
                            onLoginSuccess(user.name)
                        } else {
                            isError = true
                            message = "Невірна пошта або пароль"
                        }
                    } else {
                        UserStorage.register(User(name, email, password))
                        isError = false
                        message = "Реєстрацію завершено, тепер увійдіть"
                        isLoginMode = true
                    }
                }
            }
        )

        MessageText(message, isError)

        // Перемикання між входом і реєстрацією
        TextButton(onClick = { isLoginMode = !isLoginMode; message = "" }) {
            Text(if (isLoginMode) "Немає акаунта? Зареєструватися" else "Вже є акаунт? Увійти")
        }

        Text(text = "Тестовий вхід: operator@grid.ua / 123456", fontSize = 12.sp, color = Color.Gray)
    }
}

/** Заголовок екрана. */
@Composable
fun AuthHeader(isLoginMode: Boolean) {
    Text(text = "⚡ Smart Grid", fontSize = 28.sp, color = MaterialTheme.colorScheme.primary)
    Text(text = if (isLoginMode) "Вхід оператора" else "Реєстрація оператора", fontSize = 18.sp)
    Spacer(modifier = Modifier.height(16.dp))
}

/** Поле введення; для пароля символи приховуються. */
@Composable
fun AuthTextField(value: String, onValueChange: (String) -> Unit, label: String, isPassword: Boolean = false) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier.fillMaxWidth()
    )
}

/** Кнопка дії; під час завантаження показує індикатор. */
@Composable
fun AuthButton(text: String, isLoading: Boolean, onClick: () -> Unit) {
    Button(onClick = onClick, enabled = !isLoading, modifier = Modifier.fillMaxWidth()) {
        if (isLoading) CircularProgressIndicator(modifier = Modifier.size(20.dp)) else Text(text)
    }
}

/** Повідомлення: червоне — помилка, зелене — успіх. */
@Composable
fun MessageText(message: String, isError: Boolean) {
    if (message.isNotEmpty()) {
        Text(text = message, color = if (isError) Color.Red else Color(0xFF2E7D32))
    }
}
