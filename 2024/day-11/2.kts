import java.io.File
import java.io.InputStream
import kotlin.math.abs

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<List<String>>()

inputStream.bufferedReader().forEachLine { line -> lineList.add(line.split(" ").filter { it.isNotBlank() }) }

var stones = lineList.first()
for (i in 1..10) {
    stones = stones
        .flatMap {
            if (it == "0") {
                listOf("1")
            } else if (it.length % 2 == 0) {
                val firstHalf = it.substring(0, endIndex = it.length / 2)
                val secondHalf = it.substring(it.length / 2).trimStart { ch -> ch == '0' }
                listOf(firstHalf, secondHalf.ifBlank { "0" })
            } else {
                listOf("${it.toLong() * 2024}")
            }
        }
//    println(i)
    println(stones)
}

stones.count()

