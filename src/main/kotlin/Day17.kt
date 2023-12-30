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
            Path(mutableListOf(Step(Pos(0, 0), Down, 0, 0)), 0),
            Path(mutableListOf(Step(Pos(0, 0), Right, 0, 0)), 0),
        )

        val finishedPaths = mutableListOf<Path>()
        val lastPos = Pos(board.first().lastIndex, board.lastIndex)

        while (pathsToWalk.isNotEmpty()) {
            val currentPath = pathsToWalk.removeFirst()
            if (currentPath.steps.last().pos == lastPos) {
                finishedPaths.add(currentPath)
                continue
            }
            val newPaths = currentPath.walkAndMarkHeatLoss(board, minimalHeatLoss)
            pathsToWalk += newPaths
        }

//        finishedPaths
//            .minBy { it.totalHeatLoss }
//            .let { path ->
//                path.steps.withIndex()
//                    .joinToString("    \n") { item ->
//                        val (i, step) = item
//                        val (pos, dir, loss, heatLossUpToStep) = step
//                        val pathUpToHere = Path(path.steps.take(i + 1).toMutableList(), heatLossUpToStep)
//                        val lastThreeDirections = LastThreeDirections.from(pathUpToHere)
////                        val heatLossUpToStep = path.steps.take(i + 1).sumOf { it.heatLoss }
//
//                        "${dir.toString().padStart(5)}" +
//                                " -> [${pos.x},${pos.y}], " +
//                                "~${heatLossUpToStep.toString().padStart(3)}, " +
//                                "(${lastThreeDirections}) |"
//                    }
//
//                    .also {
//                        println("${path.totalHeatLoss}: $it")
//                        println(path.printPath(board))
//                        println()
//                    }
//            }
//        println()
//        minimalHeatLoss.onEach { println(it.joinToString { num -> num.toString().padStart(3) }) }

        return minimalHeatLoss.last().last().minOf { it.value }
    }

    data class Path(val steps: MutableList<Step>, val totalHeatLoss: Int) {
        fun walkAndMarkHeatLoss(
            board: Array<IntArray>,
            minimalHeatLossBoard: Array<Array<MutableMap<LastThreeDirections, Int>>>
        ): List<Path> {
            val currentStep = steps.last()
            val (pos, _, _) = currentStep
            val lastThreeDirs = LastThreeDirections.from(this)

            return getValidDirsToTurn()
                .filter { board.tileExists(pos.x + it.x, pos.y + it.y) }
                .filter { dir ->
                    val newHeatLoss = board[pos.y + dir.y][pos.x + dir.x] + totalHeatLoss
                    val currentMinimalHeatLossOnTile = minimalHeatLossBoard[pos.y + dir.y][pos.x + dir.x]
                        .getOrDefault(lastThreeDirs, Int.MAX_VALUE)

                    (newHeatLoss) < currentMinimalHeatLossOnTile
                }
                .map { dir ->
                    val newPos = Pos(pos.x + dir.x, pos.y + dir.y)
                    val heatLossOnTile = board[pos.y + dir.y][pos.x + dir.x]
                    val totalHeatLossUpToStep = heatLossOnTile + totalHeatLoss
                    minimalHeatLossBoard[newPos.y][newPos.x][lastThreeDirs] = totalHeatLossUpToStep

                    Step(newPos, dir, heatLossOnTile, totalHeatLossUpToStep)
                }.map {
                    this.copy(steps = (steps + it).toMutableList(), totalHeatLoss = it.totalHeatLossUpToStep)
                }
        }

        private fun getValidDirsToTurn(): Collection<Direction> {
            val (pos, currentDirection) = steps.last()
            val lastThreeDirs = LastThreeDirections.from(this)
            val mustTurn = lastThreeDirs.wentStraight()
//            println("$lastThreeDirs :  $mustTurn")

            val validDirections = when {
                mustTurn -> currentDirection.turnDirections()
                else -> currentDirection.allDirections()
            }
            return validDirections

        }

        fun printPath(board: Array<IntArray>): String {
            val printBuffer = board.map { it.joinToString("").toCharArray() }.toTypedArray()
            this.steps.forEach { (pos, enteredGoing, heatLoss) ->
                printBuffer[pos.y][pos.x] = when (enteredGoing) {
                    //@formatter:off
                    Up ->    '^'
                    Right -> '>'
                    Down ->  'v'
                    Left ->  '<'
                    //@formatter:on
                }
            }
            return printBuffer.withIndex().joinToString("\n") { (y, row) ->
                row.withIndex().joinToString("") { (x, char) -> "$char" }
            }
        }
    }

    data class Step(val pos: Pos, val enteredGoing: Direction, val heatLoss: Int, val totalHeatLossUpToStep: Int)

    data class LastThreeDirections(val a: Direction, val b: Direction?, val c: Direction?) {
        fun wentStraight(): Boolean = (a == b && b == c)

        companion object {
            fun from(path: Path): LastThreeDirections {
                return path.steps.takeLast(3).asReversed()
                    .let {
                        LastThreeDirections(
                            it[0].enteredGoing,
                            it.getOrNull(1)?.enteredGoing,
                            it.getOrNull(2)?.enteredGoing
                        )
                    }
                return path.steps
                    .reversed()
                    .take(3)
                    .map { it.enteredGoing }
                    .let { LastThreeDirections(it[0], it.getOrNull(1), it.getOrNull(2)) }
            }
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
            val allDirectionsFromUp = setOf(Left, Up, Right)
            val allDirectionsFromRight = setOf(Up, Right, Down)
            val allDirectionsFromDown = setOf(Right, Down, Left)
            val allDirectionsFromLeft = setOf(Down, Left, Up)

            val turnDirectionsFromUp = setOf(Left, Right)
            val turnDirectionsFromRight = setOf(Up, Down)
            val turnDirectionsFromDown = setOf(Right, Left)
            val turnDirectionsFromLeft = setOf(Down, Up)
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