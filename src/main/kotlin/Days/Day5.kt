package Days

import java.io.File

class Day5: Day {
    private val almanac = Almanac(File("src/main/resources/Data/Day_5.txt").readLines())
    override fun executePartOne() {
        println(almanac.lowestLocation())
    }

    override fun executePartTwo() {
        var result = Long.MAX_VALUE
        almanac.seeds.chunked(2) { LongRange(it[0], it[0] + it[1] - 1) }
            .forEach { range ->
                range.forEach {
                    val location = almanac.lowestLocation(it)
                    if (location < result) {
                        result = location
                    }
                }
            }

        println(result)
    }
}

private class Almanac(input: List<String>) {
    val orderedMaps: MutableList<Map> = mutableListOf()
    var seeds: List<Long>

    init {
        seeds = Regex("seeds: (((\\d+) ?)+)")
            .find(input.first())!!
            .groups[1]!!
            .value
            .split(" ")
            .map { it.toLong() }

        val headerRowRegex = Regex("map:")
        val numbersRowRegex = Regex("(\\d+ ?)+")

        var inMap = false
        val numberList: MutableList<List<Long>> = mutableListOf()

        input.forEachIndexed { index, s ->
            if (headerRowRegex.find(s) != null) {
                inMap = true
            }

            if (inMap && numbersRowRegex.matches(s)) {
                numberList.add(
                    numbersRowRegex
                        .find(s)!!
                        .groups[0]!!
                        .value
                        .split(" ")
                        .map { it.toLong() }
                )
            }

            if ((s.isEmpty() || index == input.count() - 1) && numberList.isNotEmpty()) {
                inMap = false
                orderedMaps.add(Map(numberList))
                numberList.clear()
            }
        }
    }

    fun lowestLocation(): Long =
        seeds.minOf {
            lowestLocation(it)
        }

    fun lowestLocation(seedValue: Long) : Long =
        orderedMaps.fold(seedValue) { value, map ->
            map.destinationFor(value)
        }

}

private class Map(input: List<List<Long>>) {
    val ranges: List<Range>

    init {
        ranges = input.map { Range(it[1], it[0], it[2]) }
    }

    fun destinationFor(source: Long): Long =
        ranges.firstOrNull { LongRange(it.source, it.source + it.length - 1).contains(source) }?.
        let { source - it.source + it.destination } ?: source
}

data class Range(val source: Long, val destination: Long, val length: Long) { }