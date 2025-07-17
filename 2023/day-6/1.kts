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
    val time: Int, // miliseconds
    val recordDistance: Int // milimeters
)

val times = lineList[0].trimMargin("Time: ").trimStart().split(" ").filter { it.isNotBlank() }.map { it.toInt() }
val recordsDistances = lineList[1].trimMargin("Distance: ").trimStart().split(" ").filter { it.isNotBlank() }.map { it.toInt() }

val races = mutableListOf<Race>()
for (i in 0 until times.size) {
    races.add(Race(times[i], recordsDistances[i]))
}

println(races)

races.map {race ->
    println(race)
    var waysICouldWin = 0
    for (hold in 0 until race.time + 1) {
        val speed = hold
        val distance = (race.time - hold) * speed
        println("Distance: $distance")
        if (distance > race.recordDistance) {
            waysICouldWin += 1
        }
    }
    println("Ways I could win: $waysICouldWin")
    waysICouldWin
}.reduce { acc, next -> acc * next }