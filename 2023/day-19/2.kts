import java.io.File
import java.io.InputStream
import java.lang.Math.abs
import java.lang.Math.pow
import java.util.PriorityQueue
import java.util.Queue
import kotlin.contracts.contract
import kotlin.math.ceil
import kotlin.reflect.full.memberProperties


val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

data class Condition(
    val category: String,
    val comparator: String,
    val number: Int,
    val ruleName: String,
)

data class Rule(
    val conditions: List<Condition>,
    val fallback: String,
)

val rules = mutableMapOf<String, Rule>()

data class Part(
    val x: Int,
    val m: Int,
    val a: Int,
    val s: Int,
)

val parts = mutableListOf<Part>()

lineList.filter { it.isNotBlank() }.forEach { line ->
    if (line.startsWith("{")) {
        val trimmed = line.removeSurrounding("{", "}")
        val split = trimmed.split(",")
        val x = split[0].removePrefix("x=").toInt()
        val m = split[1].removePrefix("m=").toInt()
        val a = split[2].removePrefix("a=").toInt()
        val s = split[3].removePrefix("s=").toInt()
        parts.add(Part(x, m, a, s))
    } else {
        val split = line.split("{")
        val name = split[0]
        val something = split[1].removeSuffix("}").split(",")
        val conditions = mutableListOf<Condition>()
        for (i in 0 until something.size - 1) {
            val condition = something[i]
            val splitCon = condition.split(":")
            val ruleName = splitCon[1]
            val spl = splitCon[0]
            val category = spl[0].toString()
            val comparator = spl[1].toString()
            val number = spl.drop(2).toInt()
            conditions.add(Condition(category, comparator, number, ruleName))
        }
        val fallback = something.last()
        rules[name] = Rule(conditions, fallback)
    }
}

println(rules)
println(parts)

data class Ranges(
    val name: String,
    val x: IntRange,
    val m: IntRange,
    val a: IntRange,
    val s: IntRange,
)

val tree = mutableMapOf<Ranges, List<Ranges>>()

val ruleName = "in"
val rule = rules[ruleName]!!
val initialRanges = Ranges(
    ruleName,
    1..4000,
    1..4000,
    1..4000,
    1..4000,
)

fun getRanges(condition: Condition, nextRanges: Ranges): Pair<Ranges, Ranges> {
    if (condition.comparator == "<") {
        val oldRange = Ranges::class.memberProperties.find { it.name == condition.category }!!.get(nextRanges) as IntRange
        val newRange = oldRange.first..condition.number - 1
        val rangesForNextConditions = condition.number..oldRange.last
        return Ranges(
            condition.ruleName,
            if (condition.category == "x") newRange else nextRanges.x,
            if (condition.category == "m") newRange else nextRanges.m,
            if (condition.category == "a") newRange else nextRanges.a,
            if (condition.category == "s") newRange else nextRanges.s,
        ) to Ranges(
            "?",
            if (condition.category == "x") rangesForNextConditions else nextRanges.x,
            if (condition.category == "m") rangesForNextConditions else nextRanges.m,
            if (condition.category == "a") rangesForNextConditions else nextRanges.a,
            if (condition.category == "s") rangesForNextConditions else nextRanges.s
        )
    }
    if (condition.comparator == ">") {
        val oldRange = Ranges::class.memberProperties.find { it.name == condition.category }!!.get(nextRanges) as IntRange
        val newRange = condition.number + 1..oldRange.last
        val rangesForNextConditions = oldRange.first..condition.number
        return Ranges(
            condition.ruleName,
            if (condition.category == "x") newRange else nextRanges.x,
            if (condition.category == "m") newRange else nextRanges.m,
            if (condition.category == "a") newRange else nextRanges.a,
            if (condition.category == "s") newRange else nextRanges.s,
        ) to Ranges(
            "?",
            if (condition.category == "x") rangesForNextConditions else nextRanges.x,
            if (condition.category == "m") rangesForNextConditions else nextRanges.m,
            if (condition.category == "a") rangesForNextConditions else nextRanges.a,
            if (condition.category == "s") rangesForNextConditions else nextRanges.s,
        )
    }
    error("never")
}
//var count = 0
fun searchOneLevel(parentRanges: Ranges) {
//    println("Calling for $parentRanges and tree looks like: ")
//    tree.forEach(::println)
//    count++
//    if (count > 3) {
//        return
//    }
    if (parentRanges.name in listOf("A", "R")) {
        return
    }
    var nextRanges = parentRanges
    val rule = rules[parentRanges.name]!!
    for (condition in rule.conditions) {
//        println("ToNextRanges: $condition $nextRanges")
        val tmp = getRanges(condition, nextRanges)
//        println("Out: $tmp")
        val ranges = tmp.first
        nextRanges = tmp.second
        val node = tree[parentRanges]
        if (node == null) {
            tree[parentRanges] = listOf(ranges)
        } else {
            tree[parentRanges] = node + ranges
        }
    }
    val fallbackRanges = nextRanges.copy(name = rule.fallback)
    tree[parentRanges] = tree[parentRanges]!! + fallbackRanges
//    println("After:")
//    tree.forEach(::println)
//    println()
    for (range in tree[parentRanges]!!) {
        searchOneLevel(range)
    }
}

searchOneLevel(initialRanges)

println()
tree.forEach(::println)

//val validParts = mutableSetOf<Part>()
//tree.flatMap { pair -> pair.value.filter { it.name == "A" } }
//    .map {
//
//        for (x in it.x.first..it.x.last) {
//            for (m in it.m.first..it.m.last) {
//                for (a in it.a.first..it.a.last) {
//                    for (s in it.s.first..it.s.last) {
//                        val part = Part(x, m, a, s)
//                        validParts.add(part)
//                    }
//                }
//            }
//        }
//    }
//
//println(validParts.size)

val range = tree.flatMap { pair -> pair.value.filter { it.name == "A" } }

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

fun calculate(size: Int): ULong {
    val combinations = combinations(range, size)
    var sum = 0UL
    combinations.forEach {
        val intersection = it.reduce { acc, ranges ->
            Ranges(
                "?",
                maxOf(acc.x.first, ranges.x.first)..minOf(acc.x.last, ranges.x.last),
                maxOf(acc.m.first, ranges.m.first)..minOf(acc.m.last, ranges.m.last),
                maxOf(acc.a.first, ranges.a.first)..minOf(acc.a.last, ranges.a.last),
                maxOf(acc.s.first, ranges.s.first)..minOf(acc.s.last, ranges.s.last),
            )
        }
        if (intersection.x.first > intersection.x.last || intersection.m.first > intersection.m.last || intersection.a.first > intersection.a.last || intersection.s.first > intersection.s.last) {
            return@forEach
        }
        val x = intersection.x.last - intersection.x.first + 1
        val m = intersection.m.last - intersection.m.first + 1
        val a = intersection.a.last - intersection.a.first + 1
        val s = intersection.s.last - intersection.s.first + 1
        sum += x.toULong() * m.toULong() * a.toULong() * s.toULong()
    }

    return sum
}

// they actually do not intersect at all, facepalm
println(calculate(1))