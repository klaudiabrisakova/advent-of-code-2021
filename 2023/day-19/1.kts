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

val acceptedParts = mutableListOf<Part>()

fun getNextRule(part: Part, rule: Rule): String {
    for (condition in rule.conditions) {
        if (condition.comparator == "<") {
            if ((Part::class.memberProperties.find { it.name == condition.category }?.get(part) as Int) < condition.number) {
                return condition.ruleName
            }
        }
        if (condition.comparator == ">") {
            if ((Part::class.memberProperties.find { it.name == condition.category }?.get(part) as Int) > condition.number) {
                return condition.ruleName
            }
        }
    }
    return rule.fallback
}

fun processPart(part: Part) {
    var nextRule = "in"
    while (true) {
        val rule = rules.getValue(nextRule)
        nextRule = getNextRule(part, rule)
        if (nextRule == "A") {
            acceptedParts.add(part)
            return
        }
        if (nextRule == "R") {
            return
        }
    }
}

for (part in parts) {
    processPart(part)
}

println("Accepted: $acceptedParts")

acceptedParts.sumOf { it.a + it.m + it.s + it.x }