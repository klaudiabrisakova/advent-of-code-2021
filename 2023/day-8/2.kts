import java.io.File
import java.io.InputStream
import java.lang.Math.pow
import kotlin.contracts.contract


val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val list = mutableListOf<MutableList<Long>>()
list.add(mutableListOf())
var index = 0

data class Next(
    val left: String,
    val right: String
)

val directions = lineList.first().split("").filter { it.isNotBlank() }

val nextMap = lineList.drop(2).associate {
    val chunked = it.filter { it.isLetter() || it.isDigit() }
        .chunked(3)
    chunked.first() to Next(chunked[1], chunked[2])
}

println(directions)
println(nextMap)

var positions = nextMap.keys.filter { it.endsWith("A") }

val numbers = positions.map {position1 ->
    var position = position1
    var count = 0
    while (true) {
        if (index == directions.size) {
            index = 0
        }
        if (position.endsWith("Z")) {
            break
        }

        val direction = directions[index]
        val options = nextMap.getValue(position)
        val nextPosition = when (direction) {
            "L" -> options.left
            "R" -> options.right
            else -> {
                error("what")
            }
        }
        count += 1
        position = nextPosition

        index += 1
    }

    count
}

println(numbers)

