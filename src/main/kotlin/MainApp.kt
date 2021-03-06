//import androidx.compose.desktop.Window
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material.Button
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.IntSize
//import androidx.compose.ui.unit.dp
import math.*

import utils.*
import java.io.File

private val FILE_NAME = File("").absolutePath + "\\src\\main\\resources\\DataSet.csv"
private val OTHER_FILE = "G:\\Downloads\\GAZP_000101_210213.csv"

fun main()  {

    val data = readCsv(OTHER_FILE, 3, 8, ",", "ddMMyy", 1)
    val rsIndex = getRSIndex(data)

    println("koef k = ${rsIndex.k}, b = ${rsIndex.b}")
    rsIndex.logPoints
        .stream()
        .map { Pair(it.first.toString().replace('.', ','), it.second.toString().replace('.', ',')) }
        .forEach { println("${it.first}  ${it.second}") }

//    Window(title = "First App", size = IntSize(400, 400)) {
//        val count = remember { mutableStateOf(0) }
//        MaterialTheme {
//            Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {
//                Button(modifier = Modifier.align(Alignment.CenterHorizontally),
//                        onClick = {
//                            count.value++
//                        }) {
//                    Text(if (count.value == 0) "Hello World" else "Clicked ${count.value}!")
//                }
//                Button(modifier = Modifier.align(Alignment.CenterHorizontally),
//                        onClick = {
//                            count.value = 0
//                        }) {
//                    Text("Reset")
//                }
//            }
//        }
//    }
}


