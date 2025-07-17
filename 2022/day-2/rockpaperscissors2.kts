import java.io.File
import java.io.InputStream
import kotlin.contracts.contract

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val me = mapOf(
    "X" to "R",
    "Y" to "P",
    "Z" to "S"
)

val opponent = mapOf(
    "A" to "R",
    "B" to "P",
    "C" to "S"
)

val mePoints = mapOf(
    "R" to 1,
    "P" to 2,
    "S" to 3
)

val loss = mapOf(
    "R" to "S",
    "P" to "R",
    "S" to "P"
)

val win = mapOf(
    "R" to "P",
    "P" to "S",
    "S" to "R"
)

lineList
    .map {
        val split = it.split(" ")

        val opponentShape = opponent.getValue(split.first())
        val strategy = split[1]
        val myShape = if (strategy == "Y") { //draw
            opponentShape
        } else if (strategy == "X") { // loose
            loss.getValue(opponentShape)
        } else win.getValue(opponentShape)
        Pair(opponentShape, myShape)
    }
    .sumOf {
        println(it)
        val shapePoints = mePoints.getValue(it.second)

        val matchPoints = if (it.first == it.second) {
            3
        } else if (it.first == "R" && it.second == "P") {
            6
        } else if (it.first == "P" && it.second == "S") {
            6
        } else if (it.first == "S" && it.second == "R") {
            6
        } else {
            0
        }
        println("$shapePoints, $matchPoints")
        shapePoints + matchPoints
    }