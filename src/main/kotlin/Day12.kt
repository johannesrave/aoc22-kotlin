import kotlin.math.pow
import kotlin.system.measureTimeMillis

fun main() {
    val durationA = measureTimeMillis { println("Solution for Day12a: ${Day12a("input/12.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day12b: ${Day12b("input/12.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

open class Day12a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Int {
        val patternsToSprings = parsePatternsToSprings(input)
        patternsToSprings.forEach { println(it) }

        val variantGenerator = buildVariantGenerator(patternsToSprings)

        val results = patternsToSprings.map { findValidVariants(it, variantGenerator).size }

        return results.sum()
    }

    fun buildVariantGenerator(patternsToSprings: List<Pair<String, List<Int>>>): List<String> {
        val maxWildcards = patternsToSprings.maxOf { (pattern) -> pattern.count { c -> c == '?' } }
        val numberOfVariants = (2.0).pow(maxWildcards).toInt()

        val variantGenerator = (0..numberOfVariants)
            .map { number ->
                number.toString(2).padStart(maxWildcards, '0')
                    .replace('0', '.')
                    .replace('1', '#')
                    .reversed()
            }
        return variantGenerator
    }

    fun findValidVariants(
        patternToSprings: Pair<String, List<Int>>,
        variantGenerator: List<String>
    ): Collection<String> {
        val (springPattern, springs) = patternToSprings
        val regex = ("""^[.?]*[#?]{${springs.first()}}""" +
                springs.drop(1).joinToString("") { """[.?]+[#?]{$it}""" }
                + """[.?]*$""").toRegex()

        val numberOfWildcards = springPattern.count { c -> c == '?' }
        val numberOfVariants = (2.0).pow(numberOfWildcards).toInt()

        val variants = mutableSetOf<String>()
        val iterator = variantGenerator.iterator()
        repeat(numberOfVariants) {
            val chars = iterator.next()
                .take(numberOfWildcards)
                .iterator()
            val sb = StringBuilder()
            springPattern.forEach { c -> (if (c == '?') chars.next() else c).let { sb.append(it) } }
            val variant = sb.toString()

            if (regex.containsMatchIn(variant)) variants.add(variant)
        }

        return variants
    }

    private fun parsePatternsToSprings(input: String): List<Pair<String, List<Int>>> = input.lines()
        .map { line ->
            val (pattern, rawInts) = line.split(" ")
            pattern.trim('.') to rawInts.split(",").map { it.toInt() }
        }
}

class Day12b(inputFileName: String) : Day12a(inputFileName) {
    override fun solve(): Int {
        return -1
    }
}