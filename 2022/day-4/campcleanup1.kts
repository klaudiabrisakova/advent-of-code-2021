import java.io.File
import java.io.InputStream
import kotlin.contracts.contract

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

lineList
    .map {
        val split = it.split(",")

        val first = split[0].split("-")
        val second = split[1].split("-")

        Pair(first[0].toInt()..first[1].toInt(), second[0].toInt()..second[1].toInt())
    }
    //.forEach{ println(it) }
    .count { pair -> pair.first.all { pair.second.contains(it) } || pair.second.all { pair.first.contains(it) } }