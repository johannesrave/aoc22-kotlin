import Day10a.Neighbour.*
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

        println(board.toString(listOf('@' to pipeLoop)))
        // println(pipeLoop)
        return pipeLoop.size / 2
    }

    data class Board(val tiles: Array<Array<Pipe>>, val input: String) {
        fun findPipeLoop(): List<Pipe> {
            var currentTile: Pipe = findStart()
            val loop = mutableListOf<Pipe>()
            while (true) {
                loop.add(currentTile)
                currentTile = moveToConnectedNeighbour(currentTile) ?: break
            }
            return loop
        }

        fun isEnclosedSideClockwise(pipeLoop: List<Pipe>): Boolean {
            val loopTiles = pipeLoop.toSet()
            pipeLoop.forEach { pipe ->
                val dir = pipe.exitedTowards!!.turnClockwise()
                var tileToCheck = neighbourOrNull(pipe.x, pipe.y, dir)
                while (true) {
                    if (tileToCheck == null) return false
                    if (tileToCheck in loopTiles) return@forEach
                    tileToCheck = neighbourOrNull(tileToCheck.x, tileToCheck.y, dir)
                }
            }
            return true
        }

        fun findEnclosedTiles(pipeLoop: List<Pipe>, enclosedClockwise: Boolean): Set<Pipe> {
            val totalEnclosedTiles = mutableSetOf<Pipe>()
            pipeLoop.forEach { pipe ->
                val floodKernels = when (pipe.enteredFrom!! to pipe.exitedTowards!!) {
                    Direction.Top to Direction.Right -> listOf(W, SW, S)
                    Direction.Top to Direction.Bottom -> listOf(W)
                    Direction.Top to Direction.Left -> listOf(NW)

                    Direction.Right to Direction.Top -> listOf(NE)
                    Direction.Right to Direction.Bottom -> listOf(N, NW, W)
                    Direction.Right to Direction.Left -> listOf(N)

                    Direction.Bottom to Direction.Top -> listOf(E)
                    Direction.Bottom to Direction.Right -> listOf(SE)
                    Direction.Bottom to Direction.Left -> listOf(N, NE, E)

                    Direction.Left to Direction.Top -> listOf(S, SE, E)
                    Direction.Left to Direction.Right -> listOf(S)
                    Direction.Left to Direction.Bottom -> listOf(SW)

                    else -> throw IllegalArgumentException()
                }.mapNotNull { offset -> tiles.getOrNull(pipe.y + offset.y)?.getOrNull(pipe.x + offset.x) }
                    .filter { it.isUnvisited() }
                    .also { if (it.isEmpty()) return@forEach }


//                val floodKernels2 =
//                    listOf(pipe.enteredFrom, pipe.exitedTowards).also { println(it) }
//                        .mapNotNull {
//                            if (enclosedClockwise) it?.turnClockwise() else it?.turnCounterClockwise()
//                        }.also { println(it) }
//                        .mapNotNull { dir -> neighbourOrNull(pipe.x, pipe.y, dir).also { println(dir) } }
//                        .filter { it.isUnvisited() }
//                        .toSet()
//                        .also { if (it.isEmpty()) return@forEach }

                println("going from $pipe, begin flooding at ${floodKernels.joinToString { (x, y) -> "[$x, $y]" }}")

                val queueToCheck = floodKernels.toMutableList()
                while (queueToCheck.isNotEmpty()) {
                    val checking = queueToCheck.removeFirst()
                    if (!checking.isUnvisited()) continue
                    val (x, y) = checking
                    println("visiting [$x,$y]")
                    checking.enteredFrom = Direction.Top
                    totalEnclosedTiles.add(checking)
                    queueToCheck.addAll(unvisitedNeighbours(x, y))
                }
            }
            return totalEnclosedTiles
            // .onEach { println(it) }
        }

        private fun findStart(): Pipe =
            tiles.mapNotNull { row -> row.find { pipe -> pipe.connectedDirections.size == 4 } }.single()

        private fun moveToConnectedNeighbour(pipe: Pipe): Pipe? {
            val (x, y) = pipe
            val (dir, connectedNeighbour) = pipe.connectedDirections
                .filter { it != pipe.enteredFrom }
                .mapNotNull { dir -> dir to (neighbourOrNull(x, y, dir) ?: return@mapNotNull null) }
                .find { (dir, neighbour) -> neighbour.isUnvisited() && neighbour.isConnectedTo(dir) }
                ?: return null
            pipe.exitedTowards = dir
            connectedNeighbour.enteredFrom = dir.connectsTo()
            return connectedNeighbour
        }

        private fun neighbourOrNull(x: Int, y: Int, dir: Direction): Pipe? = tiles
            .getOrNull(y + dir.y)
            ?.getOrNull(x + dir.x)

        private fun unvisitedNeighbours(x: Int, y: Int): List<Pipe> = Direction.entries
            .mapNotNull { dir -> neighbourOrNull(x, y, dir) }
            .filter { neighbour -> neighbour.isUnvisited() }

        companion object {
            fun from(input: String): Board {
                val tiles = input.lines().mapIndexed { y, line ->
                    line.mapIndexed { x, c -> Pipe(x, y, Direction.from(c)) }.toTypedArray()
                }.toTypedArray()
                return Board(tiles, input)
            }
        }

        override fun toString(): String = tiles
            .joinToString("\n") { row -> row.joinToString(" ") { "[$it]" } }

        fun toString(overlays: List<Pair<Char, List<Pipe>>>): String {
            val rep = input.lines().map { it.toCharArray() }.toTypedArray()
            overlays.forEach { (c, pipes) ->
                pipes.forEach { (x, y, dirs, from, exited) ->
                    if (c == '>') {
                        val char = if (dirs.size > 2) 'S' else
                            when (exited!!) {
                                Direction.Top -> '^'
                                Direction.Right -> '>'
                                Direction.Bottom -> 'v'
                                Direction.Left -> '<'
                            }
                        rep[y][x] = char
                    } else
                        rep[y][x] = c
                }
            }

            return rep.joinToString("\n") { row -> row.joinToString("") { "$it" } }
        }
    }

    enum class Neighbour(val x: Int, val y: Int) {
        N(0, -1),
        NE(1, -1),
        E(1, 0),
        SE(1, 1),
        S(0, 1),
        SW(-1, 1),
        W(-1, 0),
        NW(-1, -1)
    }

    data class Pipe(
        val x: Int,
        val y: Int,
        val connectedDirections: Set<Direction>,
        var enteredFrom: Direction? = null,
        var exitedTowards: Direction? = null
    ) {
        fun isUnvisited(): Boolean = enteredFrom == null
        fun isConnectedTo(dir: Direction): Boolean = dir.connectsTo() in connectedDirections
    }

    enum class Direction(val x: Int, val y: Int) {
        Top(0, -1),
        Right(1, 0),
        Bottom(0, 1),
        Left(-1, 0);

        fun connectsTo() = turnBy(2)
        fun turnClockwise() = turnBy(1)
        fun turnCounterClockwise() = turnBy(3)

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
        // println(board.toString(listOf('>' to pipeLoop.toList())))

        val enclosedClockwise = board.isEnclosedSideClockwise(pipeLoop)
        println("enclosedClockwise: $enclosedClockwise")
        val enclosedTiles = board.findEnclosedTiles(pipeLoop, enclosedClockwise)
        val others = board.tiles.flatten() subtract (enclosedTiles union pipeLoop)
        println(board.toString(listOf('>' to pipeLoop.toList(), '@' to enclosedTiles.toList(), 'X' to others.toList())))

        println(enclosedTiles.size)
        return enclosedTiles.size
    }
}