import kotlin.system.measureTimeMillis

fun main() {
    val durationA = measureTimeMillis { println("Solution for Day13a: ${Day13a("input/13.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day13b: ${Day13b("input/13.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

open class Day13a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Int {
        val boards = parseBoards(input)
        return boards.sumOf { calculateScore(it) }.also { println(it) }
    }

    private fun calculateScore(board: Array<CharArray>): Int {
        val transposedBoard = board.transpose()
        println("board:")
        board.onEach { println(it) }
        println("transposedBoard:")
        transposedBoard.onEach { println(it) }
        val horizontalCandidates = findCandidatesForSymmetry(transposedBoard)
        println("horizontalCandidates: $horizontalCandidates")

        testForSymmetryAtIndices(horizontalCandidates, transposedBoard).also { println(it) }?.let { return it }

        val verticalCandidates = findCandidatesForSymmetry(board)
        println("verticalCandidates: $verticalCandidates")

        testForSymmetryAtIndices(verticalCandidates, board).also { println(it) }?.let { return it * 100 }
        throw IllegalArgumentException()
    }

    private fun findCandidatesForSymmetry(board: Array<CharArray>) = (0..<board.lastIndex)
        .filter { board[it].contentEquals(board[it + 1]) }

    private fun testForSymmetryAtIndices(indices: List<Int>, board: Array<CharArray>): Int? = indices
        .find { i ->
            val firstHalf = board.copyOfRange(0, i + 1).reversed()
            val secondHalf = board.copyOfRange(i + 1, board.lastIndex + 1)

            (firstHalf zip secondHalf)
                .onEach { pair ->
                    println("${pair.first.joinToString("")} | ${pair.second.joinToString("")}")
                }
                .all { (first, second) -> first.contentEquals(second) }.also { println(it) }
        }?.plus(1)

    internal fun parseBoards(input: String) = input
        .split("\n\n")
        .map {
            it.split("\n")
                .map { line -> line.toCharArray() }
                .toTypedArray()
        }
}

class Day13b(inputFileName: String) : Day13a(inputFileName) {
    override fun solve(): Int {
        val boards = parseBoards(input)
        return boards.sumOf { calculateScoreWithSmudge(it) }.also { println(it) }
    }

    private fun calculateScoreWithSmudge(board: Array<CharArray>): Int {
        val transposedBoard = board.transpose()

        val horizontalCandidates = findCandidatesForSymmetryWithSmudge(transposedBoard)
        findSymmetricIndexWithSmudge(horizontalCandidates, transposedBoard)?.let { return it }

        val verticalCandidates = findCandidatesForSymmetryWithSmudge(board)
        findSymmetricIndexWithSmudge(verticalCandidates, board)?.let { return it * 100 }

        throw IllegalArgumentException()
    }

    private fun findCandidatesForSymmetryWithSmudge(board: Array<CharArray>, allowedSmudges: Int = 1) =
        (0..<board.lastIndex).filter { y ->
            val (row, nextRow) = board[y] to board[y + 1]
            val foundSmudges = row.indices.count { row[it] != nextRow[it] }
            foundSmudges <= allowedSmudges
        }

    private fun findSymmetricIndexWithSmudge(
        indices: List<Int>,
        board: Array<CharArray>,
        allowedSmudges: Int = 1
    ): Int? =
        indices.find { i ->
            val firstHalf = board.copyOfRange(0, i + 1).reversed()
            val secondHalf = board.copyOfRange(i + 1, board.lastIndex + 1)
            val zippedHalves = firstHalf zip secondHalf

            val foundSmudges = zippedHalves
                .sumOf { (firstRow, secondRow) ->
                    println("${firstRow.joinToString("")} | ${secondRow.joinToString("")}")
                    firstRow.indices.count { x -> (firstRow[x] != secondRow[x]) }
                }

            foundSmudges == allowedSmudges
        }?.plus(1)
}

fun Array<CharArray>.transpose(): Array<CharArray> = this.first()
    .mapIndexed { x, _ -> this.map { it[x] }.toCharArray() }.toTypedArray()