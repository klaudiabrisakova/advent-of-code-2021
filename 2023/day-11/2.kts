import java.io.File
import java.io.InputStream
import java.lang.Math.abs
import java.lang.Math.pow
import kotlin.contracts.contract
import kotlin.math.ceil


val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val list = mutableListOf<MutableList<Long>>()
list.add(mutableListOf())
var index = 1

val universe = lineList.map { line -> line.split("")
    .filter { it.isNotBlank() }
    .map { if (it == "#") index++.toString() else it} }

//universe.forEach {
//    println(it.joinToString(""))
//}

val a = mutableListOf(mutableListOf<String>())

fun List<List<String>>.transposeMatrix(): List<List<String>> {
    return List(this[0].size) { col ->
        List(this.size) { row ->
            this[row][col]
        }
    }
}

val emptyRowIndexes = mutableListOf<Int>()
val emptyColumnIndexes = mutableListOf<Int>()

val expandedUniverse = universe
    .flatMapIndexed { rowIndex, row ->
        if (row.all { it == "." }) {
            emptyRowIndexes.add(rowIndex)
        }
        listOf(row)
    }
    .transposeMatrix()
    .flatMapIndexed { columnIndex, column ->
        if (column.all { it == "." }) {
            emptyColumnIndexes.add(columnIndex)
        }
        listOf(column)
    }
    .transposeMatrix()

expandedUniverse.forEach {
    println(it.joinToString(""))
}

data class Position(
    val row: Int,
    val column: Int
)

val galaxies = mutableMapOf<String, Position>()

expandedUniverse.forEachIndexed { rowIndex, row ->
    row.forEachIndexed { columnIndex, point ->
        if (point != ".") {
            galaxies[point] = Position(rowIndex, columnIndex)
        }
    }
}

val galaxiesCount = index - 1

fun List<String>.combinations(): List<List<String>> {
    val result = mutableListOf<List<String>>()

    for (i in indices) {
        for (j in i + 1 until this.size) {
            result.add(listOf(this[i], this[j]))
        }
    }

    return result
}

println(galaxiesCount)

val combinations = (1..galaxiesCount).map { it.toString() }.combinations()

println(combinations)

fun generateNumbersInRange(start1: Int, end1: Int): List<Int> {
    var start = start1
    var end = end1
    if (start > end) {
        start = end1
        end = start1
    }

    return (start..end).toList()
}

combinations.sumOf { combination ->
    val first = galaxies[combination.first()]!!
    val second = galaxies[combination.last()]!!

    var result = abs(first.row - second.row) + abs(first.column - second.column)
    println("Between galaxy ${combination.first()} and galaxy ${combination.last()}: $result")
    emptyRowIndexes.intersect(generateNumbersInRange(first.row,second.row)).forEach { result += 999999}
    emptyColumnIndexes.intersect(generateNumbersInRange(first.column,second.column)).forEach { result += 999999 }
    println("Between galaxy ${combination.first()} and galaxy ${combination.last()}: $result")
    result.toLong()
}

