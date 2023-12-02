import kotlin.system.measureTimeMillis


fun main() {
    val day01a = Day01a("input/01.txt")

    var solutionA: Int
    val durationA = measureTimeMillis { solutionA = day01a.solve() }
    println("solution took $durationA milliseconds")
    println(solutionA)

        val day01b = Day01b("input/01.txt")

    var solutionB: Int
    val durationB = measureTimeMillis { solutionB = day01b.solve() }
    println("solution took $durationB milliseconds")
    println(solutionB)
}

class Day01a(inputFileName: String) : Day(inputFileName) {
    fun solve(): Int = input.split("\n")
        .sumOf {
            val tens = it.find { c -> c.isDigit() }!!.digitToInt() * 10
            val ones = it.reversed().find { c -> c.isDigit() }!!.digitToInt()
            tens + ones
        }
}

class Day01b(inputFileName: String) : Day(inputFileName) {
    private val digitLiterals = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
        "1" to 1,
        "2" to 2,
        "3" to 3,
        "4" to 4,
        "5" to 5,
        "6" to 6,
        "7" to 7,
        "8" to 8,
        "9" to 9,
    )

    fun solve(): Int = input.split("\n")
        .sumOf {
            val tens = digitLiterals[it.findAnyOf(digitLiterals.keys)!!.second]!! * 10
            val ones = digitLiterals[it.findLastAnyOf(digitLiterals.keys)!!.second]!!
            tens + ones
        }
}