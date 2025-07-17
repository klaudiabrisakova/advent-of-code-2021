import java.io.File
import java.io.InputStream
import kotlin.contracts.contract

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

lineList//.forEach{ println(it) }
    .chunked(3)
//    .forEach{ println(it) }
    .map { trio ->
        val match = trio[0].filter { trio[1].contains(it) }
        trio[2].first { match.contains(it) }
    }
//    .forEach{ println(it) }
    .map {
        if (it.isUpperCase()) it.code - 38 else it.code - 96
    }
//    .forEach{ println(it) }
    .sum()