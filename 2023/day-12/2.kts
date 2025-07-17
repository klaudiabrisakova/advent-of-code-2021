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

    Line(springs + "?" + springs+ "?" + springs+ "?" + springs+ "?" + springs, damagedGroups + damagedGroups + damagedGroups + damagedGroups + damagedGroups)
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

val size = 70

val cache = mutableMapOf<Pair<List<String>, List<Int>>, Long>()

fun arrangements(map: List<String>, damaged: List<Int>, started: Boolean): Long {
    if (cache.contains(Pair(map, damaged))) {
//        println("${map.joinToString("")} $damaged ${cache[Pair(map, damaged)]}")
        return cache[Pair(map, damaged)]!!
    }

    if (damaged.isEmpty()) {
        if (map.contains("#")) {
            return 0
        } else {
            return 1
        }
    }
    if (map.isEmpty()) {
        if (damaged.size == 1 && damaged.first() == 0) {
            return 1
        } else {
            return 0
        }
    }

    if (map.size < damaged.sum() + damaged.size - 1) {
        return 0
    }


    if (map.count { it == "#" || it == "?" } < damaged.sum()) {
        return 0
    }

    val current = map.first()
    if (damaged.first() == 0) {
        require(started)
        // expect ? or .
        if (current == "#") {
            return (0L).also { if (map.size < size) cache[Pair(map, damaged)] = it }
        }
        if (current == "." || current == "?") {
            return arrangements(map.drop(1), damaged.drop(1), false)
                .also { if (map.size < size) cache[Pair(map, damaged)] = it }
        }
        error("never happens")
    }

    if (started) {
        if (current == "#" || current == "?") {
            val newDamaged = listOf(damaged.first() - 1) + damaged.drop(1)
            return arrangements(map.drop(1), newDamaged, true)
                .also { if (map.size < size) cache[Pair(map, damaged)] = it }

        }
        if (current == ".") {
            return (0L).also { if (map.size < size) cache[Pair(map, damaged)] = it }
        }
        error("never happens")
    }

    if (current == ".") {
        return arrangements(map.drop(1), damaged, false)
            .also { if (map.size < size) cache[Pair(map, damaged)] = it }
    }

    if (current == "#") {
        val newDamaged = listOf(damaged.first() - 1) + damaged.drop(1)
        return arrangements(map.drop(1), newDamaged, true)
            .also { if (map.size < size) cache[Pair(map, damaged)] = it }
    }

    if (current == "?") {
        // dot
        val dot = arrangements(map.drop(1), damaged, false)
        // hash
        val newDamaged = listOf(damaged.first() - 1) + damaged.drop(1)
        val hash = arrangements(map.drop(1), newDamaged, true)

        return (dot + hash)
            .also { if (map.size < size) cache[Pair(map, damaged)] = it }
    }
    error("this should not happen")
}
println()
lines.sumOf { line ->
    cache.size
    println("${line.springs.joinToString("")} ${line.damagedGroups.joinToString(", ")}")
    val arr = arrangements(line.springs, line.damagedGroups, false)
    println(arr)
    arr.toLong()
}

