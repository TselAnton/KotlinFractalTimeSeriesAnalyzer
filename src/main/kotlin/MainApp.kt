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
import math.getRSIndex
import utils.readCsv
import java.io.File
import java.time.LocalDate

private val FILE_NAME = File("").absolutePath + "\\src\\main\\resources\\DataSet.csv"

fun main()  {
    val data = readCsv(FILE_NAME)
    val rsIndex = getRSIndex(data)
    val shuffledRsIndex = getRSIndex(getShuffledData(data))

    println(rsIndex)
    println(shuffledRsIndex)

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

private fun getShuffledData(data: Map<LocalDate, Double>): Map<LocalDate, Double> {
    val shuffledKeys: List<LocalDate> = data.keys.shuffled()
    val result = mutableMapOf<LocalDate, Double>()
    for (key in shuffledKeys) {
        result[key] = data.getOrElse(key) { 0.0 }
    }

    return result
}
