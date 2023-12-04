package Days

import java.io.File
import kotlin.math.pow

class Day4: Day {
    private val cards = File("src/main/resources/Data/Day_4.txt").readLines().map(::Card)
    override fun executePartOne() {
        println(cards.sumOf { it.score })
    }

    override fun executePartTwo() {
        TODO("Not yet implemented")
    }
}

class Card(input: String) {
    private val winningNumbers: Set<Int>
    private val numbers: Set<Int>

    val score: Int
        get() {
           return 2.0.pow(winningNumbers.intersect(numbers).count().toDouble() - 1).toInt()
        }

    private val parse: (input: String, regex: Regex) -> (Set<Int>) = { value, regex ->
        regex.find(value)!!.groups[1]!!.value
            .split(" ")
            .filter { it.isNotEmpty() }
            .map { it.toInt() }
            .toSet()
    }

    init {
        winningNumbers = parse(input, Regex("Card +\\d+: +((\\d+ +)*)"))
        numbers = parse(input, Regex("\\|(( +\\d+)*)"))
    }
}