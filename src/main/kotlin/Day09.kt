import kotlin.system.measureTimeMillis


fun main() {
    val durationA = measureTimeMillis { println("Solution for Day09a: ${Day09a("input/09.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day09b: ${Day09b("input/09.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

open class Day09a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Int {
        val histories = parseHistories()

        return histories.sumOf { history -> extrapolate(history) }
    }

    private fun extrapolate(history: List<Int>): Int {
        val differences = history.indices.drop(1).map { i -> history[i] - history[i - 1] }

        return if (differences.all { it == 0 })
            history.last() + differences.last()
        else history.last() + extrapolate(differences)
    }

    private fun parseHistories() = input.lines().map {
        """(-?\d+)""".toRegex()
            .findAll(it)
            .map { match -> match.value.toInt() }.toList()
    }
}

class Day09b(inputFileName: String) : Day09a(inputFileName) {
    override fun solve(): Int {
        return 0
    }
}