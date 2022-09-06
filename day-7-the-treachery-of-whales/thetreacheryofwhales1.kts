import java.io.File
import java.io.InputStream
import kotlin.math.abs
import kotlin.math.min

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine {
    lineList.add(it)
}

val positions = lineList.first().split(",").map { it.toInt() }.toList()
//positions.forEach { print(it) }

var minimum = Int.MAX_VALUE
for (i in positions.min()..positions.max()) {
    val distance = positions.map { abs(it - i) }.sum()
    if (distance < minimum) {
        minimum = distance
        println("$minimum $i")
    }
}

println("Result: ${minimum}")