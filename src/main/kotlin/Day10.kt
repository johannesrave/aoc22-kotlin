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
            tiles.mapNotNull { row -> row.find { pipe -> pipe.connectedDirections.size == 4 } }.single()

        private fun moveToConnectedNeighbour(pipe: Pipe): Pipe? {
            val (x, y) = pipe
            val (dir, connectedNeighbour) = pipe.connectedDirections
                .filter { it != pipe.visitedFrom }
                .mapNotNull { dir -> neighbourOrNull(x, y, dir) }
                .find { (dir, neighbour) -> neighbour.isUnvisited() && neighbour.isReachableFrom(dir) }
                ?: return null
            connectedNeighbour.visitedFrom = dir.connectsTo()
            return connectedNeighbour
        }

        private fun neighbourOrNull(x: Int, y: Int, dir: Direction): Pair<Direction, Pipe>? {
            val neighbour = tiles.getOrNull(y + dir.y)?.getOrNull(x + dir.x)
                ?: return null
            return dir to neighbour
        }

        companion object {
            fun from(input: String): Board {
                val tiles = input.lines().mapIndexed { y, line ->
                    line.mapIndexed { x, c -> Pipe(x, y, Direction.from(c)) }.toTypedArray()
                }.toTypedArray()
                return Board(tiles)
            }
        }

        fun findEnclosedTiles(pipeLoop: List<Pipe>): Set<Pipe> {
            val borders = pipeLoop.toSet()
            pipeLoop.flatMap { tile ->
                // tile.visitedFrom.turnClockwise()
                listOf(1)
            }
            return emptySet()
        }

        override fun toString(): String = tiles.joinToString("\n") { row -> row.joinToString(" ") { "[$it]" } }
    }

    data class Pipe(
        val x: Int,
        val y: Int,
        val connectedDirections: Set<Direction>,
        var visitedFrom: Direction? = null
    ) {
        fun isUnvisited(): Boolean = visitedFrom == null
        fun isReachableFrom(dir: Direction): Boolean = dir.connectsTo() in connectedDirections
    }

    enum class Direction(val x: Int, val y: Int) {
        Top(0, -1),
        Right(1, 0),
        Bottom(0, 1),
        Left(-1, 0);

        fun connectsTo() = turnBy(2)
        fun turnClockwise() = turnBy(1)
        fun turnCounterClockwise() = turnBy(-1)

        private fun turnBy(n: Int) = entries[(this.ordinal + n) % entries.size]

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
        val board = Board.from(input)

        // println(board)
        val pipeLoop = board.findPipeLoop()

        // println(pipeLoop)
        val enclosedTiles = board.findEnclosedTiles(pipeLoop)
        return enclosedTiles.size
    }
}