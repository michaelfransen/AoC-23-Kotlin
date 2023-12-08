package days

import java.io.File

class Day2: Day {
    private val results: List<String>
        get() {
            return File("src/main/resources/Data/Day_2.txt").readLines()
        }

    private val games = results.map(::Game)
    override fun executePartOne() {
        val result = games.filter { it.isValid(12, 13, 14) }
            .sumOf { it.id }
        println(result)
    }

    override fun executePartTwo() {
        val result = games.sumOf { it.power }
        print(result)
    }
}

class Pull constructor(input: String) {
    private val redRegex = Regex("(\\d+) red")
    private val greenRegex = Regex("(\\d+) green")
    private val blueRegex = Regex("(\\d+) blue")

    val redCount: Int
    val greenCount: Int
    val blueCount: Int

    init {
        redCount = redRegex.find(input)?.groups?.get(1)?.value?.toInt() ?: 0
        greenCount = greenRegex.find(input)?.groups?.get(1)?.value?.toInt() ?: 0
        blueCount = blueRegex.find(input)?.groups?.get(1)?.value?.toInt() ?: 0
    }

    fun isValid(maxRed: Int, maxGreen: Int, maxBlue: Int): Boolean {
        return redCount <= maxRed && greenCount <= maxGreen && blueCount <= maxBlue
    }
}

class Game constructor(input: String) {
    val id: Int
    val pulls: List<Pull>

    private val gameRegex = Regex("\\d+")

    init {
        id = gameRegex.find(input)!!.value.toInt()
        pulls = input
            .substring(
                Regex("Game \\d+: ")
                    .find(input)!!
                    .range
                    .last + 1
            )
            .split(";")
            .map(::Pull)
    }

    private val fewestRedNeeded: Int
        get() { return pulls.maxOf { it.redCount } }

    private val fewestGreenNeeded: Int
        get() { return pulls.maxOf { it.greenCount } }

    private val fewestBlueNeeded: Int
        get() { return pulls.maxOf { it.blueCount } }
    
    val power: Int
        get() {
            return listOf(fewestRedNeeded, fewestGreenNeeded, fewestBlueNeeded)
                .filter { it != 0 }
                .reduce(Int::times)
        }

    fun isValid(maxRed: Int, maxGreen: Int, maxBlue: Int): Boolean {
        return pulls.none { !it.isValid(maxRed, maxGreen, maxBlue) }
    }
}