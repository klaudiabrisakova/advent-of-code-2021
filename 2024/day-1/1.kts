import java.io.File
import java.io.InputStream
import kotlin.math.abs

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val left = mutableListOf<Int>()
val right = mutableListOf<Int>()
lineList
    .map { line ->
        val split = line.split("   ")
        left.add(split.first().toInt())
        right.add(split[1].toInt())
    }

left.sort()
right.sort()

var sum = 0
left.forEachIndexed { index, number ->
    sum += abs(number - right[index])
}

println(sum)
