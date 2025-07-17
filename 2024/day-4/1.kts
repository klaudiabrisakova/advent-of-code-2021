import java.io.File
import java.io.InputStream
import kotlin.math.abs

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

lineList.map { it -> it.split("").filter { it.isNotBlank() } }

val interestedVectors = listOf(
    listOf(
        0 to 1,
        0 to 2,
        0 to 3
    ),
    listOf(
        0 to -1,
        0 to -2,
        0 to -3,
    ),
    listOf(
        1 to 0,
        2 to 0,
        3 to 0
    ),
    listOf(
        -1 to 0,
        -2 to 0,
        -3 to 0
    ), 
    listOf(
        1 to 1,
        2 to 2,
        3 to 3
    ),
    listOf(
        -1 to -1,
        -2 to -2,
        -3 to -3
    ), 
    listOf(
        -1 to 1,
        -2 to 2,
        -3 to 3
    ),
    listOf(
        1 to -1,
        2 to -2,
        3 to -3
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
        if (lineList[row][col] == 'X') {
            //println(lineList[row][col])
            interestedVectors.forEach { direction ->
                // M
                val mRow = row + direction[0].first
                val mCol = col + direction[0].second
                if (!withinBounds(mRow, mCol) || lineList[mRow][mCol] != 'M') {
                    return@forEach
                }

                // A
                val aRow = row + direction[1].first
                val aCol = col + direction[1].second
                if (!withinBounds(aRow, aCol) || lineList[aRow][aCol] != 'A') {
                    return@forEach
                }

                // S
                val sRow = row + direction[2].first
                val sCol = col + direction[2].second
                if (!withinBounds(sRow, sCol) || lineList[sRow][sCol] != 'S') {
                    return@forEach
                }

                println(listOf(row, col, mRow, mCol, aRow, aCol, sRow, sCol))

                count++
            }
        }
    }
}

count
