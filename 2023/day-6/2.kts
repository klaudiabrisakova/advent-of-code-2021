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

data class Race(
    val time: Long, // miliseconds
    val recordDistance: Long, // milimeters
)

val time = lineList[0].filter { it.isDigit() }.toLong()
val recordsDistance = lineList[1].filter { it.isDigit() }.toLong()

val race = Race(time, recordsDistance)

println(race)


var startWin: Long? = null
var endWin: Long? = null

for (hold in 0 until race.time + 1) {
    val speed = hold
    val distance = (race.time - hold) * speed
    println("Distance: $distance")
    if (distance > race.recordDistance) {
        startWin = hold
        break
    }
}

for (hold in race.time + 1 downTo  -1) {
    val speed = hold
    val distance = (race.time - hold) * speed
    println("Distance: $distance")
    if (distance > race.recordDistance) {
        endWin = hold
        break
    }
}

println("Start win: $startWin")
println("End win: $endWin")

val waysICouldWin = endWin!! - startWin!! + 1
println("Ways I could win: $waysICouldWin")



