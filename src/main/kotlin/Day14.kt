import kotlin.system.measureTimeMillis

fun main() {
    val durationA = measureTimeMillis { println("Solution for Day14a: ${Day14a("input/14.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day14b: ${Day14b("input/14.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

open class Day14a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Int = parseRocks(input)
        .rotateCW()
        .tiltToRight()
        .rotateCCW()
        .calculateLoad()


    private fun gravityOrderSegments(row: CharArray, exc: Char = '#'): CharArray {
        val segments = findContiguousSegments(row, exc)
        for ((start, end) in segments) {
            row.copyOfRange(start, end).sortedArray().copyInto(row, start)
        }
        return row
    }

    private fun findContiguousSegments(row: CharArray, exc: Char): MutableSet<Pair<Int, Int>> {
        val segments = mutableSetOf<Pair<Int, Int>>()
        var begin: Int? = null
        for ((i, c) in row.withIndex()) {
            if (c != exc && begin == null) begin = i
            if (c == exc && begin != null) {
                segments.add(begin to i)
                begin = null
            }
        }
        if (begin != null) segments.add(begin to row.size)
        return segments
    }

    fun Array<CharArray>.tiltToRight(): Array<CharArray> = this.map { row -> gravityOrderSegments(row) }.toTypedArray()

    fun Array<CharArray>.calculateLoad(): Int = this
        .reversed()
        .mapIndexed { i, row -> (i + 1) * row.count { c -> c == 'O' } }
        .sum()

    private fun parseRocks(input: String): Array<CharArray> = input.lines().map { it.toCharArray() }.toTypedArray()
}

class Day14b(inputFileName: String) : Day14a(inputFileName) {
    override fun solve(): Int {
        return -1
    }
}