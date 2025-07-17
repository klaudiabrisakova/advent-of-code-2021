import java.io.File
import java.io.InputStream
import java.lang.Math.abs
import java.lang.Math.pow
import java.util.PriorityQueue
import java.util.Queue
import kotlin.contracts.contract
import kotlin.math.ceil


val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

data class Col(
    val label: Char,
    var energized: MutableList<Char>,
)

var map = lineList.map { line -> line.map { Col(it, mutableListOf()) } }

val left = mapOf(
    'L' to 'U',
    'U' to 'L',
    'R' to 'D',
    'D' to 'R'
)

val right = mapOf(
    'R' to 'U',
    'U' to 'R',
    'L' to 'D',
    'D' to 'L'
)

fun regularNextPosition(rowPosition: Int, colPosition: Int, direction: Char): Pair<Int, Int> =
    if (direction == 'R') {
        rowPosition to colPosition + 1
    } else if (direction == 'L') {
        rowPosition to colPosition - 1
    } else if (direction == 'U') {
        rowPosition - 1 to colPosition
    } else {
        rowPosition + 1 to colPosition
    }

fun search(rowPosition: Int, colPosition: Int, direction: Char) {
    if (rowPosition !in map.indices || colPosition !in map.first().indices) {
        return
    }

//    println("$rowPosition $colPosition $direction")
    val col = map[rowPosition][colPosition]
    if (direction in col.energized) {
        return
    }
    map[rowPosition][colPosition].energized.add(direction)


    if (col.label == '.' || (col.label == '-' && direction in listOf('R', 'L')) || (col.label == '|' && direction in listOf('U', 'D'))) {
        val nextPosition = regularNextPosition(rowPosition, colPosition, direction)
        return search(nextPosition.first, nextPosition.second, direction)
    }

    if (col.label == '-' && direction in listOf('U', 'D')) {
        search(rowPosition, colPosition - 1, 'L')
        search(rowPosition, colPosition + 1, 'R')
        return
    }

    if (col.label == '|' && direction in listOf('R', 'L')) {
        search(rowPosition + 1, colPosition, 'D')
        search(rowPosition - 1, colPosition, 'U')
        return
    }

    if (col.label == '\\') {
        val shiftedDirection = left.getValue(direction)
        val nextPosition = regularNextPosition(rowPosition, colPosition, shiftedDirection)
        return search(nextPosition.first, nextPosition.second, shiftedDirection)
    }

    if (col.label == '/') {
        val shiftedDirection = right.getValue(direction)
        val nextPosition = regularNextPosition(rowPosition, colPosition, shiftedDirection)
        return search(nextPosition.first, nextPosition.second, shiftedDirection)
    }
}

val toSearch = map.indices.associate { rowPosition -> Pair(rowPosition, 0) to 'R' } +
        map.indices.associate { rowPosition -> Pair(rowPosition, map.first().size - 1) to 'L' } +
        map.first().indices.associate { colPosition -> Pair(0, colPosition) to 'D' } +
        map.first().indices.associate { colPosition -> Pair(map.size -1 , colPosition) to 'U' }


toSearch.map { something ->

    search(something.key.first, something.key.second, something.value)

//    map.forEach { line ->
//        println(line.joinToString("") { if (it.energized.isNotEmpty()) "#" else "." })
//    }

    val sum: Int = map.sumOf {line ->
        line.count { it.energized.isNotEmpty() }
    }

    map = lineList.map { line -> line.map { Col(it, mutableListOf()) } }
    sum
}.max()

