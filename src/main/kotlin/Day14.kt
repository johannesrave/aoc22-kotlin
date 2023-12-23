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

    protected fun parseRocks(input: String): Array<CharArray> = input.lines().map { it.toCharArray() }.toTypedArray()

    fun Array<CharArray>.tiltToRight(): Array<CharArray> = this.map { row -> gravityOrderSegments(row) }.toTypedArray()

    fun Array<CharArray>.calculateLoad(): Int = this
        .reversed()
        .mapIndexed { i, row -> (i + 1) * row.count { c -> c == 'O' } }
        .sum()

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
}

class Day14b(inputFileName: String) : Day14a(inputFileName) {
    override fun solve(): Int = parseRocks(input)
        .let cycles@{
            var board = it
            val boardStates = mutableMapOf(board.hash() to (mutableListOf(0) to board.calculateLoad()))

            val upperBound = 1_000_000_000
            // this actually terminates far earlier than on upperBound - for the "real" input it's around 115
            for (i in 1..upperBound) {
                board = board.cycle()
                val hash = board.hash()
                if (hash in boardStates) {
                    val (occurrencesOfSameState) = boardStates[hash]!!
                    occurrencesOfSameState.add(i)

                    val (firstOccurrence) = occurrencesOfSameState
                    val period = i - firstOccurrence
                    val nonPeriodicPart = firstOccurrence - 1
                    val offset = (upperBound - nonPeriodicPart) % period
                    val occurrenceWithOffset = nonPeriodicPart + offset
                    (boardStates.values.find { (occurrenceList, _) -> occurrenceWithOffset in occurrenceList }
                        ?.let { (_, load) -> return load }
                        ?: throw IllegalStateException())
                } else {
                    boardStates[hash] = mutableListOf(i) to board.calculateLoad()
                }
            }
            board
        }
        .calculateLoad()

    fun Array<CharArray>.cycle(): Array<CharArray> {
        var board = this
        for (i in 0..<4) {
            board = board.rotateCW().tiltToRight()
        }
        return board
    }

    fun Array<CharArray>.hash(): String = this.joinToString("|") { row -> row.joinToString("") }
}