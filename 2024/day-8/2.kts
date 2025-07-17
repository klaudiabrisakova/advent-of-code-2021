import java.io.File
import java.io.InputStream
import kotlin.math.abs

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val map = lineList.map { it.split("").filter(String::isNotBlank).map { a -> a.first() } }

//println(map)

val anthenas = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()

map.forEachIndexed { rowIndex, row ->
    row.forEachIndexed { colIndex, col ->
        if (col.isLetterOrDigit()) {
            if (anthenas.contains(col)) {
                anthenas.getValue(col).add(Pair(rowIndex, colIndex))
            } else {
                anthenas[col] = mutableListOf(Pair(rowIndex, colIndex))
            }
        }
    }
}

println(anthenas)

fun Int.rowWithinBounds(): Boolean =
    this >= 0 && this < map.size

fun Int.colWithinBounds(): Boolean =
    this >= 0 && this < map.first().size

fun withinBounds(row: Int, col: Int) = row.rowWithinBounds() && col.colWithinBounds()

val antinodes = mutableSetOf<Pair<Int, Int>>()

anthenas.forEach { _, vectors ->
    vectors.forEachIndexed { vectorIndex, vector ->
        val test = vectors.filterIndexed { filterIndex, _ -> filterIndex != vectorIndex }

        test.forEach { t ->
            antinodes.add(vector.first to vector.second)

            val bMinusARow = t.first - vector.first
            val bMinusACol = t.second - vector.second

            var multiplier = 2
            var antinodeRow = vector.first + multiplier * bMinusARow
            var antinodeCol = vector.second + multiplier * bMinusACol
            multiplier++
            while (withinBounds(antinodeRow, antinodeCol)) {
                antinodes.add(antinodeRow to antinodeCol)

                antinodeRow = vector.first + multiplier * bMinusARow
                antinodeCol = vector.second + multiplier * bMinusACol
                multiplier++
            }
        }
    }
}

map.forEachIndexed { rowIndex, row ->
    row.forEachIndexed { colIndex, col ->
        if (col == '.') {
            if (antinodes.contains(rowIndex to colIndex)) {
                print("#")
            } else {
                print(".")
            }
        } else {
            print(col)
        }
    }
    println()
}

antinodes.size