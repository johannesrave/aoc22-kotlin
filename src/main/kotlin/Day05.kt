import kotlin.system.measureTimeMillis


fun main() {
    val durationA = measureTimeMillis { println("Solution for Day05a: ${Day05a("input/05.txt").solve()}") }
    println("solution took $durationA milliseconds")

//    val durationB = measureTimeMillis { println("Solution for Day05b: ${Day05b("input/05.txt").solve()}") }
//    println("solution took $durationB milliseconds")
}

open class Day05a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Int {
        val seeds = parseSeeds(input)
        val horticulturalMappingRanges = parseHorticulturalMappingRanges(input)

        return horticulturalMappingRanges
            .also { println(it) }
            .fold(seeds) { items, ranges ->
                items.map { item ->
                    ranges.find { item in it.range }?.let { item + it.offset } ?: item
                }.also { println(it) }
            }.min()
    }

    private fun parseSeeds(input: String) = input.split("\n\n")
        .take(1).flatMap { line -> line.split(" ").drop(1).map { it.toInt() } }

    fun parseHorticulturalMappingRanges(input: String) = input.split("\n\n")
        .drop(1)
        .map {
            it
                .split("\n")
                .drop(1)
                .map { it.split(" ").map { it.toInt() } }
                .map { HorticulturalMappingRange(it[0]..<it[0] + it[2], it[0] - it[1]) }
        }


    data class HorticulturalMappingRange(val range: IntRange, val offset: Int)
}

//class Day05b(inputFileName: String) : Day05a(inputFileName) {
//    override fun solve(): Int {
//        val lotteryCards = parseLotteryCards(input)
//            .map { (winningNumbers, numbersYouHave) -> LotteryCard(winningNumbers, numbersYouHave) }
//
//        return lotteryCards
//            .mapIndexed { i, card ->
//                (i + 1..i + card.hits()).forEach { cardIndex ->
//                    lotteryCards[cardIndex].copies += card.copies
//                }
//                card
//            }.sumOf { it.copies }
//    }
//
//    data class LotteryCard(val winningNumbers: Set<Int>, val numbersYouHave: Set<Int>, var copies: Int = 1) {
//        fun hits() = winningNumbers.intersect(numbersYouHave).size
//    }
//}