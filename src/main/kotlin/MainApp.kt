import math.getRSIndex
import utils.readCsv
import java.lang.String.format
import java.time.LocalDate

const val FILE_NAME = "G:\\IdeaProjects\\KotlinFractalTimeSeriesAnalyzer\\src\\main\\resources\\DataSet.csv"

fun main()  {
    val data = readCsv(FILE_NAME)

    val testData = mutableMapOf<LocalDate, Double>(
        Pair(LocalDate.of(2021, 1, 1), 1.0),
        Pair(LocalDate.of(2021, 1, 2), 2.0),
        Pair(LocalDate.of(2021, 1, 3), 4.0),
        Pair(LocalDate.of(2021, 1, 4), 8.0),
        Pair(LocalDate.of(2021, 1, 5), 16.0),
        Pair(LocalDate.of(2021, 1, 6), 50.0),
        Pair(LocalDate.of(2021, 1, 7), 55.0),
        Pair(LocalDate.of(2021, 1, 8), 60.0),
        Pair(LocalDate.of(2021, 1, 9), 65.0),
        Pair(LocalDate.of(2021, 1, 10), 70.0),
        Pair(LocalDate.of(2021, 1, 12), 75.0),
        Pair(LocalDate.of(2021, 1, 13), 75.0),
        Pair(LocalDate.of(2021, 1, 14), 76.0),
        Pair(LocalDate.of(2021, 1, 15), 77.0),
        Pair(LocalDate.of(2021, 1, 16), 78.0),
        Pair(LocalDate.of(2021, 1, 17), 79.0),
        Pair(LocalDate.of(2021, 1, 18), 80.0),
        Pair(LocalDate.of(2021, 1, 19), 81.0),
        Pair(LocalDate.of(2021, 1, 20), 82.0),
        Pair(LocalDate.of(2021, 1, 21), 33.0),
        Pair(LocalDate.of(2021, 1, 22), 84.0),
        Pair(LocalDate.of(2021, 1, 23), 24.0),
        Pair(LocalDate.of(2021, 1, 24), 11.0),
        Pair(LocalDate.of(2021, 1, 25), 17.0),
        Pair(LocalDate.of(2021, 1, 26), 19.0),
        Pair(LocalDate.of(2021, 1, 27), 23.0),
        Pair(LocalDate.of(2021, 1, 28), 63.0),
        Pair(LocalDate.of(2021, 1, 29), 35.0),
        Pair(LocalDate.of(2021, 1, 30), 43.0)
    )

    println("Test data size = ${testData.size}")

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
