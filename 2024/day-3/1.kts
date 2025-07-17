import java.io.File
import java.io.InputStream
import kotlin.math.abs

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val instructions = lineList
    .reduce { acc, s -> acc + s }

val regex = Regex("""mul\(\d{1,3},\d{1,3}\)""")

regex.findAll(instructions).fold(0) {acc, it ->
    val numbersOnly = it.value.drop(4).dropLast(1)
    val numbers = numbersOnly.split(",")
    acc + numbers.first().toInt() * numbers[1].toInt()
}