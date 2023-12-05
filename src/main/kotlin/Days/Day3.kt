package Days

import java.io.File

class Day3: Day {
    private val results: List<String>
        get() {
            return File("src/main/resources/Data/Day_3.txt").readLines()
        }

    private val schematic = Schematic(results)

    override fun executePartOne() {
        println(
            schematic
                .numbers
                .filter { it.isAdjacentToAny(schematic.symbols) }
                .sumOf { it.number }
        )
    }

    override fun executePartTwo() {
        TODO("Not yet implemented")
    }
}

private class Schematic(input: List<String>) {
    val symbols: MutableSet<Location> = mutableSetOf()
    val numbers: MutableList<NumberLocation> = mutableListOf()

    init {
        var numberInProgress = NumberLocation()

        input.forEachIndexed { y, s ->
            s.forEachIndexed { x, c ->
                if (c.isDigit()) {
                    numberInProgress.add(c, Location(x, y))
                } else {
                    if (c != '.') {
                        symbols.add(Location(x, y))
                    }

                    if (numberInProgress.isNotEmpty) {
                        numbers.add(numberInProgress)
                        numberInProgress = NumberLocation()
                    }
                }
            }

            if (numberInProgress.isNotEmpty) {
                numbers.add(numberInProgress)
                numberInProgress = NumberLocation()
            }
        }
    }
}

private class NumberLocation {
    private val chars: MutableList<Char> = mutableListOf()
    private val locations: MutableSet<Location> = mutableSetOf()

    val number: Int
        get() { return String(chars.toCharArray()).toInt() }
    private val borderPoints: Set<Location>
        get() {
            val borderPoints: MutableSet<Location> = mutableSetOf()

            val y = locations.first().y
            val minX = locations.minBy { it.x }.x - 1
            val maxX = locations.maxBy { it.x }.x + 1

            borderPoints.add(Location(minX, y))
            borderPoints.add(Location(maxX, y))

            for (x in minX..maxX) {
                borderPoints.add(Location(x, y - 1))
                borderPoints.add(Location(x, y + 1))
            }

            return borderPoints
        }

    fun add(char: Char, location: Location) {
        chars.add(char)
        locations.add(location)
    }

    fun isAdjacentToAny(symbolLocations: Set<Location>): Boolean {
        return symbolLocations.intersect(borderPoints).isNotEmpty()
    }

    val isNotEmpty: Boolean
        get() = chars.isNotEmpty()

    override fun toString(): String {
        return number.toString()
    }
}

data class Location(val x: Int, val y: Int)