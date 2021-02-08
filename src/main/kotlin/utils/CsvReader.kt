package utils

import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val logger: MyLogger = MyLogger.getInstance("CsvReader")

fun readCsv(fileName: String): Map<LocalDate, Double> {
    val resultSet = mutableMapOf<LocalDate, Double>()

    try {
        File(fileName).forEachLine { resultSet[getDate(it)] = getValue(it) }
    } catch (e: Exception) {
        logger.error(e)
    }

    return resultSet
}

private fun getValue(line: String): Double {
    return line.split(";")[1].trim().replace(',', '.').toDouble()
}

private fun getDate(line: String): LocalDate {
    val date = line.split(";")[0].trim()
    return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
}
