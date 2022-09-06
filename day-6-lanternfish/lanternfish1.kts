import java.io.File
import java.io.InputStream
import kotlin.math.sign

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine {
    lineList.add(it)
}

val lanternfish = lineList.first().split(",").map { it.toInt() }.toMutableList()

lanternfish.take(10).forEach { print(it) }
println()
for (day in 1..80) {
    val newFish = mutableListOf<Int>()
    for ((index, fish) in lanternfish.withIndex()) {
        if (fish == 0) {
            lanternfish[index] = 6
            newFish.add(8)
        } else {
            lanternfish[index]--
        }
    }
    lanternfish.addAll(newFish)
    lanternfish.take(10).forEach { print(it) }
    println()
}
println("Result ${lanternfish.size}")