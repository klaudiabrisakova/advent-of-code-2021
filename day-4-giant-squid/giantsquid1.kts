import java.io.File
import java.io.InputStream
import kotlin.system.exitProcess

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine {
    lineList.add(it)
}

//lineList.forEach { println(it) }

val drawNumbers = lineList.first().split(",").map { it.toInt() }
//drawNumbers.forEach { print(it) }

lineList.removeFirst()
lineList.removeFirst()

lineList.removeAll(String::isBlank)

//lineList.forEach { println(it) }
class Board() {
    val data: MutableList<MutableList<Int?>> = MutableList(10) { MutableList(5) { null } }

    fun print() {
        data.forEach {
            println(it)
        }
        println()
    }

    fun bingo(numbers: List<Int>): Boolean {
        for ((index, row) in data.withIndex()) {
            if (numbers.containsAll(row)) {
                println("Numbers ${numbers}")
                println("Row index ${index}")
                println("Row ${row}")
                val unmarked = data.take(5).flatten().filterNotNull().toMutableList()
                println("Flat $unmarked")
                unmarked.removeAll(numbers)
                println("Unmarked $unmarked")
                val sum = unmarked.sum()
                println("Sum $sum")
                println("Result ${sum * numbers.last()}")
                return true
            }
        }
        return false
    }
}

var boards: MutableList<Board> = mutableListOf()

var row = 0
var board = Board()
for (line in lineList) {
    var lineNumberStrings = line.split(" ").toMutableList()
    lineNumberStrings.removeAll(String::isBlank)
    val lineNumbers = lineNumberStrings.map { it.toInt() }

    for ((column, number) in lineNumbers.withIndex()) {
        board.data[row][column] = number
        board.data[column + 5][row] = number
    }
    row++
    if (row == 5) {
        row = 0
        boards.add(board)
        board = Board()
    }
}

boards.forEach { it.print() }

val numbers = mutableListOf<Int>()
for (it in drawNumbers) {
    numbers.add(it)
    println(numbers)
    for (board in boards) {
        board.print()
        if (board.bingo(numbers)) {
            exitProcess(0)
        }
    }
    println()
}
