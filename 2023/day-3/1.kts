import java.io.File
import java.io.InputStream
import kotlin.contracts.contract


val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val list = mutableListOf<MutableList<Int>>()
list.add(mutableListOf())
var index = 0
val map = lineList
    .map { line ->
        line.split("").filter { it.isNotBlank() }.map { it.single() }
    }

var sum = 0
for ((rowIndex, row: List<Char>) in map.withIndex()) {
    for ((columnIndex, policko: Char) in row.withIndex()) {
        if (!policko.isDigit()) {
            continue
        }

        if (columnIndex - 1 >= 0 && map[rowIndex][columnIndex - 1].isDigit()) {
            continue
        }

        val numberString =row.drop(columnIndex).takeWhile { it.isDigit() }
        val number = numberString.joinToString("").toInt()
        val numberLength = numberString.size

        val minRowIndex = if (rowIndex - 1 < 0) 0 else rowIndex - 1
        val maxRowIndex = if (rowIndex + 1 >= map.size) map.size - 1 else rowIndex + 1

        val minColumnIndex = if (columnIndex - 1 < 0) 0 else columnIndex - 1
        val maxColumnIndex = if (columnIndex + numberLength >= row.size) row.size - 1 else columnIndex + numberLength

        for (rowIndex2 in minRowIndex..maxRowIndex) {
            for (columnIndex2 in minColumnIndex..maxColumnIndex) {
                if (!map[rowIndex2][columnIndex2].isDigit() && map[rowIndex2][columnIndex2] != '.') {
                    sum += number
                }
            }
        }
    }
}

sum


