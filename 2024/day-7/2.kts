import java.io.File
import java.io.InputStream
import kotlin.math.abs

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val input = lineList.map {
    val split = it.split(": ")
    val testValue = split[0].toLong()
    val numbers = split[1].split(" ").filter { a -> a.isNotBlank() }.map { a -> a.toLong() }
    testValue to numbers
}

println(input)

fun applyOperator(operator: String, acc: Long, numbers: List<Long>, testValue: Long): Boolean {
    if (acc == testValue && numbers.isEmpty()) {
        return true
    }
    if (numbers.isEmpty()) {
        return false
    }

    return if (operator == "+") {
        applyOperator("+", acc + numbers.first(), numbers.drop(1), testValue) || applyOperator("*", acc + numbers.first(), numbers.drop(1), testValue) || applyOperator("||", acc + numbers.first(), numbers.drop(1), testValue)
    } else if (operator == "||") {
        applyOperator("+", "$acc${numbers.first()}".toLong(), numbers.drop(1), testValue) || applyOperator("*", "$acc${numbers.first()}".toLong(), numbers.drop(1), testValue) || applyOperator("||", "$acc${numbers.first()}".toLong(), numbers.drop(1), testValue)
    } else {
        applyOperator("+", acc * numbers.first(), numbers.drop(1), testValue) || applyOperator("*", acc * numbers.first(), numbers.drop(1), testValue) || applyOperator("||", acc * numbers.first(), numbers.drop(1), testValue)
    }
}

input.filter {
    applyOperator("+", it.second.first(), it.second.drop(1), it.first) || applyOperator("*", it.second.first(), it.second.drop(1), it.first) || applyOperator("||", it.second.first(), it.second.drop(1), it.first)
}.sumOf { it.first }