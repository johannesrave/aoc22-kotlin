import java.util.LinkedList
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
        val minimalHeatloss = getMinimalHeatLossBoard(board)

        val steps = LinkedList<Step>()
        steps.add(Step(Pos(0, 0), LastThreeDirections(null, null, null), 0))
        while (steps.isNotEmpty()) {
            val step = steps.removeFirst()

            if (minimalHeatloss[step.pos.y][step.pos.x][step.lastDirs] != null
                && minimalHeatloss[step.pos.y][step.pos.x][step.lastDirs]!! < step.heatloss
            ) continue

            step.nextSteps(board)
                .filter { (pos, lastDirs, heatloss) ->
                    val (x, y) = pos
                    minimalHeatloss[y][x][lastDirs] == null || minimalHeatloss[y][x][lastDirs]!! > heatloss
                }
                .onEach { (pos, lastDirs, heatloss) ->
                    val (x, y) = pos
                    minimalHeatloss[y][x][lastDirs] = heatloss
                }
                .let { nextSteps -> steps.addAll(nextSteps) }
        }
        return minimalHeatloss.last().last().minOf { it.value }
    }

    data class Step(val pos: Pos, val lastDirs: LastThreeDirections, val heatloss: Int) {
        fun nextSteps(board: Array<IntArray>): Collection<Step> = lastDirs.getNextDirs()
            .filter { dir -> hasNeighbourInDirection(pos, dir, board) }
            .map { dir ->
                val newPos = Pos(pos.x + dir.x, pos.y + dir.y)
                val newDirs = LastThreeDirections.from(lastDirs, dir)
                val additionalHeatloss = board[pos.y + dir.y][pos.x + dir.x]
                val newHeatloss = heatloss + additionalHeatloss

                Step(newPos, newDirs, newHeatloss)
            }

        private fun hasNeighbourInDirection(pos: Pos, dir: Direction, board: Array<IntArray>): Boolean {
            val (x, y) = (pos.x + dir.x) to (pos.y + dir.y)
            return board.getOrNull(y)?.getOrNull(x) != null
        }
    }


    data class LastThreeDirections(val a: Direction?, val b: Direction?, val c: Direction?) {
        fun getNextDirs(): Collection<Direction> {
            return if (a == null) Direction.entries
            else if (wentStraight()) a.turnDirections()
            else a.allDirections()
        }

        private fun wentStraight(): Boolean = (a == b && b == c)

        companion object {
            fun from(dirs: LastThreeDirections, dir: Direction): LastThreeDirections =
                LastThreeDirections(dir, dirs.a, dirs.b)
        }
    }

    enum class Direction(val x: Int, val y: Int) {
        Up(0, -1), Right(1, 0), Down(0, 1), Left(-1, 0);

        fun allDirections() = when (this) {
            Up -> Direction.allDirectionsFromUp
            Right -> Direction.allDirectionsFromRight
            Down -> Direction.allDirectionsFromDown
            Left -> Direction.allDirectionsFromLeft
        }

        fun turnDirections() = when (this) {
            Up -> Direction.turnDirectionsFromUp
            Right -> Direction.turnDirectionsFromRight
            Down -> Direction.turnDirectionsFromDown
            Left -> Direction.turnDirectionsFromLeft
        }

        companion object {
            private val allDirectionsFromUp = setOf(Right, Left, Up)
            private val allDirectionsFromRight = setOf(Right, Down, Up)
            private val allDirectionsFromDown = setOf(Right, Down, Left)
            private val allDirectionsFromLeft = setOf(Down, Left, Up)

            private val turnDirectionsFromUp = setOf(Right, Left)
            private val turnDirectionsFromRight = setOf(Down, Up)
            private val turnDirectionsFromDown = setOf(Right, Left)
            private val turnDirectionsFromLeft = setOf(Down, Up)
        }
    }

    private fun getMinimalHeatLossBoard(board: Array<IntArray>) =
        Array(board.size) {
            Array(board.first().size) {
                mutableMapOf<LastThreeDirections, Int>()
            }
        }
}

class Day17b(inputFileName: String) : Day17a(inputFileName) {
    override fun solve(): Int {
        return -1
    }
}