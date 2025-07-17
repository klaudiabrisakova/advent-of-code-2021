import java.io.File
import java.io.InputStream
import java.lang.Math.pow
import kotlin.contracts.contract


val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val list = mutableListOf<MutableList<Int>>()
list.add(mutableListOf())
var index = 0

data class Card(
    val winningCount: Int,
    val id: Int,
    var copies: Int,
)

val scratchcardsMap = lineList.map {
    val split0 = it.trimMargin("Card ").trimStart().split(":")
    val id = split0[0]
    val numbers = split0[1]
    val split = numbers.split("|")
    val winning = split[0]
    val iHave = split[1]

    val pair = Pair(
        winning.split(" ").filter { it.isNotBlank() }.map { it.toInt() },
        iHave.split(" ").filter { it.isNotBlank() }.map { it.toInt() })

    val count = pair.second.count { pair.first.contains(it) }
    id.toInt() to Card(count, id.toInt(), 0)
}


scratchcardsMap.forEach { (id, card) ->
    for (i in 0 until card.winningCount) {
        scratchcardsMap[id + i].second.copies += 1 * (card.copies + 1)
    }
}

scratchcardsMap.sumOf { it.second.copies + 1 }




