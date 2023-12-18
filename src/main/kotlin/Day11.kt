import kotlin.math.absoluteValue
import kotlin.system.measureTimeMillis


fun main() {
    val durationA = measureTimeMillis { println("Solution for Day11a: ${Day11a("input/11.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day11b: ${Day11b("input/11.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

open class Day11a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Long {
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

    fun pairUpGalaxies(galaxies: Set<Galaxy>) = galaxies.flatMapIndexed { i, gal ->
        galaxies.drop(i + 1).map { other -> gal to other }
    }.toSet()

    fun parseCosmos(input: String): Array<CharArray> = input.lines().map { it.toCharArray() }.toTypedArray()

    data class Galaxy(val x: Long, val y: Long) {
        companion object {
            fun setFrom(expandedCosmos: Array<CharArray>): Set<Galaxy> {
                return expandedCosmos.flatMapIndexed { y, row ->
                    row.toList().mapIndexedNotNull { x, char ->
                        if (char == '#') Galaxy(x.toLong(), y.toLong())
                        else null
                    }
                }.toSet()
            }
        }
    }
}

class Day11b(inputFileName: String) : Day11a(inputFileName) {
    override fun solve(): Long {
        val cosmos = parseCosmos(input)
        val expandedCosmos = expandReallyFast(cosmos)

        val galaxies = Galaxy.setFrom(expandedCosmos)
        val pairedGalaxies = pairUpGalaxies(galaxies)

        val distances = getExpandedDistances(pairedGalaxies, expandedCosmos, 1000000)

        return distances.sum()
    }

    fun getExpandedDistances(
        pairedGalaxies: Set<Pair<Galaxy, Galaxy>>,
        expandedCosmos: Array<CharArray>,
        expansionFactor: Long
    ) = pairedGalaxies.map { (galaxy, other) ->
        val xs = listOf(galaxy.x, other.x).sorted()
        val horizontalPath = ((xs.first() + 1)..xs.last())
            .map { x -> expandedCosmos[galaxy.y.toInt()][x.toInt()] }

        val ys = listOf(galaxy.y, other.y).sorted()
        val verticalPath = ((ys.first() + 1)..ys.last())
            .map { y -> expandedCosmos[y.toInt()][galaxy.x.toInt()] }

        val path = horizontalPath + verticalPath

        val expanses = path.count { it == 'X' } * expansionFactor
        val shorts = path.count { it != 'X' }
        expanses + shorts
    }

    fun expandReallyFast(cosmos: Array<CharArray>): Array<CharArray> {
        val expandedCosmos = cosmos.map { row ->
            if (row.all { it == '.' }) CharArray(row.size) { 'X' } else row
        }.toTypedArray()

        repeat(cosmos[0].count()) { x ->
            if(cosmos.all { it[x] == '.' }) expandedCosmos.forEach { it[x] = 'X' }
        }

        return expandedCosmos
    }
}