import kotlin.math.pow
import kotlin.system.measureTimeMillis


fun main() {
    val durationA = measureTimeMillis { println("Solution for Day05a: ${Day05a("input/05.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day05b: ${Day05b("input/05.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

open class Day05a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Int {
        val lotteryCards = parseLotteryCards(input)

        return lotteryCards.map { (winningNumbers, numbersYouHave) ->
            winningNumbers.intersect(numbersYouHave)
        }.sumOf { 2.toDouble().pow(it.size - 1).toInt() }
    }

    fun parseLotteryCards(input: String): List<Pair<Set<Int>, Set<Int>>> {
        val cards = input.split("\n")
        return cards.map { card ->
            val banana = card.split(":")[1]
            val (firstHalf, secondHalf) = banana.split("|")
            val winningNumbers = firstHalf.split(" ").filter { it.isNotBlank() }.map { it.toInt() }.toSet()
            val numbersYouHave = secondHalf.split(" ").filter { it.isNotBlank() }.map { it.toInt() }.toSet()

            winningNumbers to numbersYouHave
        }
    }
}

class Day05b(inputFileName: String) : Day05a(inputFileName) {
    override fun solve(): Int {
        val lotteryCards = parseLotteryCards(input)
            .map { (winningNumbers, numbersYouHave) -> LotteryCard(winningNumbers, numbersYouHave) }

        return lotteryCards
            .mapIndexed { i, card ->
                (i + 1..i + card.hits()).forEach { cardIndex ->
                    lotteryCards[cardIndex].copies += card.copies
                }
                card
            }.sumOf { it.copies }
    }

    data class LotteryCard(val winningNumbers: Set<Int>, val numbersYouHave: Set<Int>, var copies: Int = 1) {
        fun hits() = winningNumbers.intersect(numbersYouHave).size
    }
}