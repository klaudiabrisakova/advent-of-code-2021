import java.io.File
import java.io.InputStream
import java.math.BigInteger
import javax.swing.tree.TreeNode
import kotlin.contracts.contract
import kotlin.math.absoluteValue
import kotlin.system.exitProcess
import kotlin.math.sign

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

var x = 1
lineList
    .flatMap {
        if (it == "noop") {
            listOf(0)
        } else listOf(0, it.split(" ").last().toInt())
    }
    .mapIndexed { index, it ->
        val start = x
        x = x + it
        index + 1 to Pair(start, x)
    }
    //.forEachIndexed { index, it -> println("${index + 1}: $it") }
    .forEach {
        val cycle = it.first
        val pixel = (cycle - 1) % 40
        val spritePosition = it.second.first
        if ((spritePosition - pixel).absoluteValue < 2) print("#") else print(".")
        if (it.first % 40 == 0) {
            println()
        }
    }


