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

val histories = lineList.map {line ->
    line.split(" ").filter { it.isNotBlank() }.map { it.toInt() }.reversed()
}

println(histories)

val trees = histories.map { history ->
    val tree = mutableListOf(history)
    var next = history
    while (!next.all { it == 0 }) {
        next = next.windowed(size = 2, step = 1, partialWindows = false).map { it.first() - it.last() }
        tree.add(next)
    }
    tree
}

println(trees)

val predictions = trees.map {tree ->
    tree.reversed().drop(1).fold(0) { acc, predictionList ->
        predictionList.last() - acc
    }
}

println(predictions)

predictions.sum()