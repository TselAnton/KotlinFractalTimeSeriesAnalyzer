package utils

import typesalias.Point

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

fun List<Point>.maxX(): Double? {
    return this.map { it.first }
        .toList()
        .maxOrNull()
}

fun List<Point>.maxY(): Double? {
    return this.map { it.second }
        .toList()
        .maxOrNull()
}

fun List<Point>.minX(): Double? {
    return this.map { it.first }
        .toList()
        .minOrNull()
}

fun List<Point>.minY(): Double? {
    return this.map { it.second }
        .toList()
        .minOrNull()
}