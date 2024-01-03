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
            val key = step.walking to step.walkingStraightFor
            if (minimalHeatloss[step.pos.y][step.pos.x][key] != null
                && minimalHeatloss[step.pos.y][step.pos.x][key]!! < step.heatloss
            ) continue

            step.nextSteps(board, ::validDirections)
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

        println(finishedPaths)
        finishedPaths.minBy { it.heatloss }.printPath(board)

        return minimalHeatloss.last().last().minOf { it.value }
    }

    open fun validDirections(comingFrom: Direction?, walkingStraightFor: Int): Collection<Direction> = when {
        comingFrom == null -> Direction.entries
        walkingStraightFor >= 3 -> comingFrom.turnDirections()
        else -> comingFrom.allDirections()
    }

    data class Step(
        val pos: Pos,
        val walking: Direction?,
        val walkingStraightFor: Int,
        val heatloss: Int,
        val previous: Step?
    ) {
        operator fun Pos.plus(dir: Direction): Pos = Pos(x + dir.x, y + dir.y)

        fun nextSteps(
            board: Array<IntArray>,
            validDirections: (Direction?, Int) -> Collection<Direction>,
//            preFilter: (Pos, Direction, Int, Array<IntArray>) -> Boolean,
//            postFilter: (Pos, Direction, Int, Array<IntArray>) -> Boolean,
        ): Collection<Step> {

            return validDirections(walking, walkingStraightFor)
                .filter { dir -> hasNeighbourInDirection(dir, board) }
                .map { dir ->
                    val newPos = pos + dir
                    val newWalkingStraightFor = if (dir == walking) walkingStraightFor + 1 else 1
                    val additionalHeatloss = board[pos.y + dir.y][pos.x + dir.x]
                    val newHeatloss = heatloss + additionalHeatloss

                    Step(newPos, dir, newWalkingStraightFor, newHeatloss, this)
                }
        }

        private fun hasNeighbourInDirection(dir: Direction, board: Array<IntArray>): Boolean {
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

            path.forEach { (pos, enteredGoing) ->
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

    fun getMinimalHeatLossBoard(board: Array<IntArray>): Array<Array<MutableMap<Pair<Direction?, Int>, Int>>> =
        Array(board.size) {
            Array(board.first().size) {
                mutableMapOf<Pair<Direction?, Int>, Int>()
            }
        }
}

class Day17b(inputFileName: String) : Day17a(inputFileName) {
    override fun validDirections(comingFrom: Direction?, walkingStraightFor: Int): Collection<Direction> = when {
        comingFrom == null -> Direction.entries
        walkingStraightFor >= 10 -> comingFrom.turnDirections()
        walkingStraightFor < 4 -> listOf(comingFrom)
        else -> comingFrom.allDirections()
    }

    override fun solve(): Int {
        val board = input.lines().map { it.map { c -> c.toString().toInt() }.toIntArray() }.toTypedArray()
        val minimalHeatloss = getMinimalHeatLossBoard(board)

        val lastPos = Pos(board[0].lastIndex, board.lastIndex)
        val finishedPaths = mutableListOf<Step>()
        val steps = LinkedList<Step>()
        steps.add(Step(Pos(0, 0), null, 0, 0, null))
        while (steps.isNotEmpty()) {
            val step = steps.removeFirst()
            if (step.pos == lastPos) finishedPaths.add(step)
            val key = step.walking to step.walkingStraightFor
            if (minimalHeatloss[step.pos.y][step.pos.x][key] != null
                && minimalHeatloss[step.pos.y][step.pos.x][key]!! < step.heatloss
            ) continue

            step.nextSteps(board, ::validDirections)
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

        finishedPaths
            .filter { it.walkingStraightFor >= 4 }
            .minBy { it.heatloss }.printPath(board)

        return finishedPaths
            .filter { it.walkingStraightFor >= 4 }
            .minOf { it.heatloss }
    }
}