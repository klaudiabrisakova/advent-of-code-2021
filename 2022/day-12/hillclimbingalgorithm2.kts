import java.io.File
import java.io.InputStream
import java.math.BigInteger
import java.util.PriorityQueue
import javax.swing.plaf.basic.BasicSplitPaneDivider
import javax.swing.tree.TreeNode
import kotlin.contracts.contract
import kotlin.math.absoluteValue
import kotlin.system.exitProcess
import kotlin.math.sign

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

lineList.forEach { println(it) }

val map = lineList

data class Item(val coordinates: Pair<Int, Int>, val parent: Item?, val steps: Int) {
    override fun toString(): String {
        return coordinates.toString()
    }
}

val queue: ArrayDeque<Item> = ArrayDeque()

queue.addLast(Item(Pair(20, 154), null, 0))

val explore = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))
val explored = mutableSetOf<Pair<Int, Int>>(Pair(0, 0))

while (queue.isNotEmpty()) {
    println(queue)
    val item = queue.removeFirst()
    if (map[item.coordinates.first][item.coordinates.second] == 'a') {
        println("Found ya ${item.steps}")
        exitProcess(0)
    }

    val toExplore = explore
        .map { item.coordinates + it }
        .filter { it.first >= 0 && it.second >= 0 && it.first < map.size && it.second < map.first().length }
        .filter { !explored.contains(it) }
    //println("to explore: $toExplore")
    //println("explored: $explored")
    toExplore.forEach {
        val next = it.myCode()
        val current = item.coordinates.myCode()
        if (current - next <= 1) {
            queue.addLast(Item(Pair(it.first, it.second), item, item.steps + 1))
            explored.add(Pair(it.first, it.second))
        }
    }
}

//println(queue)

operator fun Pair<Int, Int>.plus(direction: Pair<Int, Int>): Pair<Int, Int> = Pair(this.first + direction.first, this.second + direction.second)

fun Pair<Int, Int>.myCode(): Int {
    val letter = map[this.first][this.second]
    if (letter == 'S') return 'a'.code
    if (letter == 'E') return 'z'.code
    return letter.code
}