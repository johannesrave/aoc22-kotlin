import kotlin.system.measureTimeMillis


fun main() {
    val durationA = measureTimeMillis { println("Solution for Day03a: ${Day03a("input/03.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day03b: ${Day03b("input/03.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

class Day03a(inputFileName: String) : Day(inputFileName) {

    fun solve(): Int {
        val symbolPositions = parsePositionsByPattern(input, """[^0-9.]""")
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

    private fun parsePositionsByPattern(input: String, pattern: String): List<Point2D> {
        return input
            .split("\n")
            .flatMapIndexed { y, row ->
                pattern.toRegex()
                    .findAll(row)
                    .map { result -> Point2D(result.range.first, y) }
            }
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
        val gearCandidates = parsePositionsByPattern(input, "\\*")
        val partNumbers = parsePartNumbers(input)

        return gearCandidates
            .map { pos -> adjacentPartNumbers(pos, partNumbers) }
            .filter { partNumbers_ -> partNumbers_.size == 2 }
            .sumOf { partNumbers_ -> partNumbers_.fold(1 as Int) { acc, number -> acc * number.number } }
    }

    private fun adjacentPartNumbers(pos: Point2D, partNumbers: Array<Array<PartNumber?>>): Set<PartNumber> {
        val (x, y) = pos
        return (y - 1..y + 1).flatMap { _y ->
            (x - 1..x + 1).mapNotNull { _x ->
                partNumbers[_y][_x]
            }
        }.toSet()
    }

    private fun parsePositionsByPattern(input: String, pattern: String): List<Point2D> {
        return input
            .split("\n")
            .flatMapIndexed { y, row ->
                pattern.toRegex()
                    .findAll(row)
                    .map { result -> Point2D(result.range.first, y) }
            }
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