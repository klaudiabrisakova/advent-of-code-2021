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

data class Valve(
    val name: String,
    val flowRate: Int,
    val valves: List<String>,
    var opened: Boolean = false
)

val valves = lineList.map { line ->
    val split1 = line.split(" has flow rate=")
    val name = split1[0].takeLast(2)
    val split2 = if (split1[1].split("; tunnels lead to valves ").size == 1) split1[1].split("; tunnel leads to valve ") else split1[1].split("; tunnels lead to valves ")
    val flowRate = split2[0].toInt()
    val valves = split2[1].split(", ")
    Valve(name, flowRate, valves)
}

var maximum = 0

val mapValves = valves.associate { it.name to it }

fun search(time: Int, valveName: String, releasingPressure: Int, totalReleasedPressure: Int) {
//    println("Minute $time, valve $valveName, $releasingPressure, $totalReleasedPressure")
    if (time >= 31) {
        if (totalReleasedPressure > maximum) {
            maximum = totalReleasedPressure
        }
        return
    }

    val valve = mapValves.getValue(valveName)
    if (valve.flowRate > 0 && !valve.opened) {
        mapValves.getValue(valveName).opened = true
        search(time + 1, valveName, releasingPressure + valve.flowRate, totalReleasedPressure + releasingPressure)
        return
    }
    valve.valves.forEach {
        search(time + 1, it, releasingPressure, totalReleasedPressure + releasingPressure)
    }
}

search(1, "AA", 0, 0)

println(maximum)
