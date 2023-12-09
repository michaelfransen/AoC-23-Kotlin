package days

import java.io.File

class Day8: Day {
    private var input = File("src/main/resources/Data/Day_8.txt").readLines()
    private val nodeRegex = Regex("([A-Z]{3}) = \\(([A-Z]{3}), ([A-Z]{3})\\)")
    private val instructions = input[0].toCharArray()
    private val nodes = input.subList(2, input.count())
        .map {
            val groups = nodeRegex.find(it)!!.groups
            Node(groups[1]!!.value, groups[2]!!.value, groups[3]!!.value)
        }.associateBy { it.node }

    override fun executePartOne() {
        print(numberOfStepsFor(nodes["AAA"]!!) {
            it.node == "ZZZ"
        })
    }

    override fun executePartTwo() {
        println(
            nodes.filter { it.value.node.last() == 'A' }
                .map {
                    numberOfStepsFor(it.value) { node ->
                        node.node.last() == 'Z'
                    }
                }
                .map { it.toLong() }
                .reduce { acc, i ->  acc.lcm(i) }
        )
    }

    private fun numberOfStepsFor(node: Node, isFinal: (node: Node) -> Boolean): Int {
        var i = 0
        var numberOfSteps = 0
        var workingNode = node

        while (i >= 0) {
            when (instructions[i]) {
                'L' -> workingNode = nodes[workingNode.leftNode]!!
                'R' -> workingNode = nodes[workingNode.rightNode]!!
            }

            numberOfSteps++
            if (i == instructions.count() - 1) {
                i = 0
            } else {
                i++
            }

            if (isFinal(workingNode)) {
                i = -1
            }
        }

        return numberOfSteps
    }
}

data class Node(val node: String, val leftNode: String, val rightNode: String)

fun Long.lcm(other: Long): Long {
    val maxLcm = this * other
    val larger = if (this > other) this else other
    var lcm = larger

    while (lcm <= maxLcm) {
        if (lcm % this == 0L && lcm % other == 0L) {
            return lcm
        }

        lcm += larger
    }

    return maxLcm
}