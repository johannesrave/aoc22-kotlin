import kotlin.system.measureTimeMillis


fun main() {
    val durationA = measureTimeMillis { println("Solution for Day03a: ${Day03a("input/03.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day03b: ${Day03b("input/03.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

class Day03a(inputFileName: String) : Day(inputFileName) {
    private val symbolRegex = """[^0-9.]""".toRegex()
    private val numberRegex = """[0-9]+""".toRegex()

    fun solve(): Int {
        val contactZones = getContactZones(parseSymbols(input))
        val numbers = parseNumbers(input)

        val numbersInContact = emptySet<Triple<Int, IntRange, Int>>().toMutableSet()
        contactZones.forEach { (ys, xs) ->
            ys.forEach { y ->
                xs.forEach { zoneXRange ->
                    numbers[y]?.forEach { (numberXRange, number) ->
                        if (zoneXRange.intersect(numberXRange).isNotEmpty()) {
                            numbersInContact.add(Triple(y, numberXRange, number))
                        }
                    }
                }
            }
        }

        return numbersInContact.sumOf { it.third }
    }

    private fun getContactZones(symbols: Map<Int, Set<Int>>): Map<IntRange, Set<IntRange>> {
        return symbols
            .mapKeys { (y) -> y - 1..y + 1 }
            .mapValues { (_, xs) -> xs.map { x -> (x - 1)..(x + 1) }.toSet() }
    }

    private fun parseSymbols(input: String): Map<Int, Set<Int>> {
        val symbols = emptyMap<Int, MutableSet<Int>>().toMutableMap()
        input.split("\n").forEachIndexed { y, row ->
            symbolRegex.findAll(row).forEach { result ->
                val x = result.range.first
                symbols[y]?.add(x) ?: (symbols.put(y, mutableSetOf(x)))
            }
        }
        return symbols
    }

    private fun parseNumbers(input: String): MutableMap<Int, MutableMap<IntRange, Int>> {
        val numbers = emptyMap<Int, MutableMap<IntRange, Int>>().toMutableMap()
        input.split("\n").forEachIndexed { y, row ->
            numberRegex.findAll(row).forEach { result ->
                val number = result.value.toInt()
                val xRange = result.range
                if (numbers.containsKey(y)) {
                    numbers[y]!![xRange] = number
                } else {
                    numbers[y] = mutableMapOf(xRange to number)
                }
            }
        }
        return numbers
    }
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
        val numbers = Array(rows.size) { Array<PartNumber?>(rows.first().length) { null } }

        rows.forEachIndexed { y, row ->
            "\\d+".toRegex()
                .findAll(row)
                .forEach { result ->
                    val partNumber = PartNumber(result.value.toInt(), result.range, y)
                    partNumber.xRange.forEach { x -> numbers[y][x] = partNumber }
                }
        }
        return numbers
    }

    data class PartNumber(val number: Int, val xRange: IntRange, val y: Int)

    data class Point2D(val x: Int, val y: Int)
}