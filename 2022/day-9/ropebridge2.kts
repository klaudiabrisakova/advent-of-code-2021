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

var snake = mutableListOf(Pair(150, 150), Pair(150, 150), Pair(150, 150), Pair(150, 150), Pair(150, 150), Pair(150, 150), Pair(150, 150), Pair(150, 150), Pair(150, 150), Pair(150, 150))

fun printMap() {
    map
        .mapIndexed { rowIndex, row ->
            row.mapIndexed { columnIndex, column ->
                val index = snake.indexOfFirst { it.first == rowIndex && it.second == columnIndex }
                if (index == -1) column else if (index == 0) "H" else index
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
        snake[0] += headDirection

        for (snakeIndex in 1..(snake.size - 1)) {
            var headIndex = snakeIndex - 1
            var tailIndex = snakeIndex
            var distance = snake[headIndex] - snake[tailIndex]
            distance = Pair(distance.first, distance.second)

            val tailDirection = if (distance.first > 1 || distance.second > 1 || distance.first < -1 || distance.second < -1) {
                Pair(distance.first.sign, distance.second.sign)
            } else Pair(0, 0)

            snake[tailIndex] += tailDirection
            if (snakeIndex == snake.size - 1) {
                map[snake[snakeIndex].first][snake[snakeIndex].second] = "#"
            }
        }
    }
    printMap()
    println()
}

map.sumOf { row -> row.count { it == "#" } }

