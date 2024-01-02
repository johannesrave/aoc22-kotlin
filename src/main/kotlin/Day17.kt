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

        val lastPos = Pos(board[0].lastIndex, board.lastIndex)
        val finishedPaths = mutableListOf<Step>()
        val steps = LinkedList<Step>()
        steps.add(Step(Pos(0, 0), null, 0, 0, null))
        while (steps.isNotEmpty()) {
            val step = steps.removeFirst()
            if (step.pos == lastPos) finishedPaths.add(step)
            val key = step.comingFrom to step.walkingStraightFor
            if (minimalHeatloss[step.pos.y][step.pos.x][key] != null
                && minimalHeatloss[step.pos.y][step.pos.x][key]!! < step.heatloss
            ) continue

            step.nextSteps(board)
                .filter { (pos, comingFrom, walkingStraightFor, heatloss) ->
                    val (x, y) = pos
                    val _key = comingFrom to walkingStraightFor
                    minimalHeatloss[y][x][_key] == null || minimalHeatloss[y][x][_key]!! > heatloss
                }
                .onEach { (pos, comingFrom, walkingStraightFor, heatloss) ->
                    val (x, y) = pos
                    minimalHeatloss[y][x][comingFrom to walkingStraightFor] = heatloss
                }
                .let { nextSteps -> steps.addAll(nextSteps) }
        }

        finishedPaths.minBy { it.heatloss }.printPath(board)

        return minimalHeatloss.last().last().minOf { it.value }
    }

    data class Step(
        val pos: Pos,
        val comingFrom: Direction?,
        val walkingStraightFor: Int,
        val heatloss: Int,
        val previous: Step?
    ) {
        fun nextSteps(board: Array<IntArray>): Collection<Step> = getNextDirs(walkingStraightFor)
            .filter { dir -> hasNeighbourInDirection(pos, dir, board) }
            .map { dir ->
                val newPos = Pos(pos.x + dir.x, pos.y + dir.y)
                val newWalkingStraightFor = if (dir == comingFrom) walkingStraightFor + 1 else 1
                val additionalHeatloss = board[pos.y + dir.y][pos.x + dir.x]
                val newHeatloss = heatloss + additionalHeatloss

                Step(newPos, dir, newWalkingStraightFor, newHeatloss, this)
            }

        private fun getNextDirs(walkingStraightFor: Int): Collection<Direction> {
            return when {
                comingFrom == null -> Direction.entries
                walkingStraightFor >= 3 -> comingFrom.turnDirections()
                else -> comingFrom.allDirections()
            }
        }

        private fun hasNeighbourInDirection(pos: Pos, dir: Direction, board: Array<IntArray>): Boolean {
            val (x, y) = (pos.x + dir.x) to (pos.y + dir.y)
            return board.getOrNull(y)?.getOrNull(x) != null
        }

        fun printPath(board: Array<IntArray>): String {
            var currentStep: Step? = this
            val path = mutableListOf<Step>()
            while (currentStep != null) {
                path.add(currentStep)
                currentStep = currentStep.previous
            }

            val printBuffer = board.map { it.joinToString("").toCharArray() }.toTypedArray()

            path.forEach { (pos, enteredGoing, heatLoss) ->
                printBuffer[pos.y][pos.x] = when (enteredGoing) {
                    //@formatter:off
                    Direction.Up ->    '^'
                    Direction.Right -> '>'
                    Direction.Down ->  'v'
                    Direction.Left ->  '<'
                    null ->            'X'
                    //@formatter:on
                }
            }
            val string = printBuffer.withIndex().joinToString("\n") { (y, row) ->
                row.withIndex().joinToString("") { (x, char) -> "$char" }
            }
            println(string)
            return string
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
                mutableMapOf<Pair<Direction?, Int>, Int>()
            }
        }

    private fun printMinimalHeatlossBoard(board: Array<Array<MutableMap<Pair<Direction?, Int>, Int>>>) {
        board.joinToString("\n") { row ->
            row.joinToString(" | ") { map ->
                val (walkingStraightFor, heatloss) = if (map.isNotEmpty())
                    map.minBy { it.value }.let { it.key to it.value }
                else 0 to 0
                "${heatloss.toString().padStart(3)} "
            }
        }.also { println(it) }

    }
}

class Day17b(inputFileName: String) : Day17a(inputFileName) {
    override fun solve(): Int {
        return -1
    }
}