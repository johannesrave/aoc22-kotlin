import Day17a.Direction.*
import kotlin.system.measureTimeMillis

fun main() {
    val durationA = measureTimeMillis { println("Solution for Day17a: ${Day17a("input/17.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day17b: ${Day17b("input/17.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

typealias Pos = Point2D

open class Day17a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Int {
        val board = input.lines().map { it.toCharArray() }.toTypedArray()
        val minimalHeatLoss = getMinimalHeatLossBoard(board)

        val pathsToWalk = mutableListOf(
            Path(mutableListOf(Step(Pos(0, 0), Down, 0)), 0),
            Path(mutableListOf(Step(Pos(0, 0), Right, 0)), 0),
        )

        while (pathsToWalk.isNotEmpty()) {
            val currentPath = pathsToWalk.removeFirst()
            val newPaths = currentPath.walkAndMarkHeatLoss(board, minimalHeatLoss)
            pathsToWalk += newPaths
        }

        return minimalHeatLoss.last().last()
    }

    data class Path(val steps: MutableList<Step>, var totalHeatLoss: Int) {
        fun walkAndMarkHeatLoss(board: Array<CharArray>, minimalHeatLoss: Array<IntArray>): List<Path> {
            TODO("Not yet implemented")
        }
    }

    data class Step(val pos: Pos, val enteredGoing: Direction, val heatLoss: Int)

    enum class Direction(val x: Int, val y: Int) {
        Up(0, -1), Right(1, 0), Down(0, 1), Left(-1, 0);
    }

    private fun getMinimalHeatLossBoard(board: Array<CharArray>) =
        Array(board.size) { IntArray(board.first().size) { Int.MAX_VALUE } }
}

class Day17b(inputFileName: String) : Day17a(inputFileName) {
    override fun solve(): Int {
        return -1
    }
}