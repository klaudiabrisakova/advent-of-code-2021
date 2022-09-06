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
    val distance = positions.map {
        // https://math.stackexchange.com/questions/60578/what-is-the-term-for-a-factorial-type-operation-but-with-summation-instead-of-p
        val d = abs(it - i)
        (d*d+d)/2
    }.sum()
    if (distance < minimum) {
        minimum = distance
        println("$minimum $i")
    }
}

println("Result: ${minimum}")