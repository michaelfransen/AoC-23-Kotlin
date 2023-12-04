package Days

import java.io.File
import kotlin.math.pow

class Day4: Day {
    val cards = File("src/main/resources/Data/Day_4.txt").readLines().map(::Card)
    override fun executePartOne() {
        println(cards.sumOf { it.score })
    }

    override fun executePartTwo() {
        TODO("Not yet implemented")
    }
}
class Card(input: String) {
    val winningNumbers: Set<Int>
    val numbers: Set<Int>

    val winningRegex = Regex("Card +\\d+: +((\\d+ +)*)")
    val numbersRegex = Regex("\\|(( +\\d+)*)")

    val score: Int
        get() {
           return 2.0.pow(winningNumbers.intersect(numbers).count().toDouble() - 1).toInt()
        }

    init {
        winningNumbers = winningRegex
            .find(input)?.
            groups?.
            get(1)?.
            value?.
            split(" ")?.
            filter { it.isNotEmpty() }?.
            map { it.toInt() }!!
            .toSet()

        numbers = numbersRegex
            .find(input)?.
            groups?.
            get(1)?.
            value?.
            split(" ")?.
            filter { it.isNotEmpty() }?.
            map { it.toInt() }!!
            .toSet()
    }
}