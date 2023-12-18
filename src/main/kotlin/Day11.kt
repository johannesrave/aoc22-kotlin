import kotlin.math.absoluteValue
import kotlin.system.measureTimeMillis


fun main() {
    val durationA = measureTimeMillis { println("Solution for Day11a: ${Day11a("input/11.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day11b: ${Day11b("input/11.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

open class Day11a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Int {
        val cosmos = parseCosmos(input)
        val expandedCosmos = expand(cosmos)

        val galaxies = Galaxy.setFrom(expandedCosmos)
        val pairedGalaxies = galaxies.flatMapIndexed { i, gal ->
            galaxies.drop(i + 1).map { other -> gal to other }
        }.toSet()

        val distances = pairedGalaxies.map { (galaxy, other) ->
            (galaxy.x - other.x).absoluteValue + (galaxy.y - other.y).absoluteValue
        }

        return distances.sum()
    }

    private fun expand(cosmos: Array<CharArray>): Array<CharArray> {
        val rowsToEnter = cosmos.mapIndexedNotNull { i, chars ->
            i.takeIf { chars.all { it == '.' } }
        }.reversed()

        val colsToEnter = cosmos.first().indices.mapNotNull { i ->
            i.takeIf { cosmos.all { row -> row[i] == '.' } }
        }.reversed()


        val expandedCosmos = cosmos.map { it.toMutableList() }.toMutableList()

        colsToEnter.forEach { x -> expandedCosmos.forEach { row -> row.add(x, '.') } }

        if (expandedCosmos.size >= 1) {
            val emptyRow = MutableList(expandedCosmos[0].size) { '.' }

            rowsToEnter.forEach { y -> expandedCosmos.add(y, emptyRow) }
        }

        return expandedCosmos.map { it.toCharArray() }.toTypedArray()
    }

    private fun parseCosmos(input: String): Array<CharArray> = input.lines().map { it.toCharArray() }.toTypedArray()

    data class Galaxy(val x: Int, val y: Int) {
        companion object {
            fun setFrom(expandedCosmos: Array<CharArray>): Set<Galaxy> {
                return expandedCosmos.flatMapIndexed { y, row ->
                    row.toList().mapIndexedNotNull { x, char ->
                        if (char == '#') Galaxy(x, y)
                        else null
                    }
                }.toSet()
            }
        }
    }
}

class Day11b(inputFileName: String) : Day11a(inputFileName) {
    override fun solve(): Int {
        return -1
    }
}