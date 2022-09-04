import java.io.File
import java.io.InputStream
import kotlin.contracts.contract

fun countIncreased(list: List<Int>): Int {
    var increasedCount = 0
    var previousM = list.first()
    list.forEach { m ->
        if(m > previousM) {
            increasedCount++
            println("$m (increased)")
        } else {
            println("$m (decreased)")
        }
        previousM = m
    }
    return increasedCount
}

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<Int>()

inputStream.bufferedReader().forEachLine { lineList.add(it.toInt()) }
//lineList.forEach { println(">  " + it) }

val threeSumList = lineList.mapIndexed { index, item ->
    if (index >= lineList.size - 2) 0 else
    item + lineList[index + 1] + lineList[index + 2]
}

println("Result: ${countIncreased(threeSumList)}")
