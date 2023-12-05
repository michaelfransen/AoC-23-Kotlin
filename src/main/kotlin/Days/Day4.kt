package Days

import java.io.File
import kotlin.math.pow

class Day4: Day {
    private val cards = File("src/main/resources/Data/Day_4.txt").readLines().map(::Card)
    override fun executePartOne() {
        println(cards.sumOf { it.score })
    }

    override fun executePartTwo() {
        println(cards.sumOf { processCard(it) })
    }

    private fun processCard(card: Card): Int {
        if (card.id == cards.count()) { return 1 }

        return cards
            .subList(card.id, card.id + card.numberOfWinningMatches)
            .sumOf { processCard(it) } + 1
    }
}

class Card(input: String) {
    val id: Int
    private val winningNumbers: Set<Int>
    private val numbers: Set<Int>

    val score: Int
        get() {
           return 2.0.pow(winningNumbers.intersect(numbers).count().toDouble() - 1).toInt()
        }

    val numberOfWinningMatches: Int
        get() {
            return winningNumbers.intersect(numbers).count()
        }

    private val parse: (input: String, regex: Regex) -> (Set<Int>) = { value, regex ->
        regex.find(value)!!.groups[1]!!.value
            .split(" ")
            .filter { it.isNotEmpty() }
            .map { it.toInt() }
            .toSet()
    }

    init {
        id = Regex("Card +(\\d+):").find(input)!!.groups[1]!!.value.toInt()
        winningNumbers = parse(input, Regex("Card +\\d+: +((\\d+ +)*)"))
        numbers = parse(input, Regex("\\|(( +\\d+)*)"))
    }
}