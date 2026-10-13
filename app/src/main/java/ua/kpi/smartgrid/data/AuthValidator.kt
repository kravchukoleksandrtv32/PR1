package ua.kpi.smartgrid.data

/**
 * Перевірка даних входу. Повертає текст помилки або null, якщо все правильно.
 */
fun validateLogin(email: String, password: String): String? {
    if (email.isBlank() || password.isBlank()) return "Заповніть усі поля"
    if (!email.contains("@")) return "Некоректна електронна пошта"
    return null
}

/**
 * Перевірка даних реєстрації. Повертає текст помилки або null, якщо все правильно.
 */
fun validateRegistration(name: String, email: String, password: String, confirm: String): String? {
    if (name.isBlank() || email.isBlank() || password.isBlank()) return "Заповніть усі поля"
    if (!email.contains("@")) return "Некоректна електронна пошта"
    if (password.length < 6) return "Пароль має містити щонайменше 6 символів"
    if (password != confirm) return "Паролі не збігаються"
    return null
}
