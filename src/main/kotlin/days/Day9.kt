package days

import java.io.File

class Day9: Day {
    private var historySets = File("src/main/resources/Data/Day_9.txt").readLines().map(::HistorySet)

    override fun executePartOne() {
        println(historySets.sumOf { it.nextValue })
    }

    override fun executePartTwo() {
        println(historySets.sumOf { it.previousValue })
    }
}

class HistorySet(private val input: String) {
    private val history: List<Int> = input.split(" ").map { it.toInt() }

    val nextValue: Int
        get() {
            val rows = generate(history)
            return rows.subList(0, rows.count() - 1)
                .reversed()
                .fold(0) { acc, ints ->
                    acc + ints.last()
                } + history.last()
        }

    val previousValue: Int
        get() {
            val rows = generate(history)
            return generate(history)
                .subList(0, rows.count() - 1)
                .reversed()
                .fold(0) { acc, history ->
                    -1 * acc + history.first()
                } * -1 + history.first()
        }

    private fun generate(row: List<Int>): List<List<Int>> {
        val nextRow = row.windowed(2) { it[1] - it[0] }

        return if (nextRow.any { it != 0 }) {
            listOf(nextRow) + generate(nextRow)
        } else {
            listOf(nextRow + listOf(0))
        }
    }
}