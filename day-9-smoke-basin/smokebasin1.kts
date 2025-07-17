import java.io.File
import java.io.InputStream

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<List<Int>>()

inputStream.bufferedReader().forEachLine {
    lineList.add(it.split("").filterNot { it.equals("") }.map { it.toInt() })
}

//lineList.forEach { println(it.joinToString("")) }

val lowestPoints = mutableListOf<Int>()

val maxX = lineList.first().size
val maxY = lineList.size

lineList.mapIndexedNotNull { y, line ->
    println(line.joinToString(""))
    val result = line.mapIndexedNotNull { x, number ->
        val neighbors = listOf<Pair<Int, Int>>(Pair(x + 1, y), Pair(x, y + 1), Pair(x - 1, y), Pair(x, y - 1))
            .filter { it.first in 0..maxX - 1 && it.second in 0..maxY - 1 }
            .map { lineList[it.second][it.first] }
//        println("neighbors=$neighbors number=$number")
        if (neighbors.all { number < it }) number else null
    }
    println(result)
    if (result.isNotEmpty()) result else null
}
    .flatten()
    .sumOf { it + 1 }
