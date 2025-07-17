import java.io.File
import java.io.InputStream
import javax.swing.plaf.basic.BasicSplitPaneDivider
import javax.swing.tree.TreeNode
import kotlin.contracts.contract
import kotlin.math.absoluteValue
import kotlin.system.exitProcess
import kotlin.math.sign

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

data class Monkey(
    var items: MutableList<ULong> = mutableListOf(),
    var worry: ((ULong) -> ULong)? = null,
    var test: ULong? = null,
    var ifTrue: Int? = null,
    var ifFalse: Int? = null,
    var inspected: ULong = 0UL
) {
    override fun toString(): String {
        return items.joinToString(", ") + " inspected: $inspected"
    }
}

fun parseULong(line: String) = line.trimStart { !it.isDigit() }.toULong()

val monkeys = mutableListOf<Monkey>()

lineList.forEach { line ->
    if (line.startsWith("Monkey")) {
        monkeys.add(Monkey())
    } else if (line.contains("Starting items:")) {
        val trimed = line.trimStart { !it.isDigit() }
        val split = trimed.split(", ")
        split.forEach { monkeys.last().items.add(it.toULong()) }
    } else if (line.contains("Operation:")) {
        val equation = line.split(": ")[1].split("= ")[1]
        val split = equation.split(" ")
        monkeys.last().worry = if (split[0] == "old" && split[2] == "old") {
            if (split[1] == "*") { old: ULong ->
                old * old
            } else { old: ULong -> old + old }
        } else if (split[1] == "*") { old: ULong -> old * split[2].toULong() } else { old: ULong -> old + split[2].toULong() }
    } else if (line.contains("Test:")) {
        monkeys.last().test = parseULong(line)
    } else if (line.contains("If true:")) {
        monkeys.last().ifTrue = parseULong(line).toInt()
    } else if (line.contains("If false:")) {
        monkeys.last().ifFalse = parseULong(line).toInt()
    } else {
    }
}

monkeys.forEachIndexed { index, it -> println("Monkey $index: $it") }
println()

val modulo = monkeys.map { it.test!! }.reduce { acc, it -> acc * it }
println(modulo)
println()

val round = 10000

for (i in 1..round) {
    println("round: $i")
    monkeys.forEach { monkey ->
        monkey.items.forEach { item ->
            val worried = monkey.worry?.invoke(item)!!
            val bored = (worried) % modulo
            println("$bored % ${monkey.test!!} = ${bored % monkey.test!!}")
            if (bored % monkey.test!! == 0UL) {
                monkeys[monkey.ifTrue!!].items.add(bored)
            } else monkeys[monkey.ifFalse!!].items.add(bored)
            monkey.inspected++
        }
        monkey.items = mutableListOf()
        monkeys.forEachIndexed { index, it -> println("Monkey $index: $it") }
        println()
    }
//    monkeys.forEachIndexed { index, it -> println("Monkey $index: $it") }
}

val monkeyBusiness = monkeys.map { it.inspected }

monkeyBusiness.sortedDescending()
    .take(2)
    .reduce { acc, i -> acc * i }