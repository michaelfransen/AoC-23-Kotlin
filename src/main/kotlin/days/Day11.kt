package days

import java.io.File
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day11 : Day {
    private val input = File("src/main/resources/Data/Day_11.txt")
        .readLines()
        .map { it.toMutableList() }
        .toMutableList()

    private val emptyRowIndexes: Set<Int>
        get() {
            return input.indices.filter { !input[it].contains('#') }.toSet()
        }

    private val emptyColumnIndexes: Set<Int>
        get() {
            return (0..<input.first().count()).mapNotNull { x ->
                input.forEach { row ->
                    if (row[x] == '#') {
                        return@mapNotNull null
                    }
                }

                return@mapNotNull x
            }.toSet()
        }

    override fun executePartOne() {
        print(sumOfPathsFor(1))
    }

    override fun executePartTwo() {
        print(sumOfPathsFor(999999))
    }


    private fun sumOfPathsFor(multiplier: Long): Long {
        val galaxyLocations = retrieveGalaxyLocations()

        return galaxyLocations.flatMapIndexed { index, first ->
            galaxyLocations.drop(index + 1).map { second -> Pair(first, second) }
        }.sumOf { pair ->
            val expansionColumnCount = emptyColumnIndexes.count {
                it > min(pair.first.x, pair.second.x) && it < max(pair.first.x, pair.second.x)
            }
            val expansionRowCount = emptyRowIndexes.count {
                it > min(pair.first.y, pair.second.y) && it < max(pair.first.y, pair.second.y)
            }

            abs(pair.first.x - pair.second.x).toLong() +
                    abs(pair.first.y - pair.second.y).toLong() +
                    expansionColumnCount * multiplier + expansionRowCount * multiplier
        }
    }

    private fun retrieveGalaxyLocations(): List<Location> {
        val locations: MutableList<Location> = mutableListOf()
        for (x in 0..<input.first().count()) {
            for (y in 0..<input.count()) {
                if (input[y][x] == '#') {
                    locations.add(Location(x, y))
                }
            }
        }

        return locations
    }
}