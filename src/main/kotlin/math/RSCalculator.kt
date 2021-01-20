package math

import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Файл функций для рассчёта показателя Херста методом R/S анализа
 */


/**
 * Среднее значение
 */
fun averageValue(values: List<Double>): Double {
    // TODO: Наверное, стоит это использовать напрямую без отдельной функци
    return values.average()
}

/**
 * Стандартное отклонение S
 */
fun standardDeviation(values: List<Double>): Double {
    return sqrt(values.sum().pow(2.0) / values.count())
}

/**
 * Перевод значений в логарифмический ряд
 */
fun convertToLogarithmicSeries(values: List<Double>): List<Double> {
    val logarithmicValues: ArrayList<Double> = ArrayList()
    for (i in 1 until values.size) {
        logarithmicValues[i] = ln(values[i - 1] / values[i])
    }
    return logarithmicValues
}

/**
 * Поиск наименьшего делителя
 */
fun findSmallestDivisor(value: Double): Double {
    var divisor = 2.0
    while (value % divisor != 0.0) {
        divisor++
    }
    return divisor
}