import java.io.File
import java.io.InputStream
import java.math.BigInteger
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
    var items: MutableList<Int> = mutableListOf(),
    var worry: ((Int) -> Int)? = null,
    var test: Int? = null,
    var ifTrue: Int? = null,
    var ifFalse: Int? = null,
    var inspected: Int = 0
)

fun parseInt(line: String) = line.trimStart { !it.isDigit() }.toInt()

val monkeys = mutableListOf<Monkey>()

lineList.forEach { line ->
    if (line.startsWith("Monkey")) {
        monkeys.add(Monkey())
    } else if (line.contains("Starting items:")) {
        val trimed = line.trimStart { !it.isDigit() }
        val split = trimed.split(", ")
        split.forEach { monkeys.last().items.add(it.toInt()) }
    } else if (line.contains("Operation:")) {
        val equation = line.split(": ")[1].split("= ")[1]
        val split = equation.split(" ")
        monkeys.last().worry = if (split[0] == "old" && split[2] == "old") {
            if (split[1] == "*") { old: Int ->
                old * old
            } else { old: Int -> old + old }
        } else if (split[1] == "*") { old: Int -> old * split[2].toInt() } else { old: Int -> old + split[2].toInt() }
    } else if (line.contains("Test:")) {
        monkeys.last().test = parseInt(line)
    } else if (line.contains("If true:")) {
        monkeys.last().ifTrue = parseInt(line)
    } else if (line.contains("If false:")) {
        monkeys.last().ifFalse = parseInt(line)
    } else {
    }
}

monkeys.forEach { println(it) }
println()

val round = 20

for (i in 1..round) {
    monkeys.forEach { monkey ->
        monkey.items.forEach { item ->
            val worried = monkey.worry?.invoke(item)!!
            val bored = (worried / 3)
            if (bored % monkey.test!! == 0) {
                monkeys[monkey.ifTrue!!].items.add(bored)
            } else monkeys[monkey.ifFalse!!].items.add(bored)
            monkey.inspected++
        }
        monkey.items = mutableListOf()
    }
}

monkeys.forEach { println(it) }

val monkeyBusiness = monkeys.map { it.inspected }

monkeyBusiness.sortedDescending()
    .take(2)
    .reduce { acc, i -> acc * i }