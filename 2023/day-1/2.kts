import java.io.File
import java.io.InputStream
import kotlin.contracts.contract

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val list = mutableListOf<MutableList<Int>>()
list.add(mutableListOf())
var index = 0

val numbers = mapOf(
    "one" to "o1e",
    "two" to "t2o",
    "three" to "t3e",
    "four" to "f4r",
    "five" to "f5e",
    "six" to "s6x",
    "seven" to "s7n",
    "eight" to "e8t",
    "nine" to "n9e"
)

lineList
    .map { line ->
        var newline = line
        numbers.forEach {
            newline = newline.replace(it.key, it.value)
        }
        newline
    }
    .map { line: String ->
        val number = ("" + line.first { it.isDigit() } + line.last { it.isDigit() }).toInt()
        println(number)
        number
    }.sum()

