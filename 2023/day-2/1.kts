import java.io.File
import java.io.InputStream
import kotlin.contracts.contract

data class Game(
    val id: Int,
    val sets: List<List<Pair<CubeColor, Int>>>,
)


enum class CubeColor {
    RED, BLUE, GREEN
}

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val list = mutableListOf<MutableList<Int>>()
list.add(mutableListOf())
var index = 0
lineList
    .map { line ->
        val withoutGamePrefix = line.trimMargin("Game ")
        val split = withoutGamePrefix.split(":")
        val id = split[0].toInt()
        val sets = split[1].trimStart().split(";")
            .map { subset ->
                subset.split(",")
                    .map {
                        val pair = it.trimStart().split(" ")
                        val color = when (pair[1]) {
                            "red" -> CubeColor.RED
                            "blue" -> CubeColor.BLUE
                            "green" -> CubeColor.GREEN
                            else -> throw Exception("Unknown color")
                        }
                        val number = pair[0].toInt()
                        Pair(color, number)
                    }
            }
        Game(id, sets)
    }
    .filter { game ->
        game.sets.flatten().all {
            (it.first == CubeColor.RED && it.second <= 12)
                    || (it.first == CubeColor.GREEN && it.second <= 13)
                    || (it.first == CubeColor.BLUE && it.second <= 14)
        }
    }
    .sumOf { it.id }



