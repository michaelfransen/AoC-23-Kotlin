import days.*

fun main(args: Array<String>) {
    println("What day do you want to run?")
    val day = readln().toInt()
    println("What part do you want to run?")
    val part = readln().toInt()

    when {
        day == 1 && part == 1 -> Day1().executePartOne()
        day == 1 && part == 2 -> Day1().executePartTwo()
        day == 2 && part == 1 -> Day2().executePartOne()
        day == 2 && part == 2 -> Day2().executePartTwo()
        day == 3 && part == 1 -> Day3().executePartOne()
        day == 3 && part == 2 -> Day3().executePartOne()
        day == 4 && part == 1 -> Day4().executePartOne()
        day == 4 && part == 2 -> Day4().executePartTwo()
        day == 5 && part == 1 -> Day5().executePartOne()
        day == 5 && part == 2 -> Day5().executePartTwo()
        day == 6 && part == 1 -> Day6().executePartOne()
        day == 6 && part == 2 -> Day6().executePartTwo()
        day == 7 && part == 1 -> Day7().executePartOne()
        day == 7 && part == 2 -> Day7().executePartTwo()
        day == 8 && part == 1 -> Day8().executePartOne()
        day == 8 && part == 2 -> Day8().executePartTwo()
    }
}