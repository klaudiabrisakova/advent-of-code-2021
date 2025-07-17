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
val lineList = mutableSetOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

lineList.add("332,159 -> 720,159")

val wallIndices = lineList.flatMap { line ->
    val walls = mutableListOf<Pair<Int, Int>>()
    val coordinates = line.split(" -> ").map { it.split(",") }.map { Pair(it[0].toInt(), it[1].toInt()) }

    for (i in 0..coordinates.size - 2) {
        val first = coordinates[i]
        val second = coordinates[i+1]
        walls.addAll(listOf( first, second))

        if (first.first == second.first) {
            val step = (second.second - first.second).sign
            var current = first.second
            while (current != second.second) {
                walls.add(Pair(first.first, current))
                current += step
            }
        } else {
            val step = (second.first - first.first).sign
            var current = first.first
            while (current != second.first) {
                walls.add(Pair(current, first.second))
                current += step
            }
        }
    }
    walls
}
    .toSet()
//    .forEach{ println(it) }

val sendSource = Pair(500, 0)
val mapIndices = wallIndices.union(listOf(sendSource))
val sendIndices = mutableSetOf<Pair<Int, Int>>()

val rowMinimum = mapIndices.minBy { it.second }.second
val rowMaximum = mapIndices.maxBy { it.second }.second
val columnMinimum = mapIndices.minBy { it.first }.first
val columnMaximum = mapIndices.maxBy { it.first }.first

printMap()


var send = sendSource
var stableSend = 0

while (true) {
//    printMap(send)
    var next = Pair(send.first, send.second + 1)
    if (!wallIndices.contains(next) && !sendIndices.contains(next)) {
        send = next
        continue
    }
    next = Pair(next.first - 1, next.second)
    if (!wallIndices.contains(next) && !sendIndices.contains(next)) {
        send = next
        continue
    }
    next = Pair(next.first + 2, next.second)
    if (!wallIndices.contains(next) && !sendIndices.contains(next)) {
        send = next
        continue
    }
    if (send == sendSource) {
        sendIndices.add(sendSource)
        stableSend += 1
        break
    }
    sendIndices.add(send)
    send = sendSource
    stableSend += 1
}
printMap()
println(stableSend)

fun printMap(send: Pair<Int, Int>? = null) {
    println("$columnMinimum $columnMaximum")
    for (row in rowMinimum..rowMaximum) {
        print("$row ")
        for (column in columnMinimum..columnMaximum) {
            if (Pair(column, row) in sendIndices || Pair(column, row) == send) {
                print("o")
            } else if (Pair(column, row) == sendSource) {
                print("+")
            } else if (Pair(column, row) in wallIndices) {
                print("#")
            } else {
                print(".")
            }
        }
        println()
    }
}