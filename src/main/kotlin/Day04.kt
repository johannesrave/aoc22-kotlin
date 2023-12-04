import kotlin.system.measureTimeMillis


fun main() {
    val durationA = measureTimeMillis { println("Solution for Day04a: ${Day04a("input/04.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day04b: ${Day04b("input/04.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

class Day04a(inputFileName: String) : Day(inputFileName) {
    fun solve(): Int {
        val numbers = parseNumbers(input)
    }

    private fun parseSymbols(input: String): Map<Int, Set<Int>> {
        val symbols = emptyMap<Int, MutableSet<Int>>().toMutableMap()
        input.split("\n").forEachIndexed { y, row ->
            symbolRegex.findAll(row).forEach { result ->
                val x = result.range.first
                symbols[y]?.add(x) ?: (symbols.put(y, mutableSetOf(x)))
            }
        }
        return symbols
    }
}

class Day04b(inputFileName: String) : Day(inputFileName) {
    fun solve(): Int {
        return 0
    }
}