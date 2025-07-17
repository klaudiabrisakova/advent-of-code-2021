import java.io.File
import java.io.InputStream
import java.lang.Math.abs
import java.lang.Math.pow
import java.util.PriorityQueue
import java.util.Queue
import kotlin.contracts.contract
import kotlin.math.ceil
import kotlin.reflect.full.memberProperties


val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

data class Module(
    val name: String,
    var state: Boolean,
    val type: String,
    val outputModuleNames: List<String>,
    val conjunction: MutableMap<String, Boolean> = mutableMapOf()
)

val modules = lineList
    .map { line ->
        val split = line.split(" -> ")
        val firstPart = split[0]
        val secondPart = split[1]

        if (firstPart.startsWith("%")) {
            val type = firstPart[0].toString()
            val name = firstPart.drop(1)

            val outputModuleNames = secondPart.split(", ")
            val state = false
            Module(name, state, type, outputModuleNames)
        } else if (firstPart.startsWith("&")) {
            val type = firstPart[0].toString()
            val name = firstPart.drop(1)

            val outputModuleNames = secondPart.split(", ")
            val state = false
            Module(name, state, type, outputModuleNames)
        } else {
            val name = firstPart
            val type = "B"

            val outputModuleNames = secondPart.split(", ")
            val state = false
            Module(name, state, type, outputModuleNames)
        }
    }

data class Signal(
    val fromModuleName: String,
    val toModuleName: String,
    val value: Boolean,
)

val queue = ArrayDeque<Signal>()
var high = 0
var low = 0

val modulesMap = modules.associateBy { it.name }

val broadcaster = modulesMap["broadcaster"]!!

//broadcaster.outputModuleNames.forEach {
//    queue.add(Signal(broadcaster.name, it, false))
//}

val allAmp = modules.filter { it.type == "&" }
allAmp.forEach { amp ->
    modules.filter { it.outputModuleNames.contains(amp.name) }
        .forEach{
            amp.conjunction[it.name] = false
        }

}


for (i in 1..1000) {
    broadcaster.outputModuleNames.forEach {
        queue.add(Signal(broadcaster.name, it, false))
        low++
    }
    while (queue.isNotEmpty()) {
        val signal = queue.removeFirst()
        println("${signal.fromModuleName} ${if (signal.value) "-high" else "-low"}-> ${signal.toModuleName}")
        //println(queue)
        if (signal.toModuleName == "output") {
            continue
        }
//        println(signal.toModuleName)
        val module = modulesMap[signal.toModuleName]
//        println(module)
        if (module == null) {
            println("module is null")
            continue
        }
        if (module.type == "%") {
            if (!signal.value) {
                module.state = !module.state
                module.outputModuleNames.forEach {
                    queue.add(Signal(module.name, it, module.state))
                    if (module.state) high++ else low++
                }
            }
        } else if (module.type == "&") {
//            println(module.conjunction)
            module.conjunction[signal.fromModuleName] = signal.value
//            println(module.conjunction)
            val pulse = !module.conjunction.values.all { it }
            module.outputModuleNames.forEach {
//            println(it)
                queue.add(Signal(module.name, it, pulse))
                if (pulse) high++ else low++
            }
        }
    }
    println()
}

println(low + 1000) // 1000 for button pushes
println(high)

(low + 1000) * high