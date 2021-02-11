package math

import data.HerstIndex
import typesalias.Bitmap

import typesalias.Point
import utils.*
import java.time.LocalDate
import kotlin.math.ln
import kotlin.math.roundToInt

private val logger: MyLogger = MyLogger.getInstance("MinkovCalculator")
private const val PAINTED_CELL: Byte = 1.toByte()

/**
 * Расчёт показателя Хёрста методом Миньковского
 */
fun getMinkovIndex(map: Map<LocalDate, Double>, startBlockSize: Int = 1): HerstIndex {
    val points = normalizePoints(map)   // Нормализуем точки

    val xLength = points.maxIntX()
    val yLength = points.maxIntY()

    val minkPoints = mutableListOf<Point>()
    var blockSize = startBlockSize   // Стартовый размер ячейки

    while (blockSize <= xLength / 2) {
        logger.debug("Block size = $blockSize")
        val bitmap = buildBitmap(points, blockSize, xLength, yLength) // Строим сетку определённой длинны
        logger.debug("Bitmap")
        logger.debug("${bitmap.getToString()} \n")
        minkPoints.add(Point(ln(1.0 / blockSize), ln(bitmap.countOfBoxes().toDouble())))
        blockSize *= 2  // Увеличиваем клетку вдвоеk
    }

    val args = getMNKArguments(minkPoints)

    return HerstIndex(args.first, args.second, minkPoints)
}

/**
 * Построение битовой карты
 */
private fun buildBitmap(points: List<Point>, blockSize: Int, xLength: Int, yLength: Int): Bitmap {
    val bitmapXLength = (xLength.toDouble() / blockSize.toDouble() ).roundToInt()
    val bitmapYLength = (yLength.toDouble() / blockSize.toDouble() ).roundToInt() + 1

    val bitmap: Bitmap = Array(bitmapXLength) { Array(bitmapYLength) { 0 } }

    for (x in 0 until bitmapXLength) {

        val xMiddlePoint: Double = x * blockSize + (blockSize / 2.0)   // Находим середину отрезка квадрата

        val firstPoint = getFirstPoint(points, xMiddlePoint)    // Находим индекс первой рассматриваемой точки
        val secondPoint = getSecondPoint(points, firstPoint)  // Находим индекс второй рассматриваемой точки

        if (firstPoint != null && secondPoint != null) {    // Если есть что рассматривать
            val funValue = getFunValue(firstPoint, secondPoint, xMiddlePoint)   // Считаем значение функции
            bitmap[x][funValue.toInt() / blockSize] = PAINTED_CELL
        }
    }

    return bitmap
}

/**
 * Выбор индекса первой точки, подходящей под текущую клетку
 * Если точка лежит в пределах текущей клетке, то будет выбрана она
 * Если в данной клетке нет точки, то будет выбрана предыдущая точка
 * Если предыдущих точек нет, то будет возвращено null
 */
private fun getFirstPoint(points: List<Point>, xMiddle: Double): Point? {
    return points
            .filter { p -> p.first < xMiddle }
            .maxByOrNull { p -> p.first }
}

/**
 * Выбор второй точки
 * Если первая точка равна null, то и вторая будет null
 * Если первая точка не последняя, будет возвращена следующая за ней клетка
 * Если первая точка последняя, то будет возвращено null
 */
private fun getSecondPoint(points: List<Point>, firstPoint: Point?): Point? {
    return if (firstPoint != null) {
        val indexOfFirstPoint = points.indexOf(firstPoint)

        if (indexOfFirstPoint != points.lastIndex) points[points.indexOf(firstPoint) + 1]
        else null
    } else null
}

/**
 * Получение значения функции прямой по двум точкам и проверочному значению
 */
private fun getFunValue(p1: Point, p2: Point, xPoint: Double): Double {
    return (xPoint - p1.first) * (p2.second - p1.second) / (p2.first - p1.first) + p1.second
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