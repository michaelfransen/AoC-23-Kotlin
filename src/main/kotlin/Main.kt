import Days.Day1
import Days.Day2

fun main(args: Array<String>) {
    println("What day do you want to run?")
    val day = readln().toInt()
    println("What part do you want to run?")
    val part = readln().toInt()

    when {
        day == 1 && part == 1 -> Day1().executePartOne()
        day ==1 && part == 2 -> Day1().executePartTwo()
        day == 2 && part == 1 -> Day2().executePartOne()
        day == 2 && part == 2 -> Day2().executePartTwo()
    }
}