import math.getRSIndex
import utils.readCsv
import java.lang.String.format
import java.time.LocalDate

const val FILE_NAME = "G:\\IdeaProjects\\KotlinFractalTimeSeriesAnalyzer\\src\\main\\resources\\DataSet.csv"

fun main()  {
    val data = readCsv(FILE_NAME)
    getRSIndex(data)
        .entries.stream()
            .map { entry -> format("%s %s", entry.key, entry.value.toString().replace(".", ","))  }
        .forEach(System.out::println)
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
