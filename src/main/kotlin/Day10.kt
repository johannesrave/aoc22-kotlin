import kotlin.system.measureTimeMillis


fun main() {
    val durationA = measureTimeMillis { println("Solution for Day10a: ${Day10a("input/10.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day10b: ${Day10b("input/10.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

open class Day10a(inputFileName: String) : Day(inputFileName) {
    // i assume this is mostly about parsing into a great data structure, and will be trivial after.
    // since it is a board, i'll initially use a two-dimensional array and enums for the pipes.
    // afterward i'll traverse it starting from S, building a list. the additional pipes shouldn't be a problem.

    open fun solve(): Int {
        val board = Board.from(input)
        // println(board)
        val pipeLoop = board.findPipeLoop()
        // println(pipeLoop)
        return pipeLoop.size / 2
    }

    data class Board(val tiles: Array<Array<Pipe>>) {
        fun findPipeLoop(): List<Pipe> {
            var currentTile: Pipe? = findStart()
            val loop = mutableListOf<Pipe>()
            println(currentTile)
            while (currentTile != null) {
                loop.add(currentTile)
                currentTile = moveToConnectedNeighbour(currentTile) ?: break
                println("went to $currentTile")
            }
            return loop
        }

        private fun findStart(): Pipe =
            tiles.mapNotNull { row -> row.find { pipe -> pipe.directions.size == 4 } }.single()

        private fun moveToConnectedNeighbour(pipe: Pipe): Pipe? {
            val (x, y) = pipe
            val (dir, connectedNeighbour) = pipe.directions
                .filter { it != pipe.visitedFrom }
                .mapNotNull { dir ->
                    val neighbour = tiles.getOrNull(y + dir.y)?.getOrNull(x + dir.x)
                        ?: return@mapNotNull null
                    dir to neighbour
                }
                .filter { (dir, neighbour) -> neighbour.visitedFrom == null }
                .find { (dir, neighbour) -> dir.connectsTo in neighbour.directions } ?: return null
            connectedNeighbour.visitedFrom = dir.connectsTo
            return connectedNeighbour
        }

        companion object {
            fun from(input: String): Board {
                val tiles = input.lines().mapIndexed { y, line ->
                    line.mapIndexed { x, c -> Pipe(x, y, Direction.from(c)) }.toTypedArray()
                }.toTypedArray()
                return Board(tiles)
            }
        }

        override fun toString(): String = tiles.joinToString("\n") { row -> row.joinToString(" ") { "[$it]" } }
    }

    data class Pipe(val x: Int, val y: Int, val directions: Set<Direction>, var visitedFrom: Direction? = null)

    enum class Direction(val x: Int, val y: Int) {
        Top(0, -1) {
            override val connectsTo: Direction
                get() = Bottom
        },
        Right(1, 0) {
            override val connectsTo: Direction
                get() = Left
        },
        Bottom(0, 1) {
            override val connectsTo: Direction
                get() = Top
        },
        Left(-1, 0) {
            override val connectsTo: Direction
                get() = Right
        };

        abstract val connectsTo: Direction

        companion object {
            fun from(c: Char): Set<Direction> = when (c) {
                'S' -> setOf(Top, Right, Bottom, Left)
                '|' -> setOf(Top, Bottom)
                '-' -> setOf(Right, Left)
                'L' -> setOf(Top, Right)
                'J' -> setOf(Top, Left)
                '7' -> setOf(Bottom, Left)
                'F' -> setOf(Right, Bottom)
                '.' -> setOf()
                else -> throw IllegalArgumentException("'$c' is not a valid Pipe.")
            }
        }
    }
}

class Day10b(inputFileName: String) : Day10a(inputFileName) {
    override fun solve(): Int {
        return -1
    }
}