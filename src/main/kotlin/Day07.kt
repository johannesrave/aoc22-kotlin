import kotlin.system.measureTimeMillis


fun main() {
    val durationA = measureTimeMillis { println("Solution for Day07a: ${Day07a("input/07.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day07b: ${Day07b("input/07.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

open class Day07a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Int {
        val (timeLine, distanceLine) = input.lines()
            .map { "\\d+".toRegex().findAll(it).map { match -> match.value.toInt() } }

        return (timeLine zip distanceLine)
            .map { (time, record) ->
                println("time: $time")
                println("record: $record")
                var solutionCounter = 0
                for (ms in 0..<time) {
                    val distanceMoved = ms * (time - ms)
                    if (distanceMoved > record) solutionCounter += 1
                }
                solutionCounter
            }.reduce { result, cur -> result * cur }
    }
}

class Day07b(inputFileName: String) : Day07a(inputFileName) {
    override fun solve(): Int {
        val (time, recordDistance) = parseTimeAndDistance(input)

        var solutionCounter = 0
        for (ms in 0..<time) {
            val distanceMoved = ms * (time - ms)
            if (distanceMoved > recordDistance) solutionCounter += 1
        }
        return solutionCounter
    }

    private fun parseTimeAndDistance(input: String) = input.lines()
        .map {
            "\\d".toRegex()
                .findAll(it)
                .map { match -> match.value }
                .joinToString("")
                .toLong()
        }
}