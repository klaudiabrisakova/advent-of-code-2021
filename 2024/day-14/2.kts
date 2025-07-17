import java.io.File
import java.io.InputStream

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

data class Robot(
    val position: Pair<Long, Long>,
    val vector: Pair<Long, Long>,
)

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val maxRow = 103
val maxCol = 101

fun printMap(robots: List<Robot>) {
    for (rowIndex in 0L until maxRow) {
        for (colIndex in 0L until  maxCol) {
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
            positionAndVector[0][1].toLong() to positionAndVector[0][0].toLong(),
            positionAndVector[1][1].toLong() to positionAndVector[1][0].toLong()
        )
    }

//println(initialRobots)
//printMap(initialRobots)

for (i in 1 until 10000000000000L) {
    val after100Iterations =
        initialRobots.map { robot ->
            val vector = (20 + i * 101) * robot.vector.first to (20 + i * 101) * robot.vector.second
            val position =
                (robot.position.first + vector.first) % maxRow to (robot.position.second + vector.second) % maxCol
            val getToPositive = (position.first + maxRow) % maxRow to (position.second + maxCol) % maxCol
            Robot(getToPositive, vector)
        }

    printMap(after100Iterations)
    Thread.sleep(500)
    println(i)
//    val middleRow = maxRow / 2
//    val middleCol = maxCol / 2
//
//    val firstHalf = after100Iterations.filter { it.position.second < middleCol}
//    val secondHalf = after100Iterations.filter { it.position.second > middleCol }
//
//    val newSecondHalf = secondHalf.map { it.copy(position = it.position.first to (middleCol - (it.position.second - middleCol))) }
//    printMap(firstHalf)
//    printMap(newSecondHalf)
//
//    if (firstHalf.containsAll(newSecondHalf)) {
//        printMap(after100Iterations)
//        println(i)
//    }
}

val middleRow = maxRow / 2
val middleCol = maxCol / 2

//val firstQuadrant = after100Iterations.count { it.position.first < middleRow && it.position.second < middleCol }
//val secondQuadrant = after100Iterations.count { it.position.first < middleRow && it.position.second > middleCol }
//val thirdQuadrant = after100Iterations.count { it.position.first > middleRow && it.position.second < middleCol }
//val fourthQuadrant = after100Iterations.count { it.position.first > middleRow && it.position.second > middleCol }
//
//firstQuadrant * secondQuadrant * thirdQuadrant * fourthQuadrant