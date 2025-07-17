import java.io.File
import java.io.InputStream
import java.lang.Math.abs
import java.lang.Math.pow
import java.util.PriorityQueue
import java.util.Queue
import kotlin.contracts.contract
import kotlin.math.ceil


val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val plan = lineList.map { line ->
    val split = line.split(" ")
    val direction = split[0]
    val len = split[1].toInt()
    direction to len
}

plan.forEach {
    println(it)
}

plan.sumOf { it.second }

data class Trench(
    val rowIndex: Int,
    val colIndex: Int,
    var label: String,
)

var currentPosition = 0 to 0
var previousDirection = "?"
val trenches = mutableListOf<Trench>()

plan.forEach { (direction, len) ->
    for (i in 1..len) {
        if (i == 1) {
            val label = if ((previousDirection == "R" && direction == "U") || (previousDirection == "D" && direction == "L")) {
                "J"
            } else if ((previousDirection == "R" && direction == "D") || (previousDirection == "U" && direction == "L")){
                "7"
            } else if ((previousDirection == "L" && direction == "U") || (previousDirection == "D" && direction == "R")) {
                "L"
            } else {
                "F"
            }
            trenches.lastOrNull()?.label = label
            println(trenches.lastOrNull())
        }

        if (direction == "R") {
            currentPosition = currentPosition.first to currentPosition.second + 1
        } else if (direction == "L") {
            currentPosition = currentPosition.first to currentPosition.second - 1
        } else if (direction == "U") {
            currentPosition = currentPosition.first - 1 to currentPosition.second
        } else if (direction == "D") {
            currentPosition = currentPosition.first + 1 to currentPosition.second
        }
        trenches.add(Trench(currentPosition.first, currentPosition.second, if (direction in listOf("D", "U")) "|" else "-"))

        if (i == len) {
            previousDirection = direction
        }
    }

    trenches
}

trenches.count()

val trench = trenches

val start = trench.find { it.colIndex == 0 && it.rowIndex == 0 }
start?.label = "F"

for (rowIndex in trench.minOf { it.rowIndex } .. trench.maxOf { it.rowIndex }) {
    for (colIndex in trench.minOf { it.colIndex } .. trench.maxOf { it.colIndex }) {
        val find = trench.find { it.rowIndex == rowIndex && it.colIndex == colIndex }
        if (find != null) print(find.label) else print(".")
    }
    println()
}

var count = 0
for (rowIndex in trench.minOf { it.rowIndex } .. trench.maxOf { it.rowIndex }) {
    var inside = false
    var last: String? = null
    for (colIndex in trench.minOf { it.colIndex } .. trench.maxOf { it.colIndex }) {
        val find = trench.find { it.rowIndex == rowIndex && it.colIndex == colIndex }
        if (find != null) {
            count++
            if (find.label == "|") {
                inside = !inside
            }
            if (find.label in listOf("F", "L")) {
                last = find.label
            } else if ((last == "F" && find.label == "J") || (last == "L" && find.label == "7")) {
                inside = !inside
            }
        } else if (inside) {
            count++
        }
    }
}
count


