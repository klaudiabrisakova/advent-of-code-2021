import java.io.File
import java.io.InputStream
import kotlin.contracts.contract

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val list = mutableListOf<MutableList<Int>>()
list.add(mutableListOf())
var index = 0
lineList.forEach{
    if (it.isBlank()) {
        index +=1
        list.add(mutableListOf())
    } else {
        list[index].add(it.toInt())
    }
}

list.forEach{ println(it) }

list.map { it.sum() }.sortedDescending().take(3).sum()