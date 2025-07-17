import java.io.File
import java.io.InputStream
import java.util.Deque

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<List<String>>()

inputStream.bufferedReader().forEachLine {
    lineList.add(it.split("").filterNot { it.equals("") })
}

//lineList.forEach{ println(it.joinToString("")) }

lineList.map { line ->
    val stack = ArrayDeque<String>()
    line.forEach { bracket ->
        if (bracket in setOf("(", "[", "{", "<")) {
            stack.add(bracket)
//            println("stack=$stack")
        } else if (bracket in setOf(")", "]", "}", ">")) {
            val expected = mapOf(
                "(" to ")",
                "[" to "]",
                "{" to "}",
                "<" to ">"
            ).get(stack.removeLast())
//            println("stack=$stack expected=$expected bracket=$bracket")
            if (expected != bracket) {
                return@map mapOf(
                    ")" to 3,
                    "]" to 57,
                    "}" to 1197,
                    ">" to 25137
                ).get(bracket)
            }
        }
    }
    return@map 0
}.map { it!!.toInt() }.sum()