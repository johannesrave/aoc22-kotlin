import kotlin.system.measureTimeMillis


fun main() {
    val durationA = measureTimeMillis { println("Solution for Day05a: ${Day05a("input/05.txt").solve()}") }
    println("solution took $durationA milliseconds")

//    val durationB = measureTimeMillis { println("Solution for Day05b: ${Day05b("input/05.txt").solve()}") }
//    println("solution took $durationB milliseconds")
}

open class Day05a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Long {
        val seeds = parseSeeds(input)
        val horticulturalMappings = parseHorticulturalMappingRanges(input)

        return horticulturalMappings
            .fold(seeds) { items, mapping ->
//                println(mapping.name)
                items.map { item ->
                    mapping.transformations.find { item in it.criterion }
                        ?.let { item + it.offset } ?: item
                }
//                    .also { println(it) }
            }.min()
    }

    private fun parseSeeds(input: String) = input.split("\n\n")
        .take(1).flatMap { line -> line.split(" ").drop(1).map { it.toLong() } }

    private fun parseHorticulturalMappingRanges(input: String) = input.split("\n\n")
        .drop(1)
        .map { block ->
            val lines = block.split("\n")
            val name = lines[0].split(" ")[0].also { println(it) }
            val transformations = lines.drop(1)
                .map { it.split(" ").map { it.toLong() } }
                .map {
                    val (destination, source, length) = it
                    Transformation(source..source + length, destination - source).also { println(it) }
                }
            HorticulturalMapping(name, transformations)
        }

    data class HorticulturalMapping(val name: String, val transformations: List<Transformation>)
    data class Transformation(val criterion: LongRange, val offset: Long)
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