package utils

import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val logger: MyLogger = MyLogger.getInstance("CsvReader")

fun readCsv(fileName: String,
            dateColumnIndex: Int = 1,
            valueColumnIndex: Int = 2,
            spliterator: String = ";",
            datePattern: String = "dd.MM.yyyy",
            skipLines: Int = 0,
            outFormattedData: Boolean = false): Map<LocalDate, Double> {

    val resultSet = mutableMapOf<LocalDate, Double>()
    var skip = 0

    try {
        File(fileName)
            .forEachLine {
                if (skip >= skipLines) {
                    resultSet[getDate(it, spliterator, datePattern, dateColumnIndex)] =
                        getValue(it, spliterator, valueColumnIndex)
                }
                skip += 1
            }

    } catch (e: Exception) {
        logger.error(e)
    }

    val sortedMap = resultSet.toSortedMap(compareBy{it})

    if (outFormattedData) {
        printInFile(sortedMap)
    }
    return sortedMap
}

private fun getValue(line: String, spliterator: String, valueColumnIndex: Int): Double {
    return line.split(spliterator)[valueColumnIndex - 1].trim().replace(',', '.').toDouble()
}

private fun getDate(line: String, spliterator: String, datePattern: String, dateColumnIndex: Int): LocalDate {
    val date = line.split(spliterator)[dateColumnIndex - 1].trim()
    return LocalDate.parse(date, DateTimeFormatter.ofPattern(datePattern))
}

// Запись в файл
private fun printInFile(points: Map<LocalDate, Double>) {
    val firstDate: LocalDate = points.keys.firstOrNull()!!
    val file = File("formatted_data.txt").bufferedWriter()
    points.forEach { (t, u) -> file.write("${daysBetween(firstDate, t)};${u.toString().replace(".", ",")}\n") }
}

private fun daysBetween(d1: LocalDate, d2: LocalDate): Double {
    return (d2.toEpochDay() - d1.toEpochDay()).toDouble()
}
