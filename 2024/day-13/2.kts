import java.io.File
import java.io.InputStream
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.math.abs
import kotlin.math.round
import kotlin.math.sign

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()
data class Equation(
    val a: ULong,
    val b: ULong,
    val equals: ULong
)

data class Problem(
    val eq1: Equation,
    val eq2: Equation
)

inputStream.bufferedReader().forEachLine { lineList.add(it) }

fun isInteger(number: Double): Boolean {
    return number % 1 < 0.0001 || number % 1 > 0.9999
}

lineList.chunked(4)
    .map { window ->
//        println(window)
        val firstRow = window.first().trimMargin("Button A: X+").split(", Y+").map { it.toULong() }
        val secondRow = window[1].trimMargin("Button B: X+").split(", Y+").map { it.toULong() }
        val prize = window[2].trimMargin("Prize: X=").split(", Y=").map { it.toULong() }
        Problem(
            Equation(firstRow[0], secondRow[0], prize[0]),
            Equation(firstRow[1], secondRow[1], prize[1])
        )
    }
    .map { problem ->
        if (problem.eq1.equals.rem(problem.eq1.b) == 0uL) {
            return@map 0uL to 0uL
        }
        if (problem.eq1.equals.rem(problem.eq1.a) == 0uL) {
            return@map 0uL to 0uL
        }
        if (problem.eq1.equals.rem(problem.eq1.b) == 0uL) {
            return@map 0uL to 0uL
        }
        if (problem.eq1.equals.rem(problem.eq1.b) == 0uL) {
            return@map 0uL to 0uL
        }

        val up = problem.eq2.equals - (problem.eq2.a * problem.eq1.equals / problem.eq1.a)
//        println(up)
        val down = problem.eq2.b - (problem.eq2.a * problem.eq1.b / problem.eq1.a)
        val b = up / down
        val a = (problem.eq1.equals - problem.eq1.b * b) / problem.eq1.a

//        print(a)
//        print(" ")
//        println(b)
        return@map if (isInteger(a) && isInteger(b)) round(a).toULong() to round(b).toULong() else 0uL to 0uL
    }
    .sumOf { it.first * 3uL + it.second }