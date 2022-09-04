import java.io.File
import java.io.InputStream
import kotlin.contracts.contract

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()

inputStream.bufferedReader().forEachLine {
    lineList.add(it)
}

var total = lineList.size
val onesCount = MutableList(12) { 0 }
println("$onesCount")

onesCount.fill(0)
for (line in lineList) {
    for ((index, bit) in line.withIndex()) {
        if (bit.toString() == "1") onesCount[index]++
    }
}

println("$onesCount")
println("$total")

val gammaRateBinary: String = onesCount.map { if (it > total/2) 1 else 0 }.joinToString("")
val gammaRate = Integer.parseInt(gammaRateBinary, 2)
val epsilonRateBinary = onesCount.map { if (it <= total/2) 1 else 0 }.joinToString("")
val epsilonRate = Integer.parseInt(epsilonRateBinary, 2)
println("$epsilonRateBinary")
println("$gammaRateBinary")

println("$gammaRate and $epsilonRate")
println("${gammaRate * epsilonRate}")

//lineList.forEach { println("$it") }