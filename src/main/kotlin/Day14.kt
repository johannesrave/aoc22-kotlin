import kotlin.system.measureTimeMillis

fun main() {
    val durationA = measureTimeMillis { println("Solution for Day14a: ${Day14a("input/14.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day14b: ${Day14b("input/14.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

open class Day14a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Int {
        val rocks = parseRocks(input)
            .also { it.forEach { println(it) } }
            .also { println() }
            .transpose().map { it.reversed().toCharArray() }
        println()

        return rocks.map { row ->
            gravityOrder(row)
        }.toTypedArray()
            .transpose()
            .also { it.forEach { println(it) } }
            .also { println() }
            .reversed()
            .also { it.forEach { println(it) } }
            .also { println() }
            .mapIndexed { i, row -> (i + 1) * row.count { c -> c == 'O' } }.sum()
    }

    private fun gravityOrder(row: CharArray, exc: Char = '#'): CharArray {
        val ranges = findContinuousRanges(row, exc)
        for ((start, end) in ranges) {
            row.copyOfRange(start, end).sortedArray().copyInto(row, start)
        }
        return row
    }

    private fun findContinuousRanges(row: CharArray, exc: Char): MutableSet<Pair<Int, Int>> {
        val ranges = mutableSetOf<Pair<Int, Int>>()
        var begin: Int? = null
        row.forEachIndexed { i, c ->
            if (c != exc && begin == null) begin = i
            if (c == exc && begin != null) {
                ranges.add(begin!! to i)
                begin = null
            }
        }
        if (begin != null) ranges.add(begin!! to row.size)
        return ranges
    }

    class GravityOrdering : Comparator<Char> {
        override fun compare(a: Char?, b: Char?): Int {
            val roundRock = 'O'
            val squareRock = '#'
            val sand = '.'
            val result = when {
                a == roundRock && b == sand -> 1
                a == sand && b == roundRock -> -1
                else -> 0
            }
                .also { println("$b $a : $it") }
            return result
        }

    }

    private fun parseRocks(input: String): Array<CharArray> = input.lines().map { it.toCharArray() }.toTypedArray()
}

class Day14b(inputFileName: String) : Day14a(inputFileName) {
    override fun solve(): Int {
        return -1
    }
}