import java.io.File
import java.io.InputStream
import java.lang.Math.abs
import java.lang.Math.pow
import kotlin.contracts.contract
import kotlin.math.ceil


val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val steps = lineList.first().split(",")


steps.sumOf { step ->
    var currentValue = 0
    step.forEach { sign ->
        currentValue += sign.code
        currentValue *= 17
        currentValue %= 256
    }
    println("$step becomes $currentValue")
    currentValue
}


