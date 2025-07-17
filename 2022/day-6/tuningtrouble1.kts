import java.io.File
import java.io.InputStream
import kotlin.contracts.contract
import kotlin.system.exitProcess

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

lineList.forEach { println(it) }

val value = lineList.first()

for (i in 4..value.length) {
    val substring = value.substring(i-4, i)
//    println(substring)
    if (substring.toList().distinct().size == 4) {
        println(i)
        exitProcess(0)
    }
}