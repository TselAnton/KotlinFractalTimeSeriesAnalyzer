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
fun getRSIndex(map: Map<LocalDate, Double>): Map<Int, Double> {
    val logarithmicValues = convertToLogarithmicSeries(map) // Получение логарифмических значений
    var divisor = findSmallestDivisor(logarithmicValues.size, null) // Находим первый делитель

    logger.debug("Size of logarithmic values is ${logarithmicValues.size}")

    val resultMap = mutableMapOf<Int, Double>()

    while (divisor <= logarithmicValues.size) {
        val subLists = splitIntoSubArrays(logarithmicValues, divisor)   // Разделяем лист на подлисты длинны divisor
        val averageValues: List<Double> = subLists.map { list -> list.average() }   // Считаем средние значения для каждой группы
        val accumulatedDeviations = calculateAccumulatedDeviations(subLists, averageValues) // Считаем средние отклонения
        val r = calculateR(accumulatedDeviations)   // Считаем нормированный размах по каждой группе
        val s = calculateS(subLists, averageValues) // Считаем стандартное отклонение

        resultMap[divisor] = calculateRS(r, s)  // Считаем R/S показатель

        divisor = findSmallestDivisor(logarithmicValues.size, divisor)  // Находим следующий наименьший делитель
    }

    return resultMap
}

/**
 * Перевод значений в логарифмический ряд
 */
private fun convertToLogarithmicSeries(mapValues: Map<LocalDate, Double>): List<Double> {
    val logarithmicValues: ArrayList<Double> = ArrayList()
    val values = ArrayList(mapValues.values)
    val expectedSize = if (values.size % 2 == 0) values.size - 1 else values.size

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

    for (div in firstDiv until (value / 2) + 1) {
        if (value % div == 0) {
            return div
        }
    }
    return Int.MAX_VALUE
}

/**
 * Разделить массив на подмассивы определённой длины
 */
private fun splitIntoSubArrays(logarithmicValues: List<Double>, divisor: Int): List<List<Double>> {
    val resultList = mutableListOf<List<Double>>()
    val subSize = logarithmicValues.size / divisor

    for (i in 0 until subSize) {
        resultList.add(logarithmicValues.subList(i * divisor, (divisor + divisor * i)))
    }
    return resultList
}

/**
 * Накопленные отклонения от среднего
 */
private fun calculateAccumulatedDeviations(values: List<List<Double>>, average: List<Double>): List<List<Double>> {
    val resultList = mutableListOf<List<Double>>()

    for (i in values.indices) {
        val subList = mutableListOf<Double>()

        for (j in values[i].indices) {
            if (j == 0) {
                subList.add(values[i][j] - average[i])
            } else {
                subList.add(values[i][j] - average[i] + subList[j - 1])
            }
        }

        resultList.add(subList)
    }
    return resultList
}

/**
 * Нормированный размах по каждой группе
 */
private fun calculateR(accumulatedDeviations: List<List<Double>>): List<Double> {
    val resultList = mutableListOf<Double>()

    for (i in accumulatedDeviations.indices) {
        val max: Double? = accumulatedDeviations[i].maxOrNull()
        val min: Double? = accumulatedDeviations[i].minOrNull()

        if (max != null && min != null) {
            resultList.add(max - min)
        }
    }

    return resultList
}

/**
 * Стандартное отклонение S
 */
private fun calculateS(subList: List<List<Double>>, averageValues: List<Double>): List<Double> {
    val resultList = mutableListOf<Double>()

    for (i in subList.indices) {
        val average = mutableListOf<Double>()

        for (j in subList[i].indices) {
            average.add((subList[i][j] - averageValues[i]).pow(2))
        }
        resultList.add(average.average())
    }
    return resultList
}

/**
 * Рассчитываем показатель R/S
 */
private fun calculateRS(r: List<Double>, s: List<Double>): Double {
    val resultList = mutableListOf<Double>()

    for (i in r.indices) {
        resultList.add(r[i] / s[i])
    }
    return resultList.average()
}
