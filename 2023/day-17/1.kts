import java.io.File
import java.io.InputStream
import java.lang.Math.abs
import java.lang.Math.pow
import java.util.PriorityQueue
import java.util.Queue
import kotlin.contracts.contract
import kotlin.math.ceil


val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val map = lineList.map { line -> line.map { it.digitToInt() } }

map.forEach {
    println(it.joinToString(""))
}

data class Position(
    val rowIndex: Int,
    val colIndex: Int,
    val direction: Char
)

data class ToSearch(
    val position: Position,
    val heatLoss: Int,
)

val startingPosition = Position(0, 0, 'R')
val endPositions = listOf(Position(map.size - 1, map.first().size - 1, 'R'), Position(map.size - 1, map.first().size - 1, 'D'))
val unraechable = 100000

fun djikstra(graph: List<List<Int>>, source: Position): Pair<Pair<MutableMap<Position, Int>, MutableMap<Position, Position>>, MutableMap<Position, Pair<Char, Position>>> {
    val dist = mutableMapOf<Position, Int>()
    val prev = mutableMapOf<Position, Position>()
    val dir = mutableMapOf<Position, Pair<Char, Position>>()

    dist[source] = 0

    val queue = PriorityQueue<ToSearch> { search1, search2 ->
        search1.heatLoss - search2.heatLoss
    }

    graph.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { colIndex, _ ->
            for (direction in listOf('R', 'L', 'U', 'D')) {
                val position = Position(rowIndex, colIndex, direction)
                if (position != source) {
                    dist[position] = unraechable
                    // prev[v] ← UNDEFINED
                    // dir
                }
                queue.add(ToSearch(position, dist.getValue(position)))
            }
        }
    }

    var index= 0
    while (queue.isNotEmpty()) {
        index++
//        if (index >40) {
//            break
//        }
        val u = queue.poll()
        if (dist[u.position]!! > unraechable) {
            continue
        }
        println()
        println("${u.position} heatloss:${u.heatLoss} coming from:${prev[u.position]} ${dir[u.position]} distance ${dist[u.position]}")
        val position = u.position

        fun findLastThreeDir(position: Position, directions: Map<Position, Pair<Char, Position>>, depth: Int): List<Char> {
            if (depth >= 3) {
                return emptyList()
            }

            if (!directions.containsKey(position)) {
                return emptyList()
            }
            val (direction, prevPosition) = directions[position]!!

            return listOf(direction) + findLastThreeDir(prevPosition, directions, depth + 1)
        }

        val lastThreeDirections = findLastThreeDir(position, dir, 0).joinToString("")
        var neighbours = mutableListOf<Pair<Position, Char>>()
        val right = Position(position.rowIndex, position.colIndex + 1, 'R')
        val left = Position(position.rowIndex, position.colIndex - 1, 'L')
        val down = Position(position.rowIndex + 1, position.colIndex, 'D')
        val up = Position(position.rowIndex - 1, position.colIndex, 'U')
        println(lastThreeDirections)
        if (lastThreeDirections != "RRR") {
            neighbours.add(right to 'R')
        }
        if (lastThreeDirections != "LLL") {
            neighbours.add(left to 'L')
        }
        if (lastThreeDirections != "UUU") {
            neighbours.add(up to 'U')
        }
        if (lastThreeDirections != "DDD") {
            neighbours.add(down to 'D')
        }

        val dirMap = mapOf(
            'R' to listOf('R', 'D', 'U'),
            'L' to listOf('L', 'D', 'U'),
            'U' to listOf('U', 'R', 'L'),
            'D' to listOf('D', 'R', 'L')
        )
        neighbours = neighbours.filter { (it, _) -> it.rowIndex in graph.indices && it.colIndex in graph.first().indices }
            .filter { it.second in dirMap[position.direction]!! }
            .toMutableList()
        println("Neighbours $neighbours")

        neighbours.forEach { (v: Position, direction: Char) ->
            print("Neighbours $v $direction")
            val alt = dist.getValue(position) + graph[v.rowIndex][v.colIndex]
            println(" distance $alt")
            if (alt < dist.getValue(v)) {
                dist[v] = alt
                prev[v] = position
                dir[v] = direction to position
                val vInQueue = queue.find { it.position == v }
                vInQueue?.let {  queue.remove(it) }
                queue.add(ToSearch(v, alt))
            }
        }
    }

    return dist to prev to dir
}

val (first, dir) = djikstra(map, startingPosition)
val (dist, prev) = first


for (endPosition in endPositions) {
    println(dist[endPosition])

    val s = mutableListOf<Position>()
    var u: Position? = endPosition
    if (prev.containsKey(u) || u == startingPosition) {
        while (u != null) {
            s.add(0, u!!)
            u = prev[u]
        }
    }

//    println(s)

    val directionMap = mapOf(
        'R' to '>',
        'L' to '<',
        'U' to '^',
        'D' to 'v'
    )

    map.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { colIndex, col ->
            val find = s.find { it.rowIndex == rowIndex && it.colIndex == colIndex }
            if (find != null && (rowIndex != startingPosition.rowIndex || colIndex != startingPosition.colIndex)) {
                print(directionMap.getValue(dir.getValue(find).first))
            } else
                print(col)
        }
        println()
    }

}





