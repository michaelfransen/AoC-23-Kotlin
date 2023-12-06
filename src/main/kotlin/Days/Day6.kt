package Days

import java.io.File

class Day6: Day {
    private val input = File("src/main/resources/Data/Day_6.txt").readLines()

    private var regex = Regex("\\d+")

    override fun executePartOne() {
        val parsedData = input.map {
            regex.findAll(it).map { result -> result.value.toLong() }
        }

        val races = (0..<parsedData[0].count()).map {
            Race(parsedData[0].elementAt(it), parsedData[1].elementAt(it))
        }

        println( races.map { it.numberOfWaysToWin }.reduce { acc, i -> acc * i })
    }

    override fun executePartTwo() {
        val parsedData = input.map {
            regex.findAll(it).map { result -> result.value }.reduce { acc, s -> acc + s }
        }.map { it.toLong() }

       println(Race(parsedData[0], parsedData[1]).numberOfWaysToWin)
    }
}

data class Race(val time: Long, val recordDistance: Long) {
    val numberOfWaysToWin: Long
        get() {
            var numberOfWays = 0L
            for (i in 1..<time) {
                if ((time - i) * i > recordDistance) {
                    numberOfWays++
                }
            }
            return numberOfWays
        }
}