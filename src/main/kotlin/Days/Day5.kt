package Days

import java.io.File

class Day5: Day {
    private val almanac = Almanac(File("src/main/resources/Data/Day_4.txt").readLines())
    override fun executePartOne() {
        println(almanac.lowestLocation())
    }

    override fun executePartTwo() {
        TODO("Not yet implemented")
    }
}

private class Almanac(input: List<String>) {
    val orderedMaps: List<Map>
    val seeds: List<Int>

    init {
        orderedMaps = listOf()
        seeds = listOf()
    }

    fun lowestLocation(): Int {
        // Figure out the final destination for each seed, by looping through the ordered maps. We actually
        // should be able to do this with just looping through them once because we can figure out all the seeds
        // destinations together.
        return 0
    }
}

private class Map(input: String) {
    val ranges: List<Range>

    init {
        // Replace
        ranges = listOf()
    }

    fun destinationFor(source: Int): Int {
        // Logic to find the destination based on the provided ranges
        return 0
    }
}

private class Range(input: String) {
    val source: Int
    val destination: Int
    val length: Int

    init {
        // Replace
        source = 0
        destination = 0
        length = 0
    }
}