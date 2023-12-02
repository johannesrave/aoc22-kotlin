import kotlin.system.measureTimeMillis

fun main() {
    val durationA = measureTimeMillis { println("Solution for Day02a: ${Day02a("input/02.txt").solve()}") }
    println("Solving Day02a took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day02b: ${Day02b("input/02.txt").solve()}") }
    println("Solving Day02b took $durationB milliseconds")
}

class Day02a(inputFileName: String) : Day(inputFileName) {
    private val gameIdPattern = """Game (\d+): .*""".toRegex()

    fun solve(): Int = input
        .split("\n")
        .sumOf { gameRound ->
            Color.entries.forEach { color ->
                if (!color.allOccurrencesArePossible(gameRound)) return@sumOf 0
            }
            val (_, gameIdString) = gameIdPattern.find(gameRound)!!.groupValues
            val gameId = gameIdString.toInt()
            gameId
        }

    enum class Color(color: String, private val maxAmount: Int) {
        R("red", 12), G("green", 13), B("blue", 14);

        private val colorOccurrenceRegex = """(\d+) $color""".toRegex()

        fun allOccurrencesArePossible(round: String) = colorOccurrenceRegex
            .findAll(round)
            .map { match -> match.groupValues[1].toInt() }
            .all { amountOfCubes -> (amountOfCubes <= maxAmount) }
    }
}


class Day02b(inputFileName: String) : Day(inputFileName) {
    fun solve(): Int = input
        .split("\n")
        .sumOf { gameRound ->
            return@sumOf Color.entries
                .map { color -> color.findMaxOccurrenceInRound(gameRound) }
                .reduce { product, maxCubesPerColor -> product * maxCubesPerColor }
        }

    enum class Color(color: String) {
        R("red"), G("green"), B("blue");

        private val colorOccurrenceRegex = """(\d+) $color""".toRegex()

        fun findMaxOccurrenceInRound(round: String) = colorOccurrenceRegex
            .findAll(round)
            .maxOf { match -> match.groupValues[1].toInt() }
    }
}