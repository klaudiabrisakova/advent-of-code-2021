import java.io.File
import java.io.InputStream

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

lineList.map { it -> it.split("").filter { it.isNotBlank() } }

val interestedVectors = listOf(
    listOf(
        1 to 1, // a
        2 to 2, // s
        2 to 0, // jedno M
        0 to 2 // jedno s
    ),
    listOf(
        -1 to -1,
        -2 to -2,
        0 to -2,
        -2 to 0
    )
)

fun Int.rowWithinBounds(): Boolean =
    this >= 0 && this < lineList.size

fun Int.colWithinBounds(): Boolean =
    this >= 0 && this < lineList.first().length

fun withinBounds(row: Int, col: Int) = row.rowWithinBounds() && col.colWithinBounds()

var count = 0
lineList.forEachIndexed { row, rowValue ->
    rowValue.forEachIndexed { col, _ ->
        if (lineList[row][col] == 'M') {
            //println(lineList[row][col])
            interestedVectors.forEach { direction ->
                // A
                val aRow = row + direction[0].first
                val aCol = col + direction[0].second
                if (!withinBounds(aRow, aCol) || lineList[aRow][aCol] != 'A') {
                    return@forEach
                }

                // S
                val sRow = row + direction[1].first
                val sCol = col + direction[1].second
                if (!withinBounds(sRow, sCol) || lineList[sRow][sCol] != 'S') {
                    return@forEach
                }

                val msRow = row + direction[2].first
                val msCol = col + direction[2].second
                val msRow2 = row + direction[3].first
                val msCol2 = col + direction[3].second
                if (!withinBounds(msRow, msCol) || !withinBounds(msRow2, msCol2)) {
                    return@forEach
                }
                if ((lineList[msRow][msCol] == 'M' && lineList[msRow2][msCol2] == 'S') || (lineList[msRow][msCol] == 'S' && lineList[msRow2][msCol2] == 'M')) {
                    println(listOf(row, col, msRow, msCol, aRow, aCol, sRow, sCol))

                    count++
                }
            }
        }
    }
}

count
