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
        val board = input.lines().map { it.map { c -> c.toString().toInt() }.toIntArray() }.toTypedArray()
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
        minimalHeatLoss.onEach { println(it.joinToString { num -> num.toString().padStart(2) }) }
        println()

        return minimalHeatLoss.last().last()
    }

    data class Path(val steps: MutableList<Step>, val totalHeatLoss: Int) {
        fun walkAndMarkHeatLoss(board: Array<IntArray>, minimalHeatLoss: Array<IntArray>): List<Path> {
            val currentStep = steps.last()
            val (pos, _, _) = currentStep
            return getValidDirs(currentStep, board)
                .filter { dir ->
                    val newHeatLoss = board[pos.y + dir.y][pos.x + dir.x]
                    (totalHeatLoss + newHeatLoss) < minimalHeatLoss[pos.y + dir.y][pos.x + dir.x]
                }.map { dir ->
                    val newPos = Pos(pos.x + dir.x, pos.y + dir.y)
                    val newHeatLoss = board[pos.y + dir.y][pos.x + dir.x]
                    minimalHeatLoss[newPos.y][newPos.x] = totalHeatLoss + newHeatLoss
                    Step(newPos, dir, newHeatLoss)
                }.map {
                    this.copy(steps = (steps + it).toMutableList(), totalHeatLoss = totalHeatLoss + it.heatLoss)
                }
        }

        private fun getValidDirs(step: Step, board: Array<IntArray>): List<Direction> {
            val (pos, currentDirection) = step
            val mustTurn = steps.takeLast(3).all { it.enteredGoing != currentDirection }
            return when {
                mustTurn -> currentDirection.turnDirections()
                else -> currentDirection.allDirections()
            }.filter { board.tileExists(pos.x + it.x, pos.y + it.y) }
        }
    }

    data class Step(val pos: Pos, val enteredGoing: Direction, val heatLoss: Int)

    enum class Direction(val x: Int, val y: Int) {
        Up(0, -1), Right(1, 0), Down(0, 1), Left(-1, 0);

        fun allDirections() = when (this) {
            Up -> setOf(Left, Up, Right)
            Right -> setOf(Up, Right, Down)
            Down -> setOf(Right, Down, Left)
            Left -> setOf(Down, Left, Up)
        }

        fun turnDirections() = allDirections() - this
    }

    private fun getMinimalHeatLossBoard(board: Array<IntArray>) =
        Array(board.size) { IntArray(board.first().size) { 99 } }
}

class Day17b(inputFileName: String) : Day17a(inputFileName) {
    override fun solve(): Int {
        return -1
    }
}