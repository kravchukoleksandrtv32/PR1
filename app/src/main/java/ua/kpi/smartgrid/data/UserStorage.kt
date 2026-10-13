package ua.kpi.smartgrid.data

/**
 * Зареєстрований користувач (оператор мережі).
 */
data class User(val name: String, val email: String, val password: String)

/**
 * Список користувачів, що зберігається, поки застосунок відкритий.
 * Містить тестового оператора, щоб одразу можна було увійти.
 */
object UserStorage {
    private val users = mutableListOf(
        User("Оператор", "operator@grid.ua", "123456")
    )

    // Додає нового користувача
    fun register(user: User) {
        users.add(user)
    }

    // Шукає користувача з такою поштою і паролем; якщо немає — повертає null
    fun login(email: String, password: String): User? {
        return users.find { it.email == email && it.password == password }
    }
}
