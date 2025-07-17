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

val seeds = lineList[0].trimMargin("seeds: ").split(" ").map { it.toLong() }

data class Something(
    val destinationRangeStart: Long,
    val sourceRangeStart: Long,
    val rangeLength: Long,
)

val seedToSoil = mutableListOf<Something>()
val soilToFertilizer = mutableListOf<Something>()
val fertilizerToWater = mutableListOf<Something>()
val waterToLight = mutableListOf<Something>()
val lightToTemperature = mutableListOf<Something>()
val temperatureToHumidity = mutableListOf<Something>()
val humidityToLocation = mutableListOf<Something>()

var currentMap = seedToSoil
for (line in lineList.drop(2)) {
    if (line.isBlank()) {
        continue
    } else if (line.contains("seed-to-soil map:")) {
        currentMap = seedToSoil
    } else if (line.contains("soil-to-fertilizer map:")) {
        currentMap = soilToFertilizer
    } else if (line.contains("fertilizer-to-water map:")) {
        currentMap = fertilizerToWater
    } else if (line.contains("water-to-light map:")) {
        currentMap = waterToLight
    } else if (line.contains("light-to-temperature map:")) {
        currentMap = lightToTemperature
    } else if (line.contains("temperature-to-humidity map:")) {
        currentMap = temperatureToHumidity
    } else if (line.contains("humidity-to-location map:")) {
        currentMap = humidityToLocation
    } else {
        val numbers = line.split(" ").map { it.toLong() }
        currentMap.add(Something(numbers[0], numbers[1], numbers[2]))
    }
}

println(seedToSoil)
println(soilToFertilizer)
println(fertilizerToWater)
println(waterToLight)
println(lightToTemperature)
println(temperatureToHumidity)
println(humidityToLocation)

fun calculate(something: List<Something>, seed: Long): Long {
    for (item in something) {
        if (item.sourceRangeStart <= seed && seed < item.sourceRangeStart + item.rangeLength) {
            return seed - item.sourceRangeStart + item.destinationRangeStart
        }
    }
    return seed
}

seeds.map { seed ->
    val soil = calculate(seedToSoil, seed)
    val fertilizer = calculate(soilToFertilizer, soil)
    val water = calculate(fertilizerToWater, fertilizer)
    val light = calculate(waterToLight, water)
    val temperature = calculate(lightToTemperature, light)
    val humidity = calculate(temperatureToHumidity, temperature)
    val location = calculate(humidityToLocation, humidity)

    println("seed $seed location $location")

    location
}.min()

