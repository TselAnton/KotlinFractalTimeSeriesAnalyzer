package math

import typesalias.PointList
import kotlin.math.pow

/**
 * Поиск линейной регрессии методом наименьших квадратов
 */
fun getMNKArguments(points: PointList): Pair<Double, Double> {
    val sumX = points.map { pair -> pair.first }.sum()
    val sumY = points.map { pair -> pair.second }.sum()
    val sumXY = points.map { pair -> pair.first * pair.second }.sum()
    val sumX2 = points.map { pair -> pair.first.pow(2) }.sum()
    val n = points.size

    val a = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX.pow(2))
    val b = (sumY - a * sumX) / n

    return Pair(a, b)
}
