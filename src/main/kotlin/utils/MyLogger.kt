package utils

import java.io.File
import java.lang.String.format
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")!!

const val loggerLevelProperty = "logger.level"

class MyLogger private constructor(private val loggerName: String) {

    private val level: Level = getLoggerLevel()

    companion object {
        fun getInstance(loggerName: String) = MyLogger(loggerName)
    }

    fun info(message: Any?) {
        if (level.number == 1) {
            printMessage(message, Level.INFO)
        }
    }

    fun debug(message: Any?) {
        if (level.number <= 2) {
            printMessage(message, Level.DEBUG)
        }
    }

    fun warn(message: Any?) {
        if (level.number <= 3) {
            printMessage(message, Level.WARN)
        }
    }

    fun error(message: Any?) {
        if (level.number <= 4) {
            printMessage(message, Level.ERROR)
        }
    }

    private fun printMessage(message: Any?, level: Level) {
        println(format("%5s | %19s | %s: %s", level, LocalDateTime.now().format(dateFormatter),
            this.loggerName, message))
    }
}

private fun getLoggerLevel(): Level {
    return File(File("").absolutePath + "\\gradle.properties")
        .readLines()
        .filter { line -> line.contains(loggerLevelProperty) }
        .map { line -> line.split("=")[1] }
        .map { level -> Level.valueOf(level.toUpperCase())}
        .first()
        .also { Level.INFO }
}

private enum class Level(val level: String, val number: Int) {
    INFO("info", 1),
    DEBUG("debug", 2),
    WARN("warn", 3),
    ERROR("error", 4),
    OFF("off", 5)
}
