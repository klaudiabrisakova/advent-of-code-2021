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

var highestScenicScore = 0
println()

for (rowIndex in 0..map.size - 1) {
    for (columnIndex in 0..map.first().size - 1) {
        println("$rowIndex x $columnIndex : ${map[rowIndex][columnIndex]}")
        val height = map[rowIndex][columnIndex]

        var left = map[rowIndex].subList(0, columnIndex).indexOfLast { it >= height }
        if (left == -1) left = 0
        left = columnIndex - left

        var right = map[rowIndex].subList(columnIndex + 1, map.first().size).indexOfFirst { it >= height }
        println("r:$right")
        if (right == -1) right = map.first().size - 1 else right = right + columnIndex + 1
        println("r:$right")
        right = right - columnIndex

        var top = map.map { it[columnIndex] }.subList(0, rowIndex).indexOfLast { it >= height }
        if (top == -1) top = 0
        top = rowIndex - top

        var bottom = map.map { it[columnIndex] }.subList(rowIndex + 1, map.size).indexOfFirst { it >= height }
        if (bottom == -1) bottom = map.size - 1 else bottom = bottom + rowIndex + 1
        bottom = bottom - rowIndex

        println("l: $left r:$right t:$top b:$bottom")
        val scenicScore = left * right * top * bottom
        if (scenicScore > highestScenicScore) {
            highestScenicScore = scenicScore
        }
    }
    println()
}

highestScenicScore

