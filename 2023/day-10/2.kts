import java.io.File
import java.io.InputStream
import java.lang.Math.pow
import kotlin.contracts.contract
import kotlin.math.ceil


val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val list = mutableListOf<MutableList<Long>>()
list.add(mutableListOf())
var index = 0

val map = lineList.map { line -> line.split("").filter { it.isNotBlank() } }

println(map)

val startRow = map.indexOfFirst { it.contains("S") }
val startColumn = map[startRow].indexOf("S")

println("start $startRow $startColumn")

data class Position(
    val row: Int,
    val column: Int
)

var currentPosition = Position(startRow, startColumn)

fun nextPosition(currentPosition: Position, path: List<Position>) : Position? {
    // println("Current position: $currentPosition")
    val currentPositionSign = map[currentPosition.row][currentPosition.column]
    print(currentPositionSign)

    val validNextPositions = listOf(
        Position(currentPosition.row, currentPosition.column + 1),
        Position(currentPosition.row, currentPosition.column - 1),
        Position(currentPosition.row + 1, currentPosition.column),
        Position(currentPosition.row - 1, currentPosition.column),
    )
        .filterNot { it.column < 0 || it.row < 0 || it.row >= map.size || it.column >= map.first().size}
        .filterNot { path.contains(it) }

//    println("Valid: $validNextPositions")
//    println("Path so far: $path")

    val nextPosition = validNextPositions.firstOrNull { validPosition ->
        val validPositionSign = map[validPosition.row][validPosition.column]
        if (validPosition.column > currentPosition.column) { // east
            return@firstOrNull listOf("-", "L", "F", "S").contains(currentPositionSign) && listOf("-", "7", "J").contains(validPositionSign)
        }
        if (validPosition.column < currentPosition.column) { // west
            return@firstOrNull listOf("-", "7", "J", "S").contains(currentPositionSign) && listOf("F", "-", "L").contains(validPositionSign)
        }
        if (validPosition.row > currentPosition.row) { // south
            return@firstOrNull listOf("|", "7", "F", "S").contains(currentPositionSign) && listOf("|", "L", "J").contains(validPositionSign)
        }
        if (validPosition.row < currentPosition.row) { // north
            return@firstOrNull listOf("|", "J", "L", "S").contains(currentPositionSign) && listOf("|", "F", "7").contains(validPositionSign)
        }
        false
    }

    // println("Next position: $nextPosition")
    return nextPosition
}
val path = mutableListOf(currentPosition)

while (true) {
    val next = nextPosition(currentPosition, path) ?: break
    currentPosition = next
    index += 1
    path.add(currentPosition)
    //println(path.joinToString("") { map[it.row][it.column] })
}

println(path)

var inside = false

var insideCount = 0

map.forEachIndexed { rowIndex, row ->
    inside = false
    var corner: String? = null
    row.map{ if (it == "S") "L" else it }.forEachIndexed { columnIndex, column ->
        if (path.contains(Position(rowIndex, columnIndex))) {
            if (listOf("L", "J", "7", "F").contains(column)) {
                if (corner == null) {
                    corner = column
                } else {
                    if (corner == "F" && column == "7") {
                        // nothing
                    } else if (corner == "L" && column == "J") {
                        // nothing
                    } else {
                        inside = !inside
                    }
                    corner = null
                }
            } else if (column == "|") {
                inside = !inside
            }
            print(column)
        } else if (inside) {
            insideCount += 1
            print("I")
        } else if (!inside) {
            print("O")
        }
    }
    println()
}

insideCount