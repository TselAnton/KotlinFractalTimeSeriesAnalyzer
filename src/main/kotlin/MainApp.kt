import math.calculateRSAnalyze
import utils.readCsv
import java.time.LocalDate

const val FILE_NAME = "G:\\IdeaProjects\\KotlinFractalTimeSeriesAnalyzer\\src\\main\\resources\\DataSet.csv"

fun main(args: Array<String>)  {
    val data = readCsv(FILE_NAME)

    val testData = mutableMapOf<LocalDate, Double>(
        Pair(LocalDate.of(2021, 1, 1), 20.0),
        Pair(LocalDate.of(2021, 1, 2), 30.0),
        Pair(LocalDate.of(2021, 1, 3), 35.0),
        Pair(LocalDate.of(2021, 1, 4), 40.0),
        Pair(LocalDate.of(2021, 1, 5), 45.0),
        Pair(LocalDate.of(2021, 1, 6), 50.0),
        Pair(LocalDate.of(2021, 1, 7), 55.0),
        Pair(LocalDate.of(2021, 1, 8), 60.0),
        Pair(LocalDate.of(2021, 1, 9), 65.0),
        Pair(LocalDate.of(2021, 1, 10), 70.0),
        Pair(LocalDate.of(2021, 1, 11), 75.0),
    )

    calculateRSAnalyze(data)
        .entries.stream()
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
