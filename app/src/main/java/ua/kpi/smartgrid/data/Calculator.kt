package ua.kpi.smartgrid.data

import kotlin.math.sqrt

/**
 * Розрахунки для системи розподілу електроенергії.
 * Використовується в заняттях 1, 2 і 3.
 */
class Calculator {

    /**
     * Активна потужність трифазної мережі: P = √3 · U · I · cosφ.
     *
     * @param voltage напруга, кВ
     * @param current струм, А
     * @param cosPhi коефіцієнт потужності
     * @return потужність, кВт
     */
    fun calculatePower(voltage: Double, current: Double, cosPhi: Double): Double {
        return sqrt(3.0) * voltage * current * cosPhi
    }

    /**
     * Втрати потужності в лінії: ΔP = 3 · I² · R.
     *
     * @return втрати, кВт
     */
    fun calculateLosses(current: Double, resistance: Double): Double {
        return 3 * current * current * resistance / 1000
    }

    /**
     * Завантаження джерела у відсотках.
     */
    fun calculateLoadPercent(load: Double, capacity: Double): Double {
        if (capacity <= 0.0) return 0.0
        return load / capacity * 100
    }
}
