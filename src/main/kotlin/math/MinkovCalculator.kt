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
    val points = normalizePoints(map)   // Нормализуем точки

    val xLength = getXLength(points)    // Берём количество точек по горизонтали кратное двум
    val yLength = points.maxIntY()
    val bitmap: Array<Array<Byte>> = buildBitmap(points, xLength, yLength) // Строим битмат единичных отрезков

    val minkPoints = mutableListOf<Point>()
    var divisor = 1 // Берём первый наименьший делитель. Он будет длинной 1 квадрата

    while (divisor <= xLength / 2) {

        divisor = findSmallestDivisor(xLength, divisor) // Поиск следующего делителя
    }

    return HerstIndex(0.0, 0.0, minkPoints)
}

/**
 * Строим карту пикселей, которые содержат в себе график
 * 1 — пиксель содержит в себе график, иначе 0
 * На основе этой карты будут строиться все более крупные карты
 */
 fun buildBitmap(points: List<Point>, xLength: Int, yLength: Int): Array<Array<Byte>> {
    val bitmap: Array<Array<Byte>> = arrayOf()

    for (x in 0 until xLength) {
        bitmap.set(x, arrayOf())

        val xPoint = x + 0.5
        val p1 = chooseFirstPoint(points, x)
        val p2 = points[x + 1]

        println("point1 = $p1, point2 = $p2")

        for (y in 0 until yLength) {
            bitmap[x][y] = 1
        }
    }

    return bitmap
}

private fun chooseFirstPoint(points: List<Point>, x: Int): Point {
    return when (x) {
        points[x].first.toInt() -> points[x]
        0 -> Point(0.0, 0.0)
        else -> points[x - 1]
    }
}

private fun getFunValue(p1: Point, p2: Point, xPoint: Double): Double {
    return (xPoint - p1.first) * (p2.second - p1.second) / (p2.first - p1.first) + p1.second
}

/**
 * Получение длины ряда в у.е.
 * Если значение не кратно 2, то добавляется ещё одна клетка в конец
 */
private fun getXLength(points: List<Point>): Int {
    val maxX = points.maxIntX()
    return if (maxX % 2 == 0) maxX else maxX + 1
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

/**
 * Поиск наименьшего делителя
 */
private fun findSmallestDivisor(value: Int, previousDivisor: Int = 0): Int {
    val divisor = previousDivisor + 1

    for (div in divisor until (value / 2) + 1) {
        if (value % div == 0) {
            return div
        }
    }
    return Int.MAX_VALUE
}
