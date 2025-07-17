import java.io.File
import java.io.InputStream

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

data class Robot(
    val position: Pair<Int, Int>,
    val vector: Pair<Int, Int>,
)

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val maxRow = 103
val maxCol = 101

fun printMap(robots: List<Robot>) {
    for (rowIndex in 0 until maxRow) {
        for (colIndex in 0 until  maxCol) {
            val count = robots.map { it.position }.count { (rowIndex to colIndex) == it }
            if (count > 0) {
                print(count)
            } else {
                print(".")
            }
        }
        println()
    }
}

val initialRobots = lineList
    .map { line ->
        val positionAndVector = line.trimMargin("p=").split(" v=").map { it.split(",") }
        Robot(
            positionAndVector[0][1].toInt() to positionAndVector[0][0].toInt(),
            positionAndVector[1][1].toInt() to positionAndVector[1][0].toInt()
        )
    }

println(initialRobots)
printMap(initialRobots)

val after100Iterations =
    initialRobots.map { robot ->
        val vector = 100 * robot.vector.first to 100 * robot.vector.second
        val position =
            (robot.position.first + vector.first) % maxRow to (robot.position.second + vector.second) % maxCol
        val getToPositive = (position.first + maxRow) % maxRow to (position.second + maxCol) % maxCol
        Robot(getToPositive, vector)
    }

printMap(after100Iterations)

val middleRow = maxRow / 2
val middleCol = maxCol / 2

val firstQuadrant = after100Iterations.count { it.position.first < middleRow && it.position.second < middleCol }
val secondQuadrant = after100Iterations.count { it.position.first < middleRow && it.position.second > middleCol }
val thirdQuadrant = after100Iterations.count { it.position.first > middleRow && it.position.second < middleCol }
val fourthQuadrant = after100Iterations.count { it.position.first > middleRow && it.position.second > middleCol }

firstQuadrant * secondQuadrant * thirdQuadrant * fourthQuadrant