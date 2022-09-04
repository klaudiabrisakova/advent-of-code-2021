import java.io.File
import java.io.InputStream
import kotlin.math.sign

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine {
    lineList.add(it)
}

//lineList.forEach { println(it) }

data class Coordinates(val x1: Int, val y1: Int, val x2: Int, val y2: Int)

val data = lineList.map { line ->
    val (first, second) = line.split(" -> ")
    val (x1, y1) = first.split(",")
    val (x2, y2) = second.split(",")
    Coordinates(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt())
}

//data.forEach { println(it) }

val map = MutableList(1000) { MutableList(1000) { 0 } }

for (item in data) {
    val x1 = item.x1
    val y1 = item.y1
    val x2 = item.x2
    val y2 = item.y2
    println(item)
    if ((x1 != x2) && (y1 != y2)) continue
    if (x1 == x2) {
        if (y1 <= y2) {
            for (y in y1..y2) {
                map[x1][y]++
            }
        }
        if (y2 < y1) {
            for (y in y2..y1) {
                map[x1][y]++
            }
        }
    } else if (y1 == y2) {
        if (x1 <= x2) {
            for (x in x1..x2) {
                map[x][y1]++
            }
        }
        if (x2 < x1) {
            for (x in x2..x1) {
                map[x][y1]++
            }
        }
    }
}

//map.forEach { println(it.joinToString("")) }

println("Result ${map.flatten().count { it -> it >= 2 }}")