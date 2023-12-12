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
                    mapping.transformations.find { item in it.range }
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
            val transformations = lines.drop(1)
                .map { it.split(" ").map { it.toLong() } }
                .map {
                    val (destination, source, length) = it
                    Transformation(Day05b.Range(source, source + length - 1), destination - source)
                }.sortedBy { it.range.first }
            TransformationStep(name, transformations)
        }

    data class Transformation(val range: Day05b.Range, val offset: Long)
    data class TransformationStep(val name: String, val transformations: List<Transformation>)
}

class Day05b(inputFileName: String) : Day05a(inputFileName) {
    override fun solve(): Long {
        val seedRanges = parseSeedRanges(input)
        val horticulturalSteps = parseHorticulturalSteps(input)
        return process(seedRanges, horticulturalSteps).minBy { it.first }.first
    }

    fun process(startingRanges: List<Range>, transformationSteps: List<TransformationStep>): List<Range> =
        transformationSteps.fold(startingRanges) { ranges, (_, transformations) ->
            ranges.flatMap { range ->
                val relevantTransformations = transformations.filter { range.overlaps(it.range) }

                val untransformed = range - (relevantTransformations.map { it.range })
                val transformed = relevantTransformations.map { (transformationRange, offset) ->
                    range.intersection(transformationRange).transform(offset)
                }

                (transformed + untransformed).sortedBy { it.first }
            }
        }

    private fun parseSeedRanges(input: String): List<Range> {
        val (firstLine) = input.lines()

        val ranges = """\d+""".toRegex()
            .findAll(firstLine)
            .map { it.value.toLong() }
            .chunked(2)
            .toList()
            .map {
                val (first, length) = it.first() to it.last()
                Range(first, first + length - 1)
            }.sortedBy { it.first }
        return ranges
    }

    data class Range(val first: Long, val last: Long) {
        operator fun minus(otherRanges: List<Range>): List<Range> {
            if (otherRanges.isEmpty()) return listOf(this)

            val sortedCritRanges = otherRanges.sortedBy { it.first }

            val result = listOfNotNull(
                Range(first, sortedCritRanges.first().first - 1).takeIf { it.first <= it.last },
                Range(sortedCritRanges.last().last + 1, last).takeIf { it.first <= it.last }
            ).toMutableList()

            if (sortedCritRanges.size > 1) {
                for (index in 0..<sortedCritRanges.size - 1) {
                    val cur = sortedCritRanges[index]
                    val next = sortedCritRanges[index + 1]
                    result.add(Range(cur.last + 1, next.first - 1))
                }
            }
            return result.sortedBy { it.first }
        }

        fun transform(offset: Long): Range = Range(first + offset, last + offset)

        fun overlaps(otherRange: Range): Boolean = !(last <= otherRange.first || first >= otherRange.last)

        fun intersection(otherRange: Range): Range = when {
            first in otherRange && last in otherRange -> this
            first in otherRange && last !in otherRange -> Range(first, otherRange.last)
            first !in otherRange && last in otherRange -> Range(otherRange.first, last)
            first !in otherRange && last !in otherRange -> otherRange
            else -> throw IllegalStateException()
        }

        operator fun contains(num: Long): Boolean = num >= this.first && num <= this.last
    }
}