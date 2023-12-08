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
                    mapping.criterionRanges.find { item in it.criterionRange }
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
                }.sortedBy { it.criterionRange.first }
            HorticulturalStep(name, criterionRanges)
        }

    data class CriterionRange(val criterionRange: Day05b.SeedRange, val offset: Long)
    data class HorticulturalStep(val name: String, val criterionRanges: List<CriterionRange>)
}

class Day05b(inputFileName: String) : Day05a(inputFileName) {
    override fun solve(): Long {
        val allSeedRanges = parseSeedRanges(input).also { println(it) }
        val horticulturalSteps = parseHorticulturalSteps(input).also { println(it) }

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
            if (!this.overlaps(otherRange)) throw IllegalArgumentException("Can't build INTERSECTION, ranges don't overlap.")

            return when {
                first in otherRange && last in otherRange -> this
                first in otherRange && last !in otherRange -> SeedRange(first, otherRange.last)
                first !in otherRange && last in otherRange -> SeedRange(otherRange.first, last)
                first !in otherRange && last !in otherRange -> otherRange
                else -> throw IllegalStateException()
            }
        }


        fun difference(otherRange: SeedRange): List<SeedRange> {
//            if (!this.overlaps(otherRange)) throw IllegalArgumentException("Can't build DIFFERENCE, ranges don't overlap.")

            return when {

                first in otherRange && last in otherRange -> when {
                    first == otherRange.first && last == otherRange.last -> listOf(this)
                    first == otherRange.first -> listOf(SeedRange(last + 1, otherRange.last))
                    last == otherRange.last -> listOf(SeedRange(first, otherRange.first - 1))
                    else -> listOf(SeedRange(first, otherRange.first - 1), SeedRange(last + 1, otherRange.last))
                }

                first in otherRange && last !in otherRange ->
                    listOf(SeedRange(otherRange.first, first - 1), SeedRange(otherRange.last, last))

                first !in otherRange && last in otherRange ->
                    listOfNotNull(
                        SeedRange(first, otherRange.first - 1),
                        SeedRange(last + 1, otherRange.last).takeIf { it.first != it.last })

                first !in otherRange && last !in otherRange ->
                    listOf(SeedRange(first, otherRange.first - 1), SeedRange(otherRange.last, last))

                else -> throw IllegalStateException()
            }
        }

        operator fun minus(otherRange: SeedRange): List<SeedRange> {
            return when {
                first in otherRange && last in otherRange -> listOf()

                first !in otherRange && last in otherRange -> listOfNotNull(SeedRange(first, otherRange.first - 1))

                first in otherRange && last !in otherRange -> listOf(SeedRange(otherRange.last + 1, last))

                first !in otherRange && last !in otherRange ->
                    listOf(SeedRange(first, otherRange.first - 1), SeedRange(otherRange.last + 1, last))

                else -> throw IllegalStateException()
            }
        }

        fun shift(offset: Long): SeedRange = SeedRange(first + offset, last + offset)
    }
}

fun List<Day05b.SeedRange>.horticulturate(horticulturalSteps: List<Day05a.HorticulturalStep>): List<Day05b.SeedRange> {
    return horticulturalSteps
        .fold(this) { seedRanges, step ->
            val transformations = step.criterionRanges
            println(step.name)
            println(seedRanges)
            println(seedRanges)
            seedRanges.map { seedRange ->
                val splitRanges = transformations.flatMap { transformation ->

                    if (seedRange.overlaps(transformation.criterionRange)) {
                        val rangeToTransform = seedRange.intersection(transformation.criterionRange)
                        val rangeToLeaveBe = seedRange.difference(rangeToTransform)

                        listOf(rangeToTransform.shift(transformation.offset), rangeToLeaveBe)
                    } else {
                        listOf(seedRange)
                    }
                }

                return@map splitRanges
            }.also { println(it) }

            return@fold seedRanges
        }
}