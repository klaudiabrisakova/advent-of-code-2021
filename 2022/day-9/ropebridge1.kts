import java.io.File
import java.io.InputStream
import java.math.BigInteger
import javax.swing.tree.TreeNode
import kotlin.contracts.contract
import kotlin.math.absoluteValue
import kotlin.system.exitProcess
import kotlin.math.sign

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val commands = lineList.map { line ->
    val split = line.split(" ")
    Pair(split.first(), split.last().toInt())
}

val map: MutableList<MutableList<String>> = MutableList<MutableList<String>>(300) { MutableList<String>(400) { "." } }

var head = Pair(150, 150)
var tail = Pair(150, 150)

fun printMap() {
    map
        .mapIndexed { rowIndex, row ->
            row.mapIndexed { columnIndex, column ->
                if (tail.first == rowIndex && tail.second == columnIndex) "T" else column
            }.mapIndexed { columnIndex, column ->
                if (head.first == rowIndex && head.second == columnIndex) "H" else column
            }
        }
        .forEach { println(it.joinToString("")) }
}

printMap()

operator fun Pair<Int, Int>.plus(direction: Pair<Int, Int>): Pair<Int, Int> = Pair(this.first + direction.first, this.second + direction.second)
operator fun Pair<Int, Int>.minus(element: Pair<Int, Int>): Pair<Int, Int> = Pair(this.first - element.first, this.second - element.second)

commands.forEach { command ->
    println(command)

    val headDirection = if (command.first == "R") {
        Pair(0, 1)
    } else if (command.first == "U") {
        Pair(-1, 0)
    } else if (command.first == "L") {
        Pair(0, -1)
    } else Pair(1, 0)

    for (i in 1..command.second) {
        head += headDirection

        var distance = head - tail
        distance = Pair(distance.first, distance.second)

        val tailDirection = if (distance.first > 1 || distance.second > 1 || distance.first < -1 || distance.second < -1) {
            Pair(distance.first.sign, distance.second.sign)
        } else Pair(0, 0)

        tail += tailDirection
        map[tail.first][tail.second] = "#"

        printMap()
        println(distance)
        println()
    }
}

map.sumOf { row -> row.count { it == "#" } }

