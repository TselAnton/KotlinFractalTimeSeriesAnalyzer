package math

import data.HerstIndex
import typesalias.Bitmap

import typesalias.Point
import utils.*
import java.time.LocalDate
import kotlin.math.ln

private val logger: MyLogger = MyLogger.getInstance("MinkovCalculator")
private const val FILL: Byte = 1.toByte()

/**
 * Расчёт показателя Хёрста методом Миньковского
 */
fun getMinkovIndex(map: Map<LocalDate, Double>): HerstIndex {
    val points = normalizePoints(map)   // Нормализуем точки

    val length = getLength(points)    // Длина стороны сетки всегда будет квадратной
    val bitmap: Bitmap = buildBitmap(points, length) // Строим битмат единичных отрезков

    val minkPoints = mutableListOf(Point(ln(1.0), ln(bitmap.countOfBoxes().toDouble())))
    var divisor = findSmallestDivisor(length) // Берём наименьший делитель > 1

    while (divisor <= length / 2) {
        val newBitmap = buildScopeBitmap(bitmap, length, divisor)
        minkPoints.add(minkPoints.size - 1, Point(ln(1.0 / divisor), ln(newBitmap.countOfBoxes().toDouble())))
        divisor = findSmallestDivisor(length, divisor) // Поиск следующего делителя
    }

    val args = getMNKArguments(minkPoints)

    return HerstIndex(args.first, args.second, minkPoints)
}



fun buildScopeBitmap(bitmap: Bitmap, length: Int, divisor: Int): Bitmap {
    val resultBitmap: Bitmap = Array(length / divisor) { Array(length / divisor) { 0 } }
    for (x in 0 until length / divisor) {
        for (y in 0 until length / divisor) {
            findBoxes@ for (i in 0 until divisor) {
                for (j in 0 until divisor) {
                    if (bitmap[i + x * divisor][j + y * divisor] == FILL) {
                        resultBitmap[x][y] = FILL
                        break@findBoxes
                    }
                }
            }
        }
    }
    return resultBitmap
}

/**
 * Строим карту пикселей, которые содержат в себе график
 * 1 — пиксель содержит в себе график, иначе 0
 * На основе этой карты будут строиться все более крупные карты
 */
private fun buildBitmap(points: List<Point>, length: Int): Bitmap {
    val bitmap: Bitmap = Array(length) { Array(length) { 0 } }

    for (x in 0 until length) {

        val xPoint = x + 0.5
        val firstIndex = chooseFirstPointIndex(points, x)
        val secondIndex = chooseSecondPointIndex(firstIndex)

        for (y in 0 until length) {
            if (firstIndex != -1 && secondIndex != -1) {
                val funValue = getFunValue(points[firstIndex], points[secondIndex], xPoint)
                if (funValue >= y && funValue < y + 1) {
                    bitmap[x][y] = FILL
                }
            }
        }
    }

    return bitmap
}

/**
 * Выбор индекса первой точки, подходящей под текущую клетку
 * Если точка лежит в пределах этой точки, то будет выбрана она
 * Если в данной клетке нет точки, то будет выбрана предыдущая точка
 * Если предыдущих точек нет, то будет возвращено -1
 */
private fun chooseFirstPointIndex(points: List<Point>, x: Int): Int {
    var resultIndex: Int = -1
    for (index in points.indices) {
        if (points[index].first < x + 1) {
            resultIndex = index
        } else if (points[index].first > x + 1) {
            return resultIndex
        }
    }
    return resultIndex
}

/**
 * Выбор следующей точки для текущей клетки
 * Если первый индекс равен -1, то и второй равен -1
 * Иначе берётся следующая точка за выбранной первой
 */
private fun chooseSecondPointIndex(p1: Int): Int {
    return if (p1 == -1) -1 else p1 + 1
}

/**
 * Получение значения функции прямой по двум точкам и проверочному значению
 */
private fun getFunValue(p1: Point, p2: Point, xPoint: Double): Double {
    return (xPoint - p1.first) * (p2.second - p1.second) / (p2.first - p1.first) + p1.second
}

/**
 * Рассчёт длинны стороны сетки
 * Сетка всегда будет квадратная, поэтому берётся максимальная сторона прямоугольника кратная двум
 */
private fun getLength(points: List<Point>): Int {
    val max = if (points.maxIntX() >= points.maxIntY()) points.maxIntX() else points.maxIntY()
    return if (max % 2 == 0) max else max + 1
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

/**
 * Нормализация значения y так, чтобы все значения находились в первой координатной четверти
 */
private fun normalizeY(y: Double, minY: Double): Double {
    return if (minY <= 0) y + minY else y - minY
}

/**
 * Рассчёт дней между двумя датами
 */
private fun daysBetween(d1: LocalDate, d2: LocalDate): Double {
    return (d2.toEpochDay() - d1.toEpochDay()).toDouble()
}

/**
 * Поиск наименьшего делителя
 */
private fun findSmallestDivisor(value: Int, previousDivisor: Int = 1): Int {
    val divisor = previousDivisor + 1

    for (div in divisor until (value / 2) + 1) {
        if (value % div == 0) {
            return div
        }
    }
    return Int.MAX_VALUE
}
