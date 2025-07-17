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

val seedNumbers = lineList[0].trimMargin("seeds: ").split(" ").map { it.toLong() }

data class Seed(
    val start: Long,
    val end: Long, // including
)

val seeds = seedNumbers.chunked(2).map { Seed(it[0], it[0] + it[1] - 1) }

println(seeds)

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

fun calculate(maps: List<Something>, seed: Seed): List<Seed> {
    val allRangePoints = maps.map { it.sourceRangeStart } + maps.map { it.sourceRangeStart + it.rangeLength - 1 }.distinct()

    val seedRanges = (listOf(seed.start) + allRangePoints.filter { seed.start < it && it < seed.end }
        .sorted()
        .map { listOf(it, it) }
        .flatten()+ listOf(seed.end))
        .chunked(2)
        .map { Seed(it[0], it[1]) }

    println(seedRanges)

    return seedRanges.map { seedRange ->
        println("seedRange $seedRange")
        for (item in maps) {
            println("item $item")
            if ((item.sourceRangeStart <= seedRange.start && seedRange.start < item.sourceRangeStart + item.rangeLength) &&
                (item.sourceRangeStart <= seedRange.end && seedRange.end < item.sourceRangeStart + item.rangeLength)) {
                val soilStart = seedRange.start - item.sourceRangeStart + item.destinationRangeStart
                val soilEnd = seedRange.end - item.sourceRangeStart + item.destinationRangeStart
                return@map Seed(soilStart, soilEnd)
            }
        }
        seedRange
    }
}

seeds.flatMap { seed ->
    val soils = calculate(seedToSoil, seed)
    val fertilizers = soils.flatMap { soil -> calculate(soilToFertilizer, soil) }
    val waters = fertilizers.flatMap { fertilizer -> calculate(fertilizerToWater, fertilizer) }
    val lights = waters.flatMap { water -> calculate(waterToLight, water) }
    val temperatures = lights.flatMap { light -> calculate(lightToTemperature, light) }
    val humidities =temperatures.flatMap { temperature -> calculate(temperatureToHumidity, temperature) }
    val locations = humidities.flatMap { humidity -> calculate(humidityToLocation, humidity) }

    println("seed $seed location $locations")

    locations.map { it.start }
}.min()

