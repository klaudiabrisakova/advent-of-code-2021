import java.io.File
import java.io.InputStream
import java.lang.Math.abs
import java.lang.Math.pow
import kotlin.contracts.contract
import kotlin.math.ceil


val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

data class Pattern(
    val map: MutableList<MutableList<Char>> = mutableListOf()
)

val patterns: MutableList<Pattern> = mutableListOf(Pattern())

lineList.forEach { line ->
    if (line.isBlank()) {
        patterns.add(Pattern())
    } else {
        val lastPattern = patterns.last()
        lastPattern.map.add(line.toMutableList())
    }
}

data class Reflection(
    val point: Int,
    val horizontal: Boolean
)

val patternsAndReflections = patterns.associate { pattern ->
    pattern.map.forEach {
        println(it.joinToString(""))
    }
    println()

    // horizontal
    for (reflectionPoint in 1 until pattern.map.size) {
        val up = pattern.map.take(reflectionPoint).reversed()
        val down = pattern.map.drop(reflectionPoint)

        val zipped = up.zip(down)

        if (zipped.all { pair -> pair.first == pair.second }) {
            return@associate pattern to Reflection(reflectionPoint, true)
        }
    }

    fun <T> List<List<T>>.transposeMatrix(): List<List<T>> {
        return List(this[0].size) { col ->
            List(this.size) { row ->
                this[row][col]
            }
        }
    }

    val transposedMap = pattern.map.transposeMatrix()

    // vertical
    for (reflectionPoint in 1 until transposedMap.size) {
        val up = transposedMap.take(reflectionPoint).reversed()
        val down = transposedMap.drop(reflectionPoint)

        val zipped = up.zip(down)

        if (zipped.all { pair -> pair.first == pair.second }) {
            return@associate pattern to Reflection(reflectionPoint, false)
        }
    }

    error("no reflection")
}

println(patternsAndReflections)

data class Tile(
    val rowIndex: Int,
    val columnIndex: Int,
    val newValue: Char
)

val change = mapOf(
    '#' to '.',
    '.' to '#'
)

patterns.sumOf { pattern ->
    val newTiles = mutableListOf<Tile>()
    pattern.map.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { columnIndex, value ->
            newTiles.add(Tile(rowIndex, columnIndex, change[value]!!))
        }
    }
    val reflection = patternsAndReflections.getValue(pattern)

    newTiles.forEach { newTile ->

        val newMap = pattern.map.map { row -> row.toMutableList() }
        newMap[newTile.rowIndex][newTile.columnIndex] = newTile.newValue

        // horizontal
        for (reflectionPoint in 1 until newMap.size) {
            val up = newMap.take(reflectionPoint).reversed()
            val down = newMap.drop(reflectionPoint)

            val zipped = up.zip(down)

            if (zipped.all { pair -> pair.first == pair.second } && (reflection.point != reflectionPoint || !reflection.horizontal)) {
                return@sumOf 100 * reflectionPoint
            }
        }

        fun <T> List<List<T>>.transposeMatrix(): List<List<T>> {
            return List(this[0].size) { col ->
                List(this.size) { row ->
                    this[row][col]
                }
            }
        }

        val transposedMap = newMap.transposeMatrix()

        // vertical
        for (reflectionPoint in 1 until transposedMap.size) {
            val up = transposedMap.take(reflectionPoint).reversed()
            val down = transposedMap.drop(reflectionPoint)

            val zipped = up.zip(down)

            if (zipped.all { pair -> pair.first == pair.second } && (reflection.point != reflectionPoint || reflection.horizontal)) {
                return@sumOf reflectionPoint
            }
        }

    }
    error("no reflection in all tiles")
}
