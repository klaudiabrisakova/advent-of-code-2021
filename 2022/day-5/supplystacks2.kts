import java.io.File
import java.io.InputStream
import kotlin.contracts.contract

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val stackLines = lineList.takeWhile { it.isNotBlank() }.asReversed()//.forEach{ println(it) }
val instructions = lineList.dropWhile { it.isNotBlank() }.drop(1)//.forEach{ println(it) }

val stack = mutableListOf<ArrayDeque<String>>()

stackLines.first().filterNot { it.isWhitespace() }.forEach {
    stack.add(ArrayDeque(100))
}
stack.add(0, ArrayDeque(100))

stackLines.drop(1)
    .forEach { line ->
       line.chunked(4)
           .filterNot { it == "[" || it == "]" }
           .forEachIndexed { index, chunk ->
               if (chunk.isNotBlank()) stack[index+1].addLast(chunk.trim() )
           }
    }

stack.forEach{ println(it) }

instructions
//    .take(3)
//    .forEach { println(it) }
    .map {
        val numbers = it.filter { it.isDigit() || it.isWhitespace() }.split(" ").filter { it.isNotBlank() }
//        numbers.forEach { println(it) }
        Triple(numbers[0].toInt(), numbers[1].toInt(), numbers[2].toInt())
    }
    .forEach {
        println(it)
        val pop = mutableListOf<String>()
        for (i in 1..it.first) {
            pop.add(stack[it.second].removeLast())
        }
        pop.asReversed().forEach { popy -> stack[it.third].addLast(popy) }
        stack.forEachIndexed { index, item -> println("$index $item") }
    }

stack.map { it.removeLastOrNull() }.filterNotNull().joinToString("").filter { it.isUpperCase() }
