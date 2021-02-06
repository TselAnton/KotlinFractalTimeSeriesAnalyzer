package math

import typesalias.Point
import kotlin.math.pow

/**
 * Поиск линейной регрессии методом наименьших квадратов
 */
fun getMNKArguments(points: List<Point>): Point {
    val sumX = points.map { point -> point.first }.sum()
    val sumY = points.map { point -> point.second }.sum()
    val sumXY = points.map { point -> point.first * point.second }.sum()
    val sumX2 = points.map { point -> point.first.pow(2) }.sum()
    val n = points.size

    val a = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX.pow(2))
    val b = (sumY - a * sumX) / n

    return Point(a, b)
}
