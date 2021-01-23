package math

import utils.MyLogger
import java.time.LocalDate
import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Файл функций для рассчёта показателя Херста методом R/S анализа
 */

val logger: MyLogger = MyLogger.getInstance("RSCalculator")


/**
 * Функция подсчёта RS анализа
 */
fun calculateRSAnalyze(map: Map<LocalDate, Double>): Map<Int, Double> {
    val logarithmicValues = convertToLogarithmicSeries(map)
    var divisor = findSmallestDivisor(logarithmicValues.size, null)

    logger.debug("Size of map is ${logarithmicValues.size}")
    logger.debug("First divisor is $divisor")

    var resultMap = mutableMapOf<Int, Double>()

    while (divisor <= logarithmicValues.size) {

        //todo: доделать алгоритм


        resultMap[divisor] = 1.0    // todo: R/S результат здесь вместо 1.0

        divisor = findSmallestDivisor(logarithmicValues.size, divisor)  // Находим следующий наименьший делитель
        logger.debug("Next divisor is $divisor")
    }

    return resultMap
}

/**
 * Перевод значений в логарифмический ряд
 */
private fun convertToLogarithmicSeries(mapValues: Map<LocalDate, Double>): List<Double> {
    val logarithmicValues: ArrayList<Double> = ArrayList()
    val values = ArrayList(mapValues.values)
    val expectedSize = if (mapValues.size % 2 == 0) mapValues.size - 1 else mapValues.size

    for (i in 1 until expectedSize) {
        logarithmicValues.add(ln(values[i - 1]!!.div(values[i]!!)))
    }
    return logarithmicValues
}

/**
 * Поиск наименьшего делителя
 */
private fun findSmallestDivisor(value: Int, previousDivisor: Int?): Int {
    val firstDiv = if (previousDivisor == null) 10 else previousDivisor + 1

    for (div in firstDiv until value + 1) {
        if (value % div == 0) {
            return div
        }
    }
    return Int.MAX_VALUE
}





/**
 * Среднее значение
 */
private fun averageValue(values: List<Double>): Double {
    // TODO: Наверное, стоит это использовать напрямую без отдельной функци
    return values.average()
}

/**
 * Стандартное отклонение S
 */
private fun standardDeviation(values: List<Double>): Double {
    return sqrt(values.sum().pow(2.0) / values.count())
}
