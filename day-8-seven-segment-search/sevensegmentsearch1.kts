import java.io.File
import java.io.InputStream

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine {
    lineList.add(it)
}

val map = lineList.map { line ->
    val list = line.split("|")
    Pair(list.get(0), list.get(1))
}.associate { it.first to it.second }

//map.forEach{ println(it) }

val result = map.values
    .map { it.split(" ") }
    .flatten()
    .count { it.length.equals(2) || it.length.equals(3) || it.length.equals(4) || it.length.equals(7) }

println("Result: $result")