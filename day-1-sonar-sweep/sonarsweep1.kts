import java.io.File
import java.io.InputStream
import kotlin.contracts.contract

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<Int>()

inputStream.bufferedReader().forEachLine { lineList.add(it.toInt()) }
//lineList.forEach { println(">  " + it) }

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

//threeSumList.forEach { println(">  " + it) }

println("Result: ${countIncreased(lineList)}")
