import java.io.File
import java.io.InputStream
import kotlin.math.abs

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val diskMap = lineList.first().map { it.digitToInt() }

var freeSpace = false
val disk = mutableListOf<Int?>()
diskMap.forEachIndexed { index, it ->
    val toAdd: Int? = if (freeSpace) {
        null
    } else {
        index / 2
    }

    for (i in 1..it) {
        disk.add(toAdd)
    }

    freeSpace = !freeSpace
}

//println(disk)

var firstFreeSpaceIndex = disk.indexOfFirst { it == null }
while (disk.subList(firstFreeSpaceIndex, disk.lastIndex).any { it != null }) {
    val lastFileIndex = disk.indexOfLast { it != null }
    val lastFile = disk[lastFileIndex]
    disk[lastFileIndex] = null

    disk[firstFreeSpaceIndex] = lastFile
    firstFreeSpaceIndex = disk.indexOfFirst { it == null }
//    println(disk)
}

disk.foldIndexed(0L) { index, acc, i ->
    if (i != null) acc + index * i else acc
}
