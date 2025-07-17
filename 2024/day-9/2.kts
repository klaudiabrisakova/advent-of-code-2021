import java.io.File
import java.io.InputStream
import kotlin.math.abs

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

data class Block(
    val size: Int,
    val id: Int, // for free space we don't care
    val isFreeSpace: Boolean
)

val diskMap = lineList.first().mapIndexed { index, it -> Block(it.digitToInt(), index / 2, index % 2 == 1) }

val mutableDiskMap = diskMap.toMutableList()

fun printDisk(diskMap: List<Block>) {
    val disk = mutableListOf<String>()
    diskMap.forEach {
        val toAdd = if (it.isFreeSpace) {
            "."
        } else {
            "${it.id}"
        }

        for (i in 1..it.size) {
            disk.add(toAdd)
        }
    }

    println(disk.joinToString(""))
}


//printDisk(diskMap)

diskMap.reversed().filterNot { it.isFreeSpace }.forEach { block ->
    val freeSpaceIndex = mutableDiskMap.indexOfFirst { it.isFreeSpace && it.size >= block.size && it.id < block.id }
    if (freeSpaceIndex == -1) return@forEach

    val size = block.size
    val freeSpace = mutableDiskMap[freeSpaceIndex].size
    val spare = freeSpace - size

    if (spare > 0) {
        mutableDiskMap[freeSpaceIndex] = mutableDiskMap[freeSpaceIndex].copy(size = spare)
    } else {
        mutableDiskMap.removeAt(freeSpaceIndex)
    }
    mutableDiskMap[mutableDiskMap.indexOf(block)] = block.copy(isFreeSpace = true)
    mutableDiskMap.add(freeSpaceIndex, block)

//    printDisk(mutableDiskMap)
}

val disk = mutableListOf<Int?>()
mutableDiskMap.forEach { it ->
    val toAdd: Int? = if (it.isFreeSpace) {
        null
    } else {
        it.id
    }

    for (i in 1..it.size) {
        disk.add(toAdd)
    }
}

disk.foldIndexed(0L) { index, acc, i ->
    if (i != null) acc + index * i else acc
}