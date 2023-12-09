package days

import java.io.File

class Day9: Day {
    private var historySets = File("src/main/resources/Data/Day_9.txt").readLines().map(::HistorySet)

    override fun executePartOne() {
        println(
            historySets.sumOf { it.nextValue }
        )
    }

    override fun executePartTwo() {
        println(
            historySets.sumOf { it.previousValue }
        )
    }
}

class HistorySet(val input: String) {
    private val history: List<Int> = input.split(" ").map { it.toInt() }

    val nextValue: Int
        get() {
            var valueToAdd = 0

            val rows = generate(history)
            for (row in rows.subList(0, rows.count() - 1).reversed()) {
                valueToAdd += row.last()
            }


            return history.last() + valueToAdd
        }

    val previousValue: Int
        get() {
            var valueToAdd = 0

            val rows = generate(history)
            for (row in rows.subList(0, rows.count() - 1).reversed()) {
                valueToAdd = -1 * valueToAdd + row.first()
            }


            return history.first() + valueToAdd * -1
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