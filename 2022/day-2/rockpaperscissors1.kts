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

lineList.map {
    val split = it.split(" ")
    Pair(opponent.getValue(split.first()), me.getValue(split[1]))
}.sumOf {
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
    } else {0}
    println("$shapePoints, $matchPoints")
    shapePoints + matchPoints
}