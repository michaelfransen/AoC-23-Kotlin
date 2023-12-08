package Days

import java.io.File
import java.lang.Exception

class Day7: Day {

    private var hands = File("src/main/resources/Data/Day_7.txt").readLines().map(::Hand)

    override fun executePartOne() {
        print(hands.sorted().mapIndexed { index, hand -> (index + 1) * hand.bid }.sum())
    }

    override fun executePartTwo() {
    }
}

data class Hand(val input: String): Comparable<Hand> {
    val bid: Int
    var values: List<Int>

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
        }
    }

    val value: Int
        get() {
            val valueSet = values.groupingBy { it }.eachCount().toMutableMap()
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