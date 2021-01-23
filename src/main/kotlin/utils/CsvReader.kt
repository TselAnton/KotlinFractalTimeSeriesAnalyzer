package utils

import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun readCsv(fileName: String): Map<LocalDate, Double> {
    val resultSet = mutableMapOf<LocalDate, Double>()
    File(fileName).forEachLine { resultSet[getDate(it)] = getPrice(it) }
    return resultSet
}

private fun getPrice(line: String): Double {
    return line.split(";")[1].trim().replace(',', '.').toDouble()
}

private fun getDate(line: String): LocalDate {
    val date = line.split(";")[0].trim()
    return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
}
