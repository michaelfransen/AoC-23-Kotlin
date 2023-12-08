package days

import java.io.File

class Day8: Day {
    private var input = File("src/main/resources/Data/Day_8.txt").readLines()
    private val nodeRegex = Regex("([A-Z]{3}) = \\(([A-Z]{3}), ([A-Z]{3})\\)")

    override fun executePartOne() {
        val instructions = input[0].toCharArray()
        val nodes = input.subList(2, input.count())
            .map {
                val groups = nodeRegex.find(it)!!.groups
                Node(groups[1]!!.value, groups[2]!!.value, groups[3]!!.value)
            }.associateBy { it.node }

        var i = 0
        var numberOfSteps = 0
        var node = nodes["AAA"]!!

        while (i >= 0) {
            when (instructions[i]) {
                'L' -> node = nodes[node.leftNode]!!
                'R' -> node = nodes[node.rightNode]!!
            }

            numberOfSteps++
            if (i == instructions.count() - 1) {
                i = 0
            } else {
                i++
            }

            if (node.node == "ZZZ") {
                i = -1
            }
        }

        print(numberOfSteps)
    }

    override fun executePartTwo() {
        TODO("Not yet implemented")
    }
}

data class Node(val node: String, val leftNode: String, val rightNode: String)