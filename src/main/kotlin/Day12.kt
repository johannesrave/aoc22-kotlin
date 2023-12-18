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

        val maxWildcards = input.lines().maxOf { it.count { c -> c == '?' } }
        val permutations = buildPermutations('.', '#', maxWildcards)

        val results = patternsToSprings.map { findValidVariants(it).size }

        return results.sum()
    }

    private fun buildPermutations(c: Char, c1: Char, maxWildcards: Int) {
        CharArray(maxWildcards) { c }
    }

    fun findValidVariants(patternToSprings: Pair<String, List<Int>>): Collection<String> {
        val (pattern, springs) = patternToSprings
        val regex = ("""^[.?]*[#?]{${springs.first()}}""" +
                springs.drop(1).joinToString("") { """[.?]+[#?]{$it}""" }
                + """[.?]*$""").toRegex()

        val numberOfWildcards = pattern.count { c -> c == '?' }
        val numberOfVariants = (2.0).pow(numberOfWildcards).toInt()

        val variants = mutableSetOf<String>()
        (0..numberOfVariants).map { number ->
            val chars = number.toString(2).padStart(numberOfWildcards, '0')
                .replace('0', '.')
                .replace('1', '#')
                .iterator()

            val variant = pattern.map { if (it == '?') chars.next() else it }.joinToString("")

            //println(variant)

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