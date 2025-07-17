import java.io.File
import java.io.InputStream
import kotlin.math.abs

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val pageOrderingRules = mutableMapOf<Int, MutableList<Int>>()
val updates = mutableListOf<List<Int>>()

lineList.forEach {
    if (it.contains("|")) {
        val split = it.split("|")
        val key = split[0].toInt()
        val value = split[1].toInt()
        if (pageOrderingRules.contains(key)) {
            pageOrderingRules[key]?.add(value)
        } else {
            pageOrderingRules[key] = mutableListOf(value)
        }
    }
    if (it.contains(",")) {
        val split = it.split(",")
        updates.add(split.map { i -> i.toInt() })
    }
}

println(pageOrderingRules)
println(updates)

updates.filterNot { update ->
    update.mapIndexed { index, number ->
        if (index != 0) {
            val rule = pageOrderingRules[number] ?: emptyList()
            val before = update.subList(0, index)
            rule.intersect(before).isEmpty()
        } else {
            true
        }
    }.all { it }
}.map { update ->
    update.sortedWith(Comparator{ a, b -> if (pageOrderingRules.getOrDefault(a, emptyList<Int>().toMutableList()).contains(b)) -1 else 1 })
}.sumOf {
    it[it.size / 2]
}
