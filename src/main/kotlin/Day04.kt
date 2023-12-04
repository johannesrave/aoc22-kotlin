import kotlin.math.pow
import kotlin.system.measureTimeMillis


fun main() {
    val durationA = measureTimeMillis { println("Solution for Day04a: ${Day04a("input/04.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day04b: ${Day04b("input/04.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

open class Day04a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Int {
        val lotteryCards = parseLotteryCards(input)

        return lotteryCards.map { (winningNumbers, numbersYouHave) ->
            winningNumbers.intersect(numbersYouHave)
        }.sumOf { 2.toDouble().pow(it.size-1).toInt() }
    }

    private fun parseLotteryCards(input: String): List<Pair<Set<Int>, Set<Int>>> {
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

class Day04b(inputFileName: String) : Day04a(inputFileName) {
    override fun solve(): Int {
        return 0
    }
}