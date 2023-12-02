import kotlin.system.measureTimeMillis


fun main() {
    val durationA = measureTimeMillis { println("Solution for Day02a: ${Day02a("input/02.txt").solve()}") }
    println("solution took $durationA milliseconds")

//    val durationB = measureTimeMillis { println("Solution for Day02b: ${Day02b("input/02.txt").solve()}") }
//    println("solution took $durationB milliseconds")
}

class Day02a(inputFileName: String) : Day(inputFileName) {
    private val cubesPerColor = mapOf("red" to 12, "green" to 13, "blue" to 14)
    private val gameIdPattern = """Game (\d+): .*""".toRegex()

    fun solve(): Int = input.split("\n")
        .sumOf { it ->
            cubesPerColor.keys.forEach { color ->
                val allHandsInGame = getCubeColorPattern(color).findAll(it)
                allHandsInGame.forEach {
                    val cubesOfColorInHand = it.groupValues[1].toInt()
                    if (cubesOfColorInHand > cubesPerColor[color]!!) return@sumOf 0
                }
            }
            val (_, gameIdString) = gameIdPattern.find(it)!!.groupValues
            val gameId = gameIdString.toInt()
            gameId
        }

    private fun getCubeColorPattern(color: String) = """(\d+) $color""".toRegex()
}