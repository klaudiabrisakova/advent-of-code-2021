import java.io.File
import java.io.InputStream
import java.lang.Math.abs
import java.lang.Math.pow
import java.util.PriorityQueue
import java.util.Queue
import kotlin.contracts.contract
import kotlin.math.ceil
import kotlin.random.Random


val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val map = lineList.map { line -> line.map { it.digitToInt() } }

map.forEach {
    println(it.joinToString(""))
}

data class Position(
    val rowIndex: Int,
    val colIndex: Int
)

data class ToSearch(
    val position: Position,
    val heatLoss: Int,
)

data class Prev(
    val direction: Char,
    val position: Position
)

val endPosition = Position(map.size - 1, map.first().size - 1)
var shortest = Int.MAX_VALUE

val dist = mutableMapOf<Position, Int>()

var index = 0
fun findShortest(position: Position, howDidIGetHere: List<Prev>, heatLoss: Int): Pair<Int, List<Prev>> {
//    index++
//    if (index > 100) {
//        return 10000000
//    }
    println("$position, $heatLoss")
    if (position == endPosition) {
        if (heatLoss < shortest) {
            shortest = heatLoss
        }
        return heatLoss to howDidIGetHere
    }

    if (heatLoss > shortest) {
        println(shortest)
        return 1000000 to emptyList()
    }

    val end = howDidIGetHere.map { it.direction }.joinToString("")
    println(end)
    if (end.endsWith("RRRR") || end.endsWith("LLLL") || end.endsWith("UUUU") || end.endsWith("DDDD")) {
        return 1000000 to emptyList()
    }

//    if (dist.containsKey(position)) {
//        if (dist.getValue(position) > heatLoss) {
//            dist[position] = heatLoss
//        } else {
//            return 1000000 to emptyList()
//        }
//    } else {
//        dist[position] = heatLoss
//    }

    val neighbours = listOf(
        Position(position.rowIndex, position.colIndex + 1) to 'R', Position(position.rowIndex, position.colIndex - 1) to 'L',
        Position(position.rowIndex + 1, position.colIndex) to 'D', Position(position.rowIndex - 1, position.colIndex) to 'U'
    )
        .filter { (it, _) -> it.rowIndex in map.indices && it.colIndex in map.first().indices }
        .filter { n -> n.first !in howDidIGetHere.map { it.position } }

//    println("Neighbours $neighbours")

    if (neighbours.isEmpty()) {
        return 1000000 to emptyList()
    }

    return neighbours.map {
         findShortest(it.first, howDidIGetHere + listOf(Prev(it.second, position)), map[it.first.rowIndex][it.first.colIndex] + heatLoss)
    }.minBy { it.first }
}

val startingPosition = Position(0, 0)

val (distance, howDidIGetHere) = findShortest(startingPosition, emptyList(), 0)
println(distance)

val directionMap = mapOf(
    'R' to '>',
    'L' to '<',
    'U' to '^',
    'D' to 'v'
)

map.forEachIndexed { rowIndex, row ->
    row.forEachIndexed { colIndex, col ->
        val position = Position(rowIndex, colIndex)
        if (position in howDidIGetHere.map { it.position } && position != startingPosition) {
            print(".")
        } else
            print(col)
    }
    println()
}
