import kotlin.system.measureTimeMillis


fun main() {
    val durationA = measureTimeMillis { println("Solution for Day03a: ${Day03a("input/03.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day03b: ${Day03b("input/03.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

class Day03a(inputFileName: String) : Day(inputFileName) {

    fun solve(): Int {
        val symbolPositions = parseSymbols(input)
        val partNumbers = parsePartNumbers(input)

        return symbolPositions
            .flatMap { pos -> adjacentPartNumbers(pos, partNumbers) }
            .toSet()
            .sumOf { it.number }
    }

    private fun adjacentPartNumbers(pos: Point2D, partNumbers: Array<Array<PartNumber?>>): Set<PartNumber> {
        val (x, y) = pos
        return (y - 1..y + 1).flatMap { _y ->
            (x - 1..x + 1).mapNotNull { _x ->
                partNumbers[_y][_x]
            }
        }.toSet()
    }

    private fun parseSymbols(input: String): List<Point2D> = input
        .split("\n")
        .flatMapIndexed { y, row ->
            """[^0-9.]""".toRegex()
                .findAll(row)
                .map { result -> Point2D(result.range.first, y) }
        }

    private fun parsePartNumbers(input: String): Array<Array<PartNumber?>> {
        val rows = input.split("\n")
        val board = Array(rows.size) { Array<PartNumber?>(rows.first().length) { null } }

        rows.forEachIndexed { y, row ->
            "\\d+".toRegex()
                .findAll(row)
                .forEach { result ->
                    val partNumber = PartNumber(result.value.toInt(), result.range, y)
                    partNumber.xRange.forEach { x -> board[y][x] = partNumber }
                }
        }
        return board
    }

    data class PartNumber(val number: Int, val xRange: IntRange, val y: Int)

    data class Point2D(val x: Int, val y: Int)

}

class Day03b(inputFileName: String) : Day(inputFileName) {
    fun solve(): Int {
        val gearCandidates = parseGearCandidates(input)
        val partNumbers = parsePartNumbers(input)

        return gearCandidates
            .map { (x, y) ->
                val neighbouringPartNumbers = mutableSetOf<PartNumber>()
                (y - 1..y + 1).forEach { _y ->
                    (x - 1..x + 1).forEach { _x ->
                        partNumbers[_y][_x]?.let { neighbouringPartNumbers.add(it) }
                    }
                }
                neighbouringPartNumbers
            }
            .filter { partNumbers_ -> partNumbers_.size == 2 }
            .sumOf { partNumbers_ ->
                partNumbers_
                    .map { it.number }
                    .reduce { acc, number -> acc * number }
                    .also { println(it) }
            }
    }

    private fun parseGearCandidates(input: String): List<Point2D> = input
        .split("\n")
        .flatMapIndexed { y, row ->
            "\\*".toRegex()
                .findAll(row)
                .map { result -> Point2D(result.range.first, y) }
        }

    private fun parsePartNumbers(input: String): Array<Array<PartNumber?>> {
        val rows = input.split("\n")
        val board = Array(rows.size) { Array<PartNumber?>(rows.first().length) { null } }

        rows.forEachIndexed { y, row ->
            "\\d+".toRegex()
                .findAll(row)
                .forEach { result ->
                    val partNumber = PartNumber(result.value.toInt(), result.range, y)
                    partNumber.xRange.forEach { x -> board[y][x] = partNumber }
                }
        }
        return board
    }

    data class PartNumber(val number: Int, val xRange: IntRange, val y: Int)

    data class Point2D(val x: Int, val y: Int)
}