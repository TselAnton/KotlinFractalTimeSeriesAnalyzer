package math

import kotlin.math.ln
import kotlin.math.pow

fun getMNKArguments(points: List<Pair<Double, Double>>) {
    val linerArgs = findLinerArguments(points)
    println(linerArgs)
}

private fun findLinerArguments(points: List<Pair<Double, Double>>): Pair<Double, Double> {
    val logPoints = points.map { pair -> Pair(ln(pair.first), ln(pair.second)) }
    val sumX = logPoints.map { pair -> pair.first }.sum()
    val sumY = logPoints.map { pair -> pair.second }.sum()
    val sumXY = logPoints.map { pair -> pair.first * pair.second }.sum()
    val sumX2 = logPoints.map { pair -> pair.first.pow(2) }.sum()
    val n = logPoints.size

    val a = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX.pow(2))
    val b = (sumY - a * sumX) / n

    return Pair(a, b)
}