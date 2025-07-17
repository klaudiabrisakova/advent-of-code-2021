import java.io.File
import java.io.InputStream

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { line -> lineList.add(line) }

val emptyIndex = lineList.indexOfFirst { it.isBlank() }

val map = lineList.take(emptyIndex)
    .map { it.split("").filter { ch -> ch.isNotBlank() } }

fun printMap(map: List<List<String>>) {
    map.forEachIndexed { _, rowValue ->
        rowValue.forEachIndexed { _, colValue ->
            print(colValue)
        }
        println()
    }
}



val moves = lineList.drop(emptyIndex + 1)
    .flatMap { it.split("").filter { ch -> ch.isNotBlank() } }

println(moves)

val newMap = map.map { it.toMutableList() }.toMutableList()

fun move(position: Pair<Int, Int>, map: MutableList<MutableList<String>>, where: String): Pair<Int, Int>? {
    val sign = map[position.first][position.second]
    println("position $position sign $sign")

    val newPosition = when (where) {
        ">" -> position.first to position.second + 1
        "v" -> position.first + 1 to position.second
        "<" -> position.first to position.second - 1
        "^" -> (position.first - 1) to position.second
        else -> error(9)
    }

    println("Move ${map[position.first][position.second]} with $where:")

    val newSign = map[newPosition.first][newPosition.second]
    println("new position $newPosition new sign $newSign")

    if (newSign == ".") {
        val s = map[position.first][position.second]
        val ns = map[newPosition.first][newPosition.second]
        map[position.first][position.second] = ns
        map[newPosition.first][newPosition.second] = s
        return newPosition
    } else if (newSign == "O") {
        if (move(newPosition, map, where) != null) {
            val s = map[position.first][position.second]
            val ns = map[newPosition.first][newPosition.second]
            map[position.first][position.second] = ns
            map[newPosition.first][newPosition.second] = s
            return newPosition
        }
        return null
    }
    return null
}

var rowIndex = map.indexOfFirst { it.contains("@") }
var colIndex = map[rowIndex].indexOf("@")

var robot = rowIndex to colIndex
moves.forEach { move ->
    val newRobot = move(robot, newMap, move)
    if (newRobot != null) {
        robot = newRobot
        println("Updated robot position $robot")
    }
//    println("Move ${newMap[robot.first][robot.second]} with $move:")
//    printMap(newMap)
}

printMap(newMap)

var sum = 0
newMap.forEachIndexed { rowIndex, row ->
    row.forEachIndexed { colIndex, col ->
        if (col == "O") {
            println(rowIndex * 100 + colIndex)
            sum += (rowIndex * 100 + colIndex)
        }
    }
}

sum