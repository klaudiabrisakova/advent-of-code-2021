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
    println(line.joinToString(""))
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
                return@map 0L // broken line
            }
        }
    }
    val missingPiece = stack.reversed().map {  mapOf(
        "(" to ")",
        "[" to "]",
        "{" to "}",
        "<" to ">"
    ).getValue(it) }.toList()
    println(missingPiece.joinToString(""))
    missingPiece.fold(0L) { acc, bracket ->
        println("acc=$acc bracket=$bracket")
        acc * 5L + mapOf(")" to 1L, "]" to 2L, "}" to 3L, ">" to 4L).getValue(bracket)
    }
}
    .filterNot { it == 0L }
    .sorted()
    .let { it[it.size / 2] }