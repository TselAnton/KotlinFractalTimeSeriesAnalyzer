package utils

import typesalias.Bitmap
import typesalias.Point
import java.lang.StringBuilder
import kotlin.math.roundToInt

fun <K, V> Map<K, V>.shuffle(): Map<K, V> {
    val shuffledKeys = this.keys.shuffled()
    val result = mutableMapOf<K, V>()

    for (key in shuffledKeys) {
        result[key] = this[key] ?: error("Can't find element by key = $key")
    }

    return result
}

fun <K> Set<K>.get(index: Long): K? {
    return this.stream()
            .skip(index)
            .findFirst()
            .orElse(null)
}

fun List<Point>.maxIntX(defaultValue: Int = 0): Int {
    val result = this.map { it.first }
        .toList()
        .maxOrNull()

    return result?.roundToInt() ?: defaultValue
}

fun List<Point>.maxIntY(defaultValue: Int = 0): Int {
    val result = this.map { it.second }
        .toList()
        .maxOrNull()

    return result?.roundToInt() ?: defaultValue
}

fun Bitmap.countOfBoxes(): Int {
    return this.map { array -> array.sum() }.sum()
}

fun Bitmap.getToString(): String {
    val toString = StringBuilder()
    for (x in this.indices) {
        for (y in this[x].indices) {
            toString.append(this[x][y])
            toString.append(" ")
        }
        toString.append("\n")
    }
    return toString.toString()
}
