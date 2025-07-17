import java.io.File
import java.io.InputStream
import kotlin.math.abs

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val instructions = lineList
    .reduce { acc, s -> acc + s }

val regex = Regex("""mul\(\d{1,3},\d{1,3}\)|do\(\)|don't\(\)""")

var enabled = true
regex.findAll(instructions).fold(0) {acc, it ->
    if (it.value == "do()") {
        enabled = true
        acc
    } else if (it.value == "don't()") {
        enabled = false
        acc
    } else if (enabled) {
        val numbersOnly = it.value.drop(4).dropLast(1)
        val numbers = numbersOnly.split(",")
        acc + numbers.first().toInt() * numbers[1].toInt()
    } else {
        acc
    }
}