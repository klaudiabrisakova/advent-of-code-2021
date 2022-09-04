import java.io.File
import java.io.InputStream
import kotlin.contracts.contract

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<Pair<String, Int>>()

inputStream.bufferedReader().forEachLine { item ->
    val split: List<String> = item.split(" ")
    lineList.add(Pair(split.first(), split.get(1).toInt()))
}

//lineList.forEach { println(">  " + it) }

var x = 0
var depth = 0
var aim = 0

lineList.forEach {
    if (it.first == "forward") {
        x += it.second
        depth += aim * it.second
    } else if (it.first == "down") {
        aim += it.second
    } else if (it.first == "up") {
        aim -= it.second
    }
    println("$it x:$x y:$depth")
}

println("Result ${x * depth}")