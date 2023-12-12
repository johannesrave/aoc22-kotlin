import kotlin.system.measureTimeMillis


fun main() {
    val durationA = measureTimeMillis { println("Solution for Day05a: ${Day05a("input/05.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day05b: ${Day05b("input/05.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

open class Day05a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Long {
        val seeds = parseSeeds(input)
        val horticulturalMappings = parseHorticulturalSteps(input)

        return horticulturalMappings
            .fold(seeds) { items, mapping ->
                items.map { item ->
                    mapping.criterionRanges.find { item in it.range }
                        ?.let { item + it.offset } ?: item
                }
            }.min()
    }

    private fun parseSeeds(input: String) = input.split("\n\n")
        .take(1).flatMap { line -> line.split(" ").drop(1).map { it.toLong() } }

    fun parseHorticulturalSteps(input: String) = input.split("\n\n")
        .drop(1)
        .map { block ->
            val lines = block.split("\n")
            val name = lines[0].split(" ")[0]
            val criterionRanges = lines.drop(1)
                .map { it.split(" ").map { it.toLong() } }
                .map {
                    val (destination, source, length) = it
                    CriterionRange(Day05b.SeedRange(source, source + length - 1), destination - source)
                }.sortedBy { it.range.first }
            HorticulturalStep(name, criterionRanges)
        }

    data class CriterionRange(val range: Day05b.SeedRange, val offset: Long)
    data class HorticulturalStep(val name: String, val criterionRanges: List<CriterionRange>)
}

class Day05b(inputFileName: String) : Day05a(inputFileName) {
    override fun solve(): Long {
        val allSeedRanges = parseSeedRanges(input)
        val horticulturalSteps = parseHorticulturalSteps(input)

        /*
        * algo:
        * 1. iterate over horticultural steps, transforming seedRanges if necessary
        * 2. for each criterionRange of the current step:
        *   3. check whether the seedRange and criterionRange overlap
        *       3a. seedRange is completely within
        *   4. if yes, build list of transformed and/or untouched seedRanges and flatten it
        *   5. if horticultural steps remain, go to 2.
        * 6. find range with minimal start/lower bound and return that
        * */

        return allSeedRanges.horticulturate(horticulturalSteps).minBy { it.first }.first
    }

    private fun parseSeedRanges(input: String): List<SeedRange> {
        val firstLine = input.split("\n\n").first()

        val ranges = """\d+""".toRegex()
            .findAll(firstLine)
            .map { it.value.toLong() }
            .chunked(2)
            .toList()
            .map {
                val (start, length) = it.first() to it.last()
                val range = SeedRange(start, start + length - 1)
                range
            }.sortedBy { it.first }
        return ranges
    }


    data class SeedRange(val first: Long, val last: Long) {
        operator fun contains(num: Long): Boolean {
            return num >= this.first && num <= this.last
        }

        fun overlaps(otherRange: SeedRange): Boolean {
            return !(last <= otherRange.first || first >= otherRange.last)
        }

        fun union(otherRange: SeedRange): SeedRange {
            if (!this.overlaps(otherRange)) throw IllegalArgumentException("Can't build UNION, ranges don't overlap.")

            return when {
                first in otherRange && last in otherRange -> otherRange
                first in otherRange && last !in otherRange -> SeedRange(otherRange.first, last)
                first !in otherRange && last in otherRange -> SeedRange(first, otherRange.last)
                first !in otherRange && last !in otherRange -> this
                else -> throw IllegalStateException()
            }
        }

        fun intersection(otherRange: SeedRange): SeedRange {
            return when {
                first in otherRange && last in otherRange -> this
                first in otherRange && last !in otherRange -> SeedRange(first, otherRange.last)
                first !in otherRange && last in otherRange -> SeedRange(otherRange.first, last)
                first !in otherRange && last !in otherRange -> otherRange
                else -> throw IllegalStateException()
            }
        }

        operator fun minus(otherRange: SeedRange): List<SeedRange> {
            return when {
                first in otherRange && last in otherRange -> listOf()

                first !in otherRange && last in otherRange -> listOf(SeedRange(first, otherRange.first - 1))

                first in otherRange && last !in otherRange -> listOf(SeedRange(otherRange.last + 1, last))

                first !in otherRange && last !in otherRange ->
                    listOf(SeedRange(first, otherRange.first - 1), SeedRange(otherRange.last + 1, last))

                else -> throw IllegalStateException()
            }
        }

        fun shift(offset: Long): SeedRange = SeedRange(first + offset, last + offset)

        fun minusMultiple(testCriterionRanges: List<SeedRange>): List<SeedRange> {
            if (testCriterionRanges.isEmpty())
                return listOf(this)

            val sortedCritRanges = testCriterionRanges.sortedBy { it.first }

            val result = listOfNotNull(
                SeedRange(first, sortedCritRanges.first().first - 1).takeIf { it.first <= it.last },
                SeedRange(sortedCritRanges.last().last + 1, last).takeIf { it.first <= it.last }
            ).toMutableList()

            if (sortedCritRanges.size > 1) {
                for (index in 0..<sortedCritRanges.size-1) {
                    val cur = sortedCritRanges[index]
                    val next = sortedCritRanges[index + 1]
                    result.add(SeedRange(cur.last + 1, next.first - 1))
                }
            }
            return result.sortedBy { it.first }
        }
    }
}

fun List<Day05b.SeedRange>.horticulturate(horticulturalSteps: List<Day05a.HorticulturalStep>): List<Day05b.SeedRange> =
    horticulturalSteps
        .fold(this) { seedRanges, (name,criterionRanges) ->
//            val criterionRanges = step.criterionRanges
            println(name)
            return@fold seedRanges.flatMap { seedRange ->
                val relevantCritRanges = criterionRanges.filter { seedRange.overlaps(it.range) }
                val untransformed = seedRange.minusMultiple(relevantCritRanges.map { it.range })

                val shifted = relevantCritRanges.map { (range, offset) ->
                    seedRange.intersection(range).shift(offset)
                }

                val result = (shifted + untransformed).sortedBy { it.first }
                println("seedRange")
                println(seedRange)
                println("relevantCritRanges")
                println(relevantCritRanges)
                println("untransformed")
                println(untransformed)
                println("shifted")
                println(shifted)
                println("result")
                println(result)

                return@flatMap result
            }
        }