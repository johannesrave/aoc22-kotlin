import kotlin.system.measureTimeMillis


fun main() {
    val durationA = measureTimeMillis { println("Solution for Day07a: ${Day07a("input/07.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day07b: ${Day07b("input/07.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

open class Day07a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Int {
        val hands = parseHands(input).sorted().also { println(it) }

        return hands.foldIndexed(0) { i, total, hand -> total + ((i + 1) * hand.bid) }
    }

    private fun parseHands(input: String): List<Hand> = "(.{5}) (\\d+)".toRegex()
        .findAll(input)
        .also { it.forEach { println(it.groupValues) } }
        .associate { it.groupValues[1] to it.groupValues[2] }
        .also { println(it) }
        .map { (rawCards, rawBid) ->
            val cards = Card.listFrom(rawCards)
            val occurrences = cards.groupBy { it.name }.map { (_, list) -> list.size }.sortedDescending()
            val type = Type.entries.find { it.matches(occurrences) } ?: throw IllegalStateException()
            Hand(cards, type, rawBid.toInt())
        }

    data class Hand(val cards: List<Card>, val type: Type, val bid: Int) : Comparable<Hand> {
        override fun compareTo(other: Hand): Int {
            if (type.ordinal > other.type.ordinal) return 1
            else if (type.ordinal < other.type.ordinal) return -1

            this.cards.forEachIndexed { i, card ->
                if (card.ordinal > other.cards[i].ordinal) return 1
                else if (card.ordinal < other.cards[i].ordinal) return -1
            }

            return 0
        }
    }

    enum class Card {
        Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace;

        companion object {
            fun listFrom(s: String): List<Card> =
                s.map {
                    when (it) {
                        '2' -> Two
                        '3' -> Three
                        '4' -> Four
                        '5' -> Five
                        '6' -> Six
                        '7' -> Seven
                        '8' -> Eight
                        '9' -> Nine
                        'T' -> Ten
                        'J' -> Jack
                        'Q' -> Queen
                        'K' -> King
                        'A' -> Ace
                        else -> throw IllegalArgumentException("'$it' is not a valid Card-label.")
                    }
                }
        }
    }

    enum class Type(private vararg val sortedDistribution: Int) {

        //@formatter:off
        HighCard(    1, 1, 1, 1, 1),
        OnePair(     2, 1, 1, 1),
        TwoPair(     2, 2, 1),
        ThreeOfAKind(3, 1, 1),
        FullHouse(   3, 2),
        FourOfAKind( 4, 1),
        FiveOfAKind( 5);
        //@formatter:on

        fun matches(sortedOccurrences: List<Int>): Boolean =
            (sortedDistribution zip sortedOccurrences).all { (dist, occ) -> dist == occ }
    }
}

class Day07b(inputFileName: String) : Day07a(inputFileName) {
    override fun solve(): Int {
        return -1
    }
}