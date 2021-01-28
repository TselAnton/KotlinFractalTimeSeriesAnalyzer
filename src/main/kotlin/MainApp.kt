import math.getMNKArguments
import math.getRSIndex
import utils.readCsv
import java.io.File

private val FILE_NAME = File("").absolutePath + "\\src\\main\\resources\\DataSet.csv"

fun main()  {
    val data = readCsv(FILE_NAME)
    val rsIndex = getRSIndex(data)
    getMNKArguments(rsIndex)
}

//fun main() = Window(title = "First App", size = IntSize(400, 400)) {
//    val count = remember { mutableStateOf(0) }
//    MaterialTheme {
//        Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {
//            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
//                onClick = {
//                    count.value++
//                }) {
//                Text(if (count.value == 0) "Hello World" else "Clicked ${count.value}!")
//            }
//            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
//                onClick = {
//                    count.value = 0
//                }) {
//                Text("Reset")
//            }
//        }
//    }
//}
