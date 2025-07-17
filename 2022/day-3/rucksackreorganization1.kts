import java.io.File
import java.io.InputStream
import kotlin.contracts.contract

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

lineList//.forEach{ println(it) }
    .map { Pair(it.substring(0, it.length / 2), it.substring(it.length / 2)) }
//    .forEach{ println(it) }
    .map { pair -> pair.first.first { pair.second.contains(it) } }
//    .forEach{ println(it) }
    .map {
        if (it.isUpperCase()) it.code - 38 else it.code - 96
    }
//    .forEach{ println(it) }
    .sum()