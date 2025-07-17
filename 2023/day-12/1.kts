import java.io.File
import java.io.InputStream
import java.lang.Math.abs
import java.lang.Math.pow
import kotlin.contracts.contract
import kotlin.math.ceil


val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

var index = 1

data class Line(
    val springs: List<String>,
    val damagedGroups: List<Int>
)

val lines = lineList.map { line ->
    val split = line.split(" ")
    val springs = split[0].split("").filter { it.isNotBlank() }
    val damagedGroups = split[1].split(",").map { it.toInt() }

    Line(springs, damagedGroups)
}

lines.forEach {
    println("${it.springs.joinToString("")} ${it.damagedGroups.joinToString(", ")}")
}

fun <T> combinations(list: List<T>, size: Int): List<List<T>> {
    if (size == 0) {
        return listOf(emptyList())
    }

    if (list.isEmpty()) {
        return emptyList()
    }

    val head = list[0]
    val tail = list.subList(1, list.size)

    val withHead = combinations(tail, size - 1).map { combination -> listOf(head) + combination }
    val withoutHead = combinations(tail, size)

    return withHead + withoutHead
}

lines.sumOf { line ->
    val unknownDamaged = line.damagedGroups.sum() - line.springs.count { it == "#" }
    val qIndexes = line.springs.indices.filter { index -> line.springs[index] == "?" }

    val combinations = combinations(qIndexes, unknownDamaged)

    println("${line.springs.joinToString("")} ${line.damagedGroups.joinToString(", ")}")
    println(combinations)

    val count = combinations.count { combination ->
        var springs = line.springs.toMutableList()

        combination.forEach {
            springs[it] = "#"
        }

        val counts = springs.joinToString("").split(".", "?").filter { it.contains("#") }.map { it.length }
        // println(counts)

        counts == line.damagedGroups
    }

    println(count)
    count
}

