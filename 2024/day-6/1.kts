import java.io.File
import java.io.InputStream
import kotlin.math.abs

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val map = lineList.map { it.split("").filter { it.isNotBlank() }.toMutableList() }.toMutableList()

data class Guard(
    val positionRow: Int,
    val positionCol: Int,
    val direction: String,
)

val startingPositionRow = lineList.indexOfFirst { it.contains('^') }
val startingPositionCol = lineList[startingPositionRow].indexOf('^')

var guard = Guard(startingPositionRow, startingPositionCol, "^")

fun Int.rowWithinBounds(): Boolean =
    this >= 0 && this < map.size

fun Int.colWithinBounds(): Boolean =
    this >= 0 && this < map.first().size

fun withinBounds(row: Int, col: Int) = row.rowWithinBounds() && col.colWithinBounds()

val mapToTheRight = mapOf(
    "^" to ">",
    ">" to "v",
    "v" to "<",
    "<" to "^"
)

while (true) {
    map[guard.positionRow][guard.positionCol] = "X"

    var nextGuard = if (guard.direction == "^") {
        guard.copy(positionRow = guard.positionRow - 1)
    } else if (guard.direction == "v") {
        guard.copy(positionRow = guard.positionRow + 1)
    } else if (guard.direction == ">") {
        guard.copy(positionCol = guard.positionCol + 1)
    } else {
        guard.copy(positionCol = guard.positionCol - 1)
    }

    if (!withinBounds(nextGuard.positionRow, nextGuard.positionCol)) {
        break
    }

    val nextTile = map[nextGuard.positionRow][nextGuard.positionCol]
    if (nextTile == "#") {
        nextGuard = guard.copy(direction = mapToTheRight.getValue(guard.direction))
    }
    guard = nextGuard
}

map.forEach { println(it.joinToString("")) }

map.flatten().count { it == "X" }