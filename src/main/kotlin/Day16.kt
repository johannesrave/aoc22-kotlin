import Day16a.Direction.*
import kotlin.system.measureTimeMillis

fun main() {
    val durationA = measureTimeMillis { println("Solution for Day16a: ${Day16a("input/16.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day16b: ${Day16b("input/16.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

open class Day16a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Int {
        val board = input.lines().map { it.toCharArray() }.toTypedArray()
        return calculateEnergizedTilesStartingAt(board, Beam(-1, 0, Right))
    }

    protected fun calculateEnergizedTilesStartingAt(board: Array<CharArray>, beam: Beam): Int {
        val energizedBoard = Array(board.size) { Array<MutableSet<Direction>>(board.first().size) { mutableSetOf() } }
        val beams = mutableListOf(beam)

        while (beams.isNotEmpty()) {
            val currentBeam = beams.removeFirst()
            val newBeams = currentBeam.moveAndSplit(board, energizedBoard)
            beams.addAll(newBeams)
        }
        return energizedBoard.sumOf { row -> row.count { energized -> energized.isNotEmpty() } }
    }

    data class Beam(val pos: Point2D, val dir: Direction) {
        constructor(x: Int, y: Int, dir: Direction) : this(Point2D(x, y), dir)

        fun moveAndSplit(
            board: Array<CharArray>,
            energizedBoard: Array<Array<MutableSet<Direction>>>
        ): Collection<Beam> {
            val newBeams = mutableListOf<Beam>()
            var pos = pos
            var currentTile: Char
            while (true) {
                pos = Point2D(pos.x + dir.x, pos.y + dir.y)
                val (_x, _y) = pos
                currentTile = board.getOrNull(_y)?.getOrNull(_x) ?: break
                if (dir in energizedBoard[_y][_x]) break
                energizedBoard[_y][_x] += dir
                when (currentTile) {
                    '.' -> continue

                    '-' -> when (dir) {
                        Up, Down -> newBeams.addAll(listOf(Beam(_x, _y, Right), Beam(_x, _y, Left)))
                        else -> continue
                    }

                    '|' -> when (dir) {
                        Right, Left -> newBeams.addAll(listOf(Beam(_x, _y, Up), Beam(_x, _y, Down)))
                        else -> continue
                    }

                    '/' -> when (dir) {
                        Up -> newBeams.add(Beam(_x, _y, Right))
                        Right -> newBeams.add(Beam(_x, _y, Up))
                        Down -> newBeams.add(Beam(_x, _y, Left))
                        Left -> newBeams.add(Beam(_x, _y, Down))
                    }

                    '\\' -> when (dir) {
                        Up -> newBeams.add(Beam(_x, _y, Left))
                        Left -> newBeams.add(Beam(_x, _y, Up))
                        Down -> newBeams.add(Beam(_x, _y, Right))
                        Right -> newBeams.add(Beam(_x, _y, Down))
                    }
                }
                break

            }
            return newBeams
        }
    }

    enum class Direction(val x: Int, val y: Int) {
        Up(0, -1), Right(1, 0), Down(0, 1), Left(-1, 0);
    }
}

class Day16b(inputFileName: String) : Day16a(inputFileName) {
    override fun solve(): Int {
        val board = input.lines().map { it.toCharArray() }.toTypedArray()
        val energizations = mutableListOf<Int>()
        for (x in board.first().indices) {
            energizations += calculateEnergizedTilesStartingAt(board, Beam(x, -1, Down))
            energizations += calculateEnergizedTilesStartingAt(board, Beam(x, board.size, Up))
        }
        for (y in board.indices) {
            energizations += calculateEnergizedTilesStartingAt(board, Beam(-1, y, Right))
            energizations += calculateEnergizedTilesStartingAt(board, Beam(board.first().size, y, Left))
        }
        return energizations.max()
    }
}