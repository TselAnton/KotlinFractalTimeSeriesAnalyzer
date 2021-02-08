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
import math.buildBitmap
import math.getMinkovIndex
import math.getRSIndex

import typesalias.Point
import utils.maxIntX
import utils.maxIntY
import utils.readCsv
import utils.shuffle
import java.io.File
import kotlin.math.roundToInt

private val FILE_NAME = File("").absolutePath + "\\src\\main\\resources\\DataSet.csv"

private fun getXLength(points: List<Point>): Int {
    val maxX = points.maxIntX()
    return if (maxX % 2 == 0) maxX else maxX + 1
}

fun main()  {
    val points = mutableListOf<Point>(
        Point(1.0, 2.8),
        Point(2.0, 4.56),
        Point(3.0, 5.0),
        Point(5.0, 3.9),
        Point(6.0, 2.8),
        Point(7.0, 2.0),
        Point(9.0, 2.3),
        Point(10.0, 2.8),
    )

    val xLength = getXLength(points)    // Берём количество точек по горизонтали кратное двум
    val yLength = points.maxIntY()
    val bitmap = buildBitmap(points, xLength, yLength)
    println(bitmap)



//    val data = readCsv(FILE_NAME)
//    val rsIndex = getRSIndex(data)
//    val shuffledRsIndex = getRSIndex(data.shuffle())
//
//    val minkIndex = getMinkovIndex(data)

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


