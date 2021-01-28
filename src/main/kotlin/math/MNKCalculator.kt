package math

import kotlin.math.atan
import kotlin.math.pow
import kotlin.math.tan

fun getMNKArguments(points: List<Pair<Double, Double>>) {
    val sumX = points.map { pair -> pair.first }.sum()
    val sumY = points.map { pair -> pair.second }.sum()
    val sumXY = points.map { pair -> pair.first * pair.second }.sum()
    val sumX2 = points.map { pair -> pair.first.pow(2) }.sum()
    val n = points.size

    val a = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX.pow(2))
    val b = (sumY - a * sumX) / n

    //todo: доделать - посчитать H
    println("a = $a, b = $b")
    println(atan(a))
    println(tan(a))
    println(atan(b))
    println(tan(b))
}