package Days

import java.io.File
import java.lang.Exception

class Day7: Day {

    private var hands = File("src/main/resources/Data/Day_7.txt").readLines()

    override fun executePartOne() {
        println(
            hands.map { Hand(it, false) }
                .sorted()
                .mapIndexed { index, hand -> (index + 1) * hand.bid }
                .sum()
        )
    }

    override fun executePartTwo() {
        println(
            hands.map { Hand(it, true) }
                .sorted()
                .mapIndexed { index, hand -> (index + 1) * hand.bid }
                .sum()
        )
    }
}

data class Hand(val input: String, val usesJokers: Boolean): Comparable<Hand> {
    val bid: Int
    var values: MutableList<Int>

    private val regex = Regex("([1-9TJQKA]{5}) (\\d+)")

    init {
        bid = regex.find(input)!!.groups[2]!!.value.toInt()
        values = regex.find(input)!!.groups[1]!!.value.toCharArray().map {
            when {
                it.isDigit() -> it.toString().toInt()
                it == 'T' -> 10
                it == 'J' -> 11
                it == 'Q' -> 12
                it == 'K' -> 13
                it == 'A' -> 14
                else -> throw Exception()
            }
        }.toMutableList()
    }

    private val value: Int
        get() {
            if (usesJokers) {
                values.replaceAll { if (it == 11) 1 else it }
            }

            val valueSet = values.groupingBy { it }.eachCount().toMutableMap()

            if (usesJokers && valueSet.count() > 1 && valueSet.contains(1)) {
                val jokerCount = valueSet.remove(1)!!
                val mostFrequentKey = valueSet.maxBy { it.value }.key
                valueSet[mostFrequentKey] = valueSet[mostFrequentKey]!!.plus(jokerCount)
            }

            return when {
                valueSet.count() == 1 -> 7
                valueSet.values.contains(4) -> 6
                valueSet.count() == 2 && valueSet.values.contains(3) -> 5
                valueSet.values.contains(3) -> 4
                valueSet.values.count { it == 2 } == 2 -> 3
                valueSet.values.contains(2) -> 2
                valueSet.count() == 5 -> 1
                else -> 0
            }
        }

    override fun compareTo(other: Hand): Int {
        val thisValue = this.value
        val otherValue = other.value

        if (thisValue != otherValue) {
            return if (thisValue > otherValue) { 1 } else { -1 }
        }

        var i = 0

        while (true) {
            if (this.values[i] != other.values[i]) {
                return if (this.values[i] > other.values[i]) { 1 } else { -1 }
            }

            if (i == this.values.count() - 1) {
                return 0
            }

            i++
        }
    }
}