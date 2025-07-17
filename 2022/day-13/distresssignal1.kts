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

val pairs = lineList
    .filterNot { it.isBlank() }
    .chunked(2)
    .map { Pair(it[0], it[1]) }
//    .forEach { println(it) }


fun String.next(len: Int = 0): Pair<String, Int> {
    if (this.startsWith("[")) {
        return Pair("[", len + 1)
    }
    if (this.startsWith("]")) {
        return Pair("]", len + 1)
    }
    if (this.startsWith(",")) {
        return this.drop(1).next(len + 1)
    }
    val number = this.takeWhile { it.isDigit() }
    return Pair(number, len + number.length)
}

pairs.mapIndexed { index, pair ->
    var left = pair.first
    var right = pair.second
    while (left.isNotBlank() || right.isNotBlank()) {
        val (leftNext, llen) = left.next()
        val (rightNext, rlen) = right.next()

        left = left.drop(llen)
        right = right.drop(rlen)

        print("$index compare $leftNext and $rightNext")
        if (leftNext.all { it.isDigit() } && rightNext.all { it.isDigit() }) {
            if (leftNext.toInt() > rightNext.toInt()) {
                println(false)
                return@mapIndexed false
            }
            if (leftNext.toInt() < rightNext.toInt()) {
                println(true)
                return@mapIndexed true
            }
        }

        if ((leftNext.all { it.isDigit() } || leftNext == "[") && rightNext == "]") {
            println(false)
            return@mapIndexed false
        }

        if ((rightNext.all { it.isDigit() } || rightNext == "[") && leftNext == "]") {
            println(true)
            return@mapIndexed true
        }

        if (leftNext == "[" && rightNext.all { it.isDigit() }) {
            right = rightNext + "]" + right
        }

        if (rightNext == "[" && leftNext.all { it.isDigit() }) {
            left = leftNext + "]" + left
        }
        println(" left $left and $right")
    }
    println(true)
    return@mapIndexed true
}.mapIndexed { index, it -> Pair(index + 1, it) }
    .filter { it.second }
    .sumOf { it.first }
