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

fun basin(x: Int, y: Int): Set<Pair<Int, Int>> {
    if (lineList[y][x] == 9) {
        return setOf<Pair<Int, Int>>()
    }
    val neighbors = listOf<Pair<Int, Int>>(Pair(x + 1, y), Pair(x, y + 1), Pair(x - 1, y), Pair(x, y - 1))
        .filter { it.first in 0..maxX - 1 && it.second in 0..maxY - 1 }
        .associate { it to lineList[it.second][it.first] }
//    println(neighbors.values)

    val n = neighbors.filter { it.value > lineList[y][x] }
        .keys
        .flatMap { basin(it.first, it.second) }
        .toSet()

    return setOf(Pair(x, y), *n.toTypedArray())
}

val result = lineList.mapIndexedNotNull { y, line ->
    println(line.joinToString(""))
    val result = line.mapIndexedNotNull { x, number ->
        val neighbors = listOf<Pair<Int, Int>>(Pair(x + 1, y), Pair(x, y + 1), Pair(x - 1, y), Pair(x, y - 1))
            .filter { it.first in 0..maxX - 1 && it.second in 0..maxY - 1 }
            .map { lineList[it.second][it.first] }
        if (neighbors.all { number < it }) Pair(x, y) else null
    }
//    println(result)
    if (result.isNotEmpty()) result else null
}
    .flatten()
    .map { val b = basin(it.first, it.second).size; println(b); b }
    .sortedDescending()
    .take(3)

result.get(0) * result.get(1) * result.get(2)
