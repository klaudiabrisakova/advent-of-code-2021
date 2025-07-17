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

fun List<List<Char>>.tilt(): List<List<Char>> = this.map { line ->
    val newLine = mutableListOf<Char>()
    var countO = 0
    var countDot = 0
    line.forEachIndexed { index, col ->
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

fun List<List<Char>>.iteration(): List<List<Char>> {
//    println()
//    println("Input")
//    this.forEach {
//        println(it.joinToString(""))
//    }

    val north = this.transposeMatrix().tilt().transposeMatrix()
//    println()
//    println("North")
//    north.forEach {
//        println(it.joinToString(""))
//    }

    val west = north.tilt()
//    println()
//    println("West")
//    west.forEach {
//        println(it.joinToString(""))
//    }


    val south = west.transposeMatrix().map { it.reversed() }.tilt().map { it.reversed() }.transposeMatrix()
//    println()
//    println("South")
//    south.forEach {
//        println(it.joinToString(""))
//    }

    val east = south.map { it.reversed() }.tilt().map { it.reversed() }
//    println()
//    println("East")
//    east.forEach {
//        println(it.joinToString(""))
//    }

    return east
}

val map  = mutableMapOf<List<List<Char>>, List<Int>>()

var tilted = dish
for (i in 1..1000) {
//    println("Cycle $i:")
    tilted = tilted.iteration()

    if (map.containsKey(tilted)) {
        println()
        tilted.forEach {
            println(it.joinToString(""))
        }
        println("${map[tilted]} with $i")
        map[tilted] = map[tilted]!! + listOf(i)
    } else {
        map[tilted] = listOf(i)
    }

//    if (i % 1000000 == 0) {
//        println("After $i cycles:")
//        tilted.forEach {
//            println(it.joinToString(""))
//        }
//        println()
//    }
}

1_000_000_000 % 7

