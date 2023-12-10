package days

import java.io.File

typealias Position = Pair<Int, Int>

class Day10: Day {

    private var pipes = File("src/main/resources/Data/Day_10.txt").readLines().map { it ->
        it.toCharArray().map { char -> PipeType.fromChar(char) }
    }

    override fun executePartOne() {
        val startingPosition = pipes.mapIndexed { y, values ->
            val x = values.indexOf(PipeType.Start)
            if (x == -1 ) null else Pair(x, y) }
            .filterNotNull()
            .first()

        println(determinePathFor(startingPosition).count() / 2)
    }

    override fun executePartTwo() {
        val path = determinePathFor(
            pipes.mapIndexed { y, values ->
                val x = values.indexOf(PipeType.Start)
                if (x == -1 ) null else Pair(x, y) }
                .filterNotNull()
                .first()
        ).toSet()

        var area = 0
        val boundaries: List<PipeType> = listOf(PipeType.NorthSouth, PipeType.NorthWest, PipeType.NorthEast)

        (0..<pipes.count()).forEachIndexed { y, _ ->
            (0..<pipes.first().count()).forEachIndexed { x, _ ->
                // Is part of the pipe loop, so doesn't count
                if (path.contains(Position(x, y))) {
                    return@forEachIndexed
                }

                val numberOfItemsToLeft = path
                    .filter { it.second == y && it.first < x }
                    .count { boundaries.contains(pipes[it.second][it.first]) }

                if (numberOfItemsToLeft % 2 == 0) {
                    return@forEachIndexed
                } else {
                    area++
                }
            }
        }

        println(area)
    }

    private fun determinePathFor(startingPosition: Position): List<Position> {
        val list: MutableList<Position> = mutableListOf(startingPosition)

        var next: Position? = getConnectedNeighbors(startingPosition).first()

        while (next != null) {
            list.add(next)
            next = getConnectedNeighbors(next).firstOrNull() { !list.contains(it) }
        }

        return list
    }


    private fun getConnectedNeighbors(position: Position): List<Position> {
        val returnValue: MutableList<Position> = mutableListOf()
        val availableDirections = pipes[position.second][position.first].connectableDirections

        // North
        if (availableDirections.contains(Direction.North) && position.second > 0) {
            val northNeighbor = pipes[position.second - 1][position.first].connectableDirections
            if (northNeighbor.contains(Direction.South)) {
                returnValue.add(Position(position.first, position.second - 1))
            }
        }

        // East
        if (availableDirections.contains(Direction.East) && position.first < pipes.first().count() - 1) {
            val eastNeighbor = pipes[position.second][position.first + 1].connectableDirections
            if (eastNeighbor.contains(Direction.West)) {
                returnValue.add(Position(position.first + 1, position.second))
            }
        }

        // South
        if (availableDirections.contains(Direction.South) && position.second < pipes.count() - 1) {
            val southNeighbor = pipes[position.second + 1][position.first].connectableDirections
            if (southNeighbor.contains(Direction.North)) {
                returnValue.add(Position(position.first, position.second + 1))
            }
        }

        // West
        if (availableDirections.contains(Direction.West) && position.first > 0) {
            val westNeighbor = pipes[position.second][position.first - 1].connectableDirections
            if (westNeighbor.contains(Direction.East)) {
                returnValue.add(Position(position.first - 1, position.second))
            }
        }

        return returnValue
    }
}

enum class Direction {
    North,
    East,
    South,
    West
}

enum class PipeType(val char: Char) {
    WestEast('-'),
    NorthSouth('|'),
    NorthEast('L'),
    NorthWest('J'),
    SouthWest('7'),
    SouthEast('F'),
    NoPipe('.'),
    Start('S');

    val connectableDirections: Set<Direction>
        get() {
            return when (this) {
                Start -> setOf(Direction.North, Direction.East, Direction.South, Direction.West)
                NoPipe -> setOf()
                SouthEast -> setOf(Direction.South, Direction.East)
                SouthWest -> setOf(Direction.South, Direction.West)
                NorthWest -> setOf(Direction.North, Direction.West)
                NorthEast -> setOf(Direction.North, Direction.East)
                NorthSouth -> setOf(Direction.North, Direction.South)
                WestEast -> setOf(Direction.West, Direction.East)
            }
        }

    companion object {
        fun fromChar(char: Char): PipeType {
            return entries.first { it.char == char }
        }
    }
}