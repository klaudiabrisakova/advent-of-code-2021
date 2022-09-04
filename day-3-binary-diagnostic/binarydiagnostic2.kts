import java.io.File
import java.io.InputStream
import kotlin.contracts.contract

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine {
    lineList.add(it)
}

fun mostCommon(list: List<String>, index: Int): List<String> {
    val (ones, zeros) = list.partition { it[index].toString() == "1" }
    if (ones.size == zeros.size) {
        return ones
    } else if (ones.size > zeros.size) {
        return ones
    } else {
        return zeros
    }
}

fun leastCommon(list: List<String>, index: Int): List<String> {
    val (ones, zeros) = list.partition { it[index].toString() == "1" }
    if (ones.size == zeros.size) {
        return zeros
    } else if (ones.size > zeros.size) {
        return zeros
    } else {
        return ones
    }
}

var oxygenGeneratorRating = mostCommon(lineList, 0)
var co2ScrubberRating = leastCommon(lineList, 0)
println("${oxygenGeneratorRating.size} ${co2ScrubberRating.size}")
println("${oxygenGeneratorRating} ${co2ScrubberRating}")

for (i in 1..11) {
    oxygenGeneratorRating = mostCommon(oxygenGeneratorRating, i)
    co2ScrubberRating = leastCommon(co2ScrubberRating, i)
    println("${oxygenGeneratorRating.size} ${co2ScrubberRating.size}")
    println("${oxygenGeneratorRating} ${co2ScrubberRating}")
}

val o = Integer.parseInt("110101110011", 2)
val c = Integer.parseInt("010101001101", 2)

println("$o $c ${o * c}")


