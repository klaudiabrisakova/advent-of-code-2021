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

data class Lens(
    val wholeString: String,
    val label: String,
    val hash: Int, // correct box
    val operation: Char,
    val focalLen: Int?,
)

val lenses = steps.map { step ->
    var currentValue = 0

    val label = step.takeWhile { it != '=' && it != '-' }
    val operation = step[label.length]
    val focalLen = step.getOrNull(label.length + 1)

    label.forEach { sign ->
        currentValue += sign.code
        currentValue *= 17
        currentValue %= 256
    }
//    println("$step becomes $currentValue")
    Lens(
        step,
        label,
        currentValue,
        operation,
        focalLen?.digitToInt()
    )
}

val boxes: MutableList<MutableList<Pair<String, Int>>> = MutableList(256) { mutableListOf() }

lenses.forEach { lens: Lens ->
    if (lens.operation == '=') {
        val index = lens.hash
        val boxLabels = boxes[index].map { it.first }

        if (boxLabels.contains(lens.label)) {
            val indexOfLabel = boxLabels.indexOf(lens.label)
            boxes[index][indexOfLabel] = (lens.label to lens.focalLen!!)
        } else {
            boxes[index].add(lens.label to lens.focalLen!!)
        }
    } else if (lens.operation == '-') {
        boxes[lens.hash] = boxes[lens.hash].filterNot { it.first == lens.label }.toMutableList()
    }

    println("After \"${lens.wholeString}\":")
    boxes.forEachIndexed { index, box ->
        if (box.isNotEmpty()) {
            println("Box $index: ${box.map { "[${it.first} ${it.second}]" }.joinToString(" ")}")
        }
    }
    println()
}

val focusingPower = boxes.withIndex().sumOf { boxIndexed ->
    val box = boxIndexed.value
    box.withIndex().sumOf { lensIndexed ->
        val lens = lensIndexed.value
        val result = (1 + boxIndexed.index) * (1 + lensIndexed.index) * lens.second
        println(result)
        result.toLong()
    }
}

focusingPower

