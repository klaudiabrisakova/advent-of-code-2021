import java.io.File
import java.io.InputStream
import java.lang.Math.abs
import java.lang.Math.pow
import kotlin.contracts.contract
import kotlin.math.ceil


val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val dish = lineList.map { it.toList() }

fun <T> List<List<T>>.transposeMatrix(): List<List<T>> {
    return List(this[0].size) { col ->
        List(this.size) { row ->
            this[row][col]
        }
    }
}

dish.forEach {
    println(it.joinToString(""))
}

dish.transposeMatrix().sumOf { line ->
    line.reversed().mapIndexedNotNull { index, col ->
        if (col == 'O') index + 1 else null
    }.sum()
}