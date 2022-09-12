import java.io.File
import java.io.InputStream

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine {
    lineList.add(it)
}

val map = lineList.map { line ->
    val list = line.split("|")
    Pair(list.get(0), list.get(1))
}.associate { it.first to it.second }

//map.forEach{ println(it) }

fun String.list(separator: String = ""): MutableList<String> {
    return split(separator).toMutableList().also { list -> list.removeAll { it.equals("") } }
}

map.map { line ->
    println(line)
    val numbers = line.key.list(" ")
    val one = numbers.first { it.length.equals(2) }
    val seven = numbers.first { it.length.equals(3) }
    val a = seven.list().first { !one.list().contains(it) }
    val six = numbers.filter { it.length.equals(6) }.first { !it.list().containsAll(one.list()) }
    val f = six.list().first { it == one.get(0).toString() || it == one.get(1).toString() }
    val c = one.list().first { !it.equals(f) }
    val four = numbers.first { it.length.equals(4) }
    val eight = numbers.first { it.length.equals(7) }
    val three = numbers.filter { it.length.equals(5) }.first { it.list().containsAll(one.list()) }
    val two = numbers.filter { it.length.equals(5) }.first { it.list().contains(c) && !it.list().contains(f) }
    val five = numbers.filter { it.length.equals(5) }.first { !it.list().contains(c) && it.list().contains(f) }
    val d = two.list().filter { four.list().contains(it) }.first { !it.equals(c) }
    val nine = numbers.first { it.length.equals(6) && !it.equals(six) && it.contains(d) }
    val zero = numbers.first { it.length.equals(6) && !it.equals(six) && !it.equals(nine) }
    println("a=$a c=$c d=$d f=$f")
    val decoded = listOf(zero, one, two, three, four, five, six, seven, eight, nine)
    decoded.forEachIndexed{ index, it -> print("$index=$it ") }
    var result1 = line.value.list(" ").map { number ->
        decoded.indexOfFirst { it.list().containsAll(number.list()) && it.length == number.length }
    }.map { it.toString() }
    println("Result: $result1")

    val result2 = result1.joinToString("").toInt()

    println("Result: $result2")
    println()
    result2
}.sum()

