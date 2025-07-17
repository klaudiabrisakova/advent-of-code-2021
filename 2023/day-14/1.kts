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

val transposedDish = dish.transposeMatrix()

transposedDish.forEach {
    println(it.joinToString(""))
}

val new = transposedDish.map { line ->
    val newLine = mutableListOf<Char>()
    var countO = 0
    var countDot = 0
    line.forEachIndexed {index, col ->
        if (col == 'O') {
            countO++
        }

        if (col == '.') {
            countDot++
        }

        if (col == '#') {
            for (i in 1..countO) {
                newLine.add('O')
            }
            for (i in 1..countDot) {
                newLine.add('.')
            }
            newLine.add('#')

            countO = 0
            countDot = 0
        } else if (index == line.size - 1) {
            for (i in 1..countO) {
                newLine.add('O')
            }
            for (i in 1..countDot) {
                newLine.add('.')
            }
        }
    }

    return@map newLine
}

new.transposeMatrix().forEach {
    println(it.joinToString(""))
}

new.sumOf { line ->
    line.reversed().mapIndexedNotNull { index, col ->
        if (col == 'O') index + 1 else null
    }.sum()
}