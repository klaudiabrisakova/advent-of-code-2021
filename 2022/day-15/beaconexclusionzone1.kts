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

val noBeacons = sensorsAndBeacons.flatMap {sensorAndBeacon ->
    val distance = sensorAndBeacon.distance
    val sensor = sensorAndBeacon.sensor

    val noBeaconsForOneSensor = mutableSetOf<Pair<Int, Int>>()
    for (y in sensor.first - distance..sensor.first + distance) {
        val x = 2000000
//        for (x in sensor.second - distance..sensor.second + distance) {
            if (Pair(y, x) in sensors || Pair(y, x) in beacons) {
                //nothing
            } else if ((y - sensor.first).absoluteValue + (x - sensor.second).absoluteValue <= distance) {
                noBeaconsForOneSensor.add(Pair(y, x))
            }
//        }
    }
    noBeaconsForOneSensor
}.toSet()

val mergedWithNoBeacons = merged.union(noBeacons)

val rowMinimum = mergedWithNoBeacons.minBy { it.second }.second
val rowMaximum = mergedWithNoBeacons.maxBy { it.second }.second
val columnMinimum = mergedWithNoBeacons.minBy { it.first }.first
val columnMaximum = mergedWithNoBeacons.maxBy { it.first }.first

fun printMap() {
    println("$columnMinimum $columnMaximum")
    for (row in rowMinimum..rowMaximum) {
        print("$row ")
        for (column in columnMinimum..columnMaximum) {
            if (Pair(column, row) in beacons) {
                print("B")
            } else if (Pair(column, row) in sensors) {
                print("S")
            } else if (Pair(column, row) in noBeacons) {
                print("#")
            } else {
                print(".")
            }
        }
        println()
    }
}

//printMap()

noBeacons.filter { it.second == 2000000 }.count()

