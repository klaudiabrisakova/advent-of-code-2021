import java.io.File
import java.io.InputStream
import kotlin.math.sign

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine {
    lineList.add(it)
}

val lanternfish = lineList.first().split(",").map { it.toInt() }.toMutableList()
val fishMap = lanternfish.associate { fish -> Pair(fish, lanternfish.count { it.equals(fish) }.toBigInteger()) }.toMutableMap()
fishMap.put(0,0.toBigInteger())
fishMap.put(6,0.toBigInteger())
fishMap.put(7,0.toBigInteger())
fishMap.put(8,0.toBigInteger())

fishMap.toSortedMap().forEach { println(it) }
println()
for (day in 1..256) {
    val newFish = fishMap[0]!!
        fishMap.mapNotNull { (key, value) ->
            if (key > 0) key - 1 to value else null
        }.toMap(fishMap)
    fishMap.put(6, fishMap[6]!! + newFish)
    fishMap[8] = newFish
    fishMap.toSortedMap().forEach { println(it) }
    println()
}
println("Result ${fishMap.values}")