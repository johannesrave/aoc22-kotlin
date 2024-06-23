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
        val finishedPaths = walkAllPaths()

        return finishedPaths.minBy { it.heatloss }.also { it.printPath(input.toIntBoard()) }.heatloss
    }

    fun walkAllPaths(): MutableList<Step> {
        val board = input.toIntBoard()
        val heatlossBoard = getEmptyHeatLossBoard(board)
        val validDirections = ::validDirections

        val finalTile = Pos(board[0].lastIndex, board.lastIndex)
        val finishedPaths = mutableListOf<Step>()

        val initialStep = Step(Pos(0, 0), null, 0, 0, null)
        val stepsQueue = LinkedList<Step>()
        stepsQueue.add(initialStep)

        while (stepsQueue.isNotEmpty()) {
            val step = stepsQueue.removeFirst()
            if (step.pos == finalTile) {
                finishedPaths.add(step)
                continue
            }
            val (pos, _, _, heatloss, _, dirToStraightSteps) = step
            val recordedHeatloss = heatlossBoard[pos.y][pos.x][dirToStraightSteps]

            if (recordedHeatloss != null && recordedHeatloss < heatloss) continue

            step.getNextSteps(board, validDirections)
                .filter { (pos, _, _, heatloss, _, dirToStraightSteps) ->
                    val recordedHeatloss = heatlossBoard[pos.y][pos.x][dirToStraightSteps]
                        (recordedHeatloss == null || recordedHeatloss > heatloss)
                }
                .onEach { nextStep -> recordMinimalHeatlossOnBoard(nextStep, heatlossBoard) }
                .also { stepsQueue.addAll(it) }
        }
        return finishedPaths
    }

    private fun recordMinimalHeatlossOnBoard(step: Step, heatlossBoard: Board<MutableMap<Pair<Direction?, Int>, Int>>) {
        heatlossBoard[step.pos.y][step.pos.x][step.dirToStraightSteps] = step.heatloss
    }

    private fun getEmptyHeatLossBoard(board: Array<IntArray>): Board<MutableMap<Pair<Direction?, Int>, Int>> =
        Array(board.size) {
            Array(board.first().size) {
                mutableMapOf()
            }
        }

    open fun validDirections(currentDirection: Direction?, straightSteps: Int): Collection<Direction> = when {
        currentDirection == null -> Direction.entries
        straightSteps >= 3 -> currentDirection.turnDirections()
        else -> currentDirection.allDirections()
    }

    data class Step(
        val pos: Pos,
        val dir: Direction?,
        val straightSteps: Int,
        val heatloss: Int,
        val previousStep: Step?,
        val dirToStraightSteps: Pair<Direction?, Int> = dir to straightSteps
    ) {
        operator fun Pos.plus(dir: Direction): Pos = Pos(x + dir.x, y + dir.y)

        fun getNextSteps(
            board: Array<IntArray>,
            validDirections: (Direction?, Int) -> Collection<Direction>,
        ): Collection<Step> =
            validDirections(dir, straightSteps).filter { dir -> hasNeighbourInDirection(dir, board) }.map { dir ->
                    val newPos = pos + dir
                    val newWalkingStraightFor = if (dir == this.dir) straightSteps + 1 else 1
                    val additionalHeatloss = board[pos.y + dir.y][pos.x + dir.x]
                    val newHeatloss = heatloss + additionalHeatloss

                    Step(newPos, dir, newWalkingStraightFor, newHeatloss, this)
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
                currentStep = currentStep.previousStep
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
            //@formatter:off
            private val allDirectionsFromUp =     setOf(Right, Left, Up)
            private val allDirectionsFromRight =  setOf(Right, Down, Up)
            private val allDirectionsFromDown =   setOf(Right, Down, Left)
            private val allDirectionsFromLeft =   setOf(Down, Left, Up)

            private val turnDirectionsFromUp =    setOf(Right, Left)
            private val turnDirectionsFromRight = setOf(Down, Up)
            private val turnDirectionsFromDown =  setOf(Right, Left)
            private val turnDirectionsFromLeft =  setOf(Down, Up)
            //@formatter:on
        }
    }
}

class Day17b(inputFileName: String) : Day17a(inputFileName) {
    override fun solve(): Int {
        val finishedPaths = walkAllPaths()
        return finishedPaths
            .filter { it.straightSteps >= 4 }
            .minBy { it.heatloss }
            .also { it.printPath(input.toIntBoard()) }
            .heatloss
    }

    override fun validDirections(currentDirection: Direction?, straightSteps: Int): Collection<Direction> = when {
        currentDirection == null -> Direction.entries
        straightSteps >= 10 -> currentDirection.turnDirections()
        straightSteps < 4 -> listOf(currentDirection)
        else -> currentDirection.allDirections()
    }
}