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
    val map: MutableList<List<Char>> = mutableListOf()
)

val patterns: MutableList<Pattern> = mutableListOf(Pattern())

lineList.forEach { line ->
    if (line.isBlank()) {
        patterns.add(Pattern())
    } else {
        val lastPattern = patterns.last()
        lastPattern.map.add(line.toList())
    }
}

patterns.sumOf { pattern ->
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

    val transposedMap = pattern.map.transposeMatrix()

    // vertical
    for (reflectionPoint in 1 until transposedMap.size) {
        val up = transposedMap.take(reflectionPoint).reversed()
        val down = transposedMap.drop(reflectionPoint)

        val zipped = up.zip(down)

        if (zipped.all { pair -> pair.first == pair.second }) {
            return@sumOf reflectionPoint
        }
    }

    error("no reflection")
}
