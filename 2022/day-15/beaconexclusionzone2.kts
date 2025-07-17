import java.io.File
import java.io.InputStream
import java.math.BigInteger
import java.util.PriorityQueue
import javax.swing.plaf.basic.BasicSplitPaneDivider
import javax.swing.tree.TreeNode
import kotlin.contracts.contract
import kotlin.math.absoluteValue
import kotlin.math.min
import kotlin.system.exitProcess
import kotlin.math.sign

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableSetOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

data class SensorAndBeacon(
    val sensor: Pair<Int, Int>,
    val closestBeacon: Pair<Int, Int>,
    val distance: Int = (sensor.first - closestBeacon.first).absoluteValue + (sensor.second - closestBeacon.second).absoluteValue
)

val items = lineList.map { line ->
    line.trimStart { !it.isDigit() && it != '-' }.split(": closest beacon is at x=").map { it.split(", y=") }.map { Pair(it[0].toInt(), it[1].toInt()) }
}

val merged = items.flatten()
val sensorsAndBeacons = items.map { SensorAndBeacon(it[0], it[1]) }
sensorsAndBeacons.forEach { println(it) }
val sensors = sensorsAndBeacons.map { it.sensor }
val beacons = sensorsAndBeacons.map { it.closestBeacon }

val minimum = 0
val maximum = 4000000

val toCheck = mutableSetOf<Pair<Int, Int>>()
sensorsAndBeacons.forEach { sab ->
    val possibleDistance = sab.distance + 1

    var y = 0
    var step = 1
    for (x in -possibleDistance..possibleDistance) {
        if (x.absoluteValue + y.absoluteValue == possibleDistance) {
            val resultX = sab.sensor.first + x
            val resultY = sab.sensor.second + y
            if (minimum <= resultX && resultX <= maximum && minimum <= resultY && resultY <= maximum) {
                toCheck.add(Pair(resultX, resultY))
            }
            val resultYminus = sab.sensor.second - y
            if (minimum <= resultX && resultX <= maximum && minimum <= resultYminus && resultYminus <= maximum) {
                toCheck.add(Pair(resultX, resultYminus))
            }
        }
        if (y == possibleDistance) {
            step = -1 * step
        }
        y += step
    }
}


fun printMap() {
//    println("$columnMinimum $columnMaximum")
    for (row in minimum..maximum) {
        print("$row ")
        for (column in minimum..maximum) {
            if (Pair(column, row) in beacons) {
                print("B")
            } else if (Pair(column, row) in sensors) {
                print("S")
            } else if (Pair(column, row) in toCheck) {
                print("#")
            } else {
                print(".")
            }
        }
        println()
    }
}

//printMap()

val result = toCheck.first { checking ->
    sensorsAndBeacons.all { sab -> (sab.sensor.first - checking.first).absoluteValue + (sab.sensor.second - checking.second).absoluteValue > sab.distance }
}

println(result)
println(result.first.toLong() * 4000000 + result.second)



