import java.io.File
import java.io.InputStream
import kotlin.math.abs

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()
data class Equation(
    val a: Int,
    val b: Int,
    val equals: Int
)

data class Problem(
    val eq1: Equation,
    val eq2: Equation
)

inputStream.bufferedReader().forEachLine { lineList.add(it) }

lineList.chunked(4)
    .map { window ->
//        println(window)
        val firstRow = window.first().trimMargin("Button A: X+").split(", Y+").map { it.toInt() }
        val secondRow = window[1].trimMargin("Button B: X+").split(", Y+").map { it.toInt() }
        val prize = window[2].trimMargin("Prize: X=").split(", Y=").map { it.toInt() }
        Problem(
            Equation(firstRow[0], secondRow[0], prize[0]),
            Equation(firstRow[1], secondRow[1], prize[1])
        )
    }
    .map { problem ->
        for (a in 1..100)
            for (b in 1..100) {
                if (problem.eq1.a * a + problem.eq1.b * b == problem.eq1.equals && problem.eq2.a * a + problem.eq2.b * b == problem.eq2.equals) {
                    return@map a to b
                }
            }
        return@map 0 to 0
    }
//    .sumOf { it.first * 3 + it.second }