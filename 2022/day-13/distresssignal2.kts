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
    .toMutableList()
pairs.addAll(listOf("[[2]]", "[[6]]"))

pairs.sortWith(object : Comparator <String> {
    override fun compare (p0: String, p1: String) : Int {
        var left = p0
        var right = p1
        while (left.isNotBlank() || right.isNotBlank()) {
            val (leftNext, llen) = left.next()
            val (rightNext, rlen) = right.next()

            left = left.drop(llen)
            right = right.drop(rlen)

            print("compare $leftNext and $rightNext")
            if (leftNext.all { it.isDigit() } && rightNext.all { it.isDigit() }) {
                if (leftNext.toInt() > rightNext.toInt()) {
                    println(false)
                    return 1
                }
                if (leftNext.toInt() < rightNext.toInt()) {
                    println(true)
                    return -1
                }
            }

            if ((leftNext.all { it.isDigit() } || leftNext == "[") && rightNext == "]") {
                println(false)
                return 1
            }

            if ((rightNext.all { it.isDigit() } || rightNext == "[") && leftNext == "]") {
                println(true)
                return -1
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
        return -1
    }
})

pairs.forEach { println(it) }

val two = pairs.indexOf("[[2]]") + 1
val six = pairs.indexOf("[[6]]") + 1

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

two * six