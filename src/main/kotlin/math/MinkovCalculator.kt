package math

import data.HerstIndex
import typesalias.Point
import utils.*
import java.time.LocalDate

private val logger: MyLogger = MyLogger.getInstance("MinkovCalculator")

/**
 * Расчёт показателя Хёрста методом Миньковского
 */
fun getMinkovIndex(map: Map<LocalDate, Double>): HerstIndex {
    val minkPoints = mutableListOf<Point>() //todo: добавить проверку на размер массива, чтобы он был кратен 2-м
    val points = normalizePoints(map)   // Нормализуем точки

    logger.debug("min data value = ${map.values.minOrNull()}")

    logger.debug("max x = ${points.maxX()}, min x = ${points.minX()}")
    logger.debug("max y = ${points.maxY()}, min y = ${points.minY()}")

    var divisor = 1 // Берём первый наименьший делитель. Он будет длинной 1 квадрата



//    while (divisor <= logarithmicValues.size) {
//
//    }

    return HerstIndex(0.0, 0.0, minkPoints)
}

/**
 * Нормализуем точки
 */
private fun normalizePoints(map: Map<LocalDate, Double>): List<Point> {
    if (map.isEmpty()) {
        return mutableListOf()
    }

    val minY = map.values.minOrNull() ?: error("Can't find min for map values")
    val minX = map.keys.get(0) ?: error("Can't find first element for map values")
    val resultPoints = mutableListOf<Point>()

    for (e in map.entries) {
        resultPoints.add(Point(daysBetween(minX, e.key), normalizeY(e.value, minY)))
    }

    return resultPoints
}

private fun normalizeY(y: Double, minY: Double): Double {
    return if (minY <= 0) y + minY else y - minY
}

private fun daysBetween(d1: LocalDate, d2: LocalDate): Double {
    return (d2.toEpochDay() - d1.toEpochDay()).toDouble()
}



private fun findBiggestDivisor(value: Int, previousDivisor: Int): Int {
    val divisor = previousDivisor + 1   //todo

    for (div in divisor until (value / 2) + 1) {
        if (value % div == 0) {
            return div
        }
    }
    return Int.MAX_VALUE
}