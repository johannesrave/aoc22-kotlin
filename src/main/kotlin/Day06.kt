import kotlin.system.measureTimeMillis


fun main() {
    val durationA = measureTimeMillis { println("Solution for Day06a: ${Day06a("input/06.txt").solve()}") }
    println("solution took $durationA milliseconds")

//    val durationB = measureTimeMillis { println("Solution for Day06b: ${Day06b("input/06.txt").solve()}") }
//    println("solution took $durationB milliseconds")
}

open class Day06a(inputFileName: String) : Day(inputFileName) {
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

class Day06b(inputFileName: String) : Day06a(inputFileName) {
    override fun solve(): Int {
        return -1
    }
}