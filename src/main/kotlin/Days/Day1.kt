package Days

import java.io.File


class Day1: Day {
    private val results: List<String>
        get() {
            return File("src/main/resources/Data/Day_1.txt").readLines()
        }

    private val map: Map<String, String> = mapOf(
        "1" to "1",
        "2" to "2",
        "3" to "3",
        "4" to "4",
        "5" to "5",
        "6" to "6",
        "7" to "7",
        "8" to "8",
        "9" to "9",
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9",

    )
    override fun executePartOne() {
        var total = 0

        results.forEach {
            if (it.isEmpty()) { return }

            val firstNumber = it.first { number -> number.isDigit() }
            val lastNumber = it.last { number -> number.isDigit() }

            total += String(charArrayOf(firstNumber, lastNumber)).toInt()
        }

        println(total)
    }

    override fun executePartTwo() {
        val result = results
            .map { String(charArrayOf(firstValue(it), lastValue(it))) }
            .sumOf { it.toInt() }

        print(result)
    }

    private fun firstValue(word: String): Char {
        val keys = map.keys
        var lowestIndex: Int? = null
        var lowestKey: String? = null

        keys.forEach {
            val index = word.indexOf(it)
            if (index == -1) {
                return@forEach
            }

            if (lowestIndex == null) {
                lowestIndex = index
                lowestKey = it
            } else {
                if (index < lowestIndex!!) {
                    lowestIndex = index
                    lowestKey = it
                }
            }
        }

        return map[lowestKey]!!.first()
    }

    private fun lastValue(word: String): Char {
        val keys = map.keys
        var highestIndex: Int? = null
        var highestKey: String? = null

        keys.forEach {
            val index = word.lastIndexOf(it)

            if (index == -1) {
                return@forEach
            }

            if (highestIndex == null) {
                highestIndex = index
                highestKey = it
            } else {
                if (index > highestIndex!!) {
                    highestIndex = index
                    highestKey = it
                }
            }
        }

        return map[highestKey]!!.first()
    }
}