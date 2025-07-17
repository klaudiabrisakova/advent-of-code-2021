import java.io.File
import java.io.InputStream
import kotlin.math.abs

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

lineList
    .map { line ->
        line.split(" ").map { it.toInt() }
    }
    .map { it.windowed(2).map { window -> window.first() - window[1] } }
    .filter { it.all { number -> number > 0 } || it.all { number -> number < 0 } }
    .filter { it.all { number -> abs(number) in 1..3 } }
    .count()
