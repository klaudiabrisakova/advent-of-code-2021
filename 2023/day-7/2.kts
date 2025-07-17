import java.io.File
import java.io.InputStream
import java.lang.Math.pow
import kotlin.contracts.contract


val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine { lineList.add(it) }

val list = mutableListOf<MutableList<Long>>()
list.add(mutableListOf())
var index = 0

data class Line(
    val hand: List<Int>,
    val handString: String,
    val bid: Int,
    val type: Int
)

val lines = lineList.map { line ->
    val split = line.split(" ")
    val bid = split.last().toInt()

    val hand = split.first().split("").filter { it.isNotBlank() }
        .map {
            when (it) {
                "T" -> 10
                "J" -> 1
                "Q" -> 12
                "K" -> 13
                "A" -> 14
                else -> it.toInt()
            }
        }

    val cardToCount = hand.associate { card -> card to hand.count { it == card }}.toMutableMap()
    println(cardToCount)

    val jokersCount = cardToCount.remove(1)
    if (jokersCount != null ) {
        if (cardToCount.isEmpty()) {
            cardToCount[14] = 5
        } else {
            val max = cardToCount.maxBy { it.value }
            cardToCount.replace(max.key, max.value + jokersCount)
        }
    }

    val type = when {
        cardToCount.containsValue(5) -> 70 // five of a kind
        cardToCount.containsValue(4) -> 60 // four of a kind
        cardToCount.containsValue(3) && cardToCount.containsValue(2) -> 50 // full house
        cardToCount.containsValue(3) && cardToCount.containsValue(1) -> 40 // three of a kind
        cardToCount.containsValue(2) && cardToCount.containsValue(1) && cardToCount.size == 3 -> 30 // two pair
        cardToCount.containsValue(2) && cardToCount.containsValue(1) && cardToCount.size == 4 -> 20 // one pair
        cardToCount.size == 5 -> 10 // high card
        else -> 1
    }
    Line(hand, split.first(), bid, type)
}

println(lines)

val comparator = Comparator<Line> { a, b ->
    if (a.type != b.type) {
        println("type A ${a.type} B ${b.type} ${a.type - b.type}")
        return@Comparator a.type - b.type
    }
    else {
        for (i in 0..4) {
            if (a.hand[i] != b.hand[i]) {
                println("hand A ${a.hand[i]} B ${b.hand[i]} ${a.hand[i] - b.hand[i]}")
                return@Comparator a.hand[i] - b.hand[i]
            }
        }
        0
    }
}

val sorted = lines.sortedWith(comparator)

println(sorted.map { it.handString })

sorted.foldIndexed(0) { index, acc, line ->
    acc + (index + 1) * line.bid
}