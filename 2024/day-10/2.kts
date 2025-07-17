import java.io.File
import java.io.InputStream
import kotlin.math.abs

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val map = lineList.map { a -> a.split("").filter { it.isNotBlank() }.map { it.toInt() } }

println(map)

fun Int.rowWithinBounds(): Boolean =
    this >= 0 && this < map.size

fun Int.colWithinBounds(): Boolean =
    this >= 0 && this < map.first().size

fun withinBounds(row: Int, col: Int) = row.rowWithinBounds() && col.colWithinBounds()

fun search(rowIndex: Int, colIndex: Int, from: Int): Int {
    if (!withinBounds(rowIndex, colIndex)) {
        return 0
    }
    val current = map[rowIndex][colIndex]
    if (current - from != 1) {
        return 0
    }
    if (current == 9) {
        return 1
    }
    return search(rowIndex + 1, colIndex, current) + search(rowIndex - 1, colIndex, current) + search(rowIndex, colIndex + 1, current) + search(rowIndex, colIndex - 1, current)
}

map.flatMapIndexed { rowIndex, row ->
    row.mapIndexedNotNull { colIndex, col ->
        if (col == 0) {
            val a = search(rowIndex, colIndex, -1)
            println(a)
            a
        } else {
            null
        }
    }
}.sum()