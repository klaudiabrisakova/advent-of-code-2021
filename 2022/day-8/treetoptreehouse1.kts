import java.io.File
import java.io.InputStream
import java.math.BigInteger
import javax.swing.tree.TreeNode
import kotlin.contracts.contract
import kotlin.system.exitProcess

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

lineList.forEach { println(it) }

val map = lineList.map { line -> line.split("").filterNot { it.isBlank() }.map { it.toInt() } }

var count = 0
println()

for (rowIndex in 0..map.size - 1) {
    for (columnIndex in 0..map.first().size - 1) {
        print(map[rowIndex][columnIndex])

        if ((columnIndex == 0) || (columnIndex == map.first().size - 1) || (rowIndex == 0) || (rowIndex == map.first().size - 1)) {
            count += 1
        } else {
            val height = map[rowIndex][columnIndex]
            val leftVisible = map[rowIndex].subList(0, columnIndex).all { it < height }
            val rightVisible = map[rowIndex].subList(columnIndex + 1, map.first().size).all { it < height }
            val topVisible = map.map { it[columnIndex] }.subList(0, rowIndex).all { it < height }
            val bottomVisible = map.map { it[columnIndex] }.subList(rowIndex + 1, map.size).all { it < height }
            if (leftVisible || rightVisible || topVisible || bottomVisible) {
                count += 1
            }
        }
    }
    println()
}

count

