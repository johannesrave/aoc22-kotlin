import kotlin.system.measureTimeMillis


fun main() {
    val durationA = measureTimeMillis { println("Solution for Day11a: ${Day11a("input/11.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day11b: ${Day11b("input/11.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

open class Day11a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Int {
        return -1
    }
}

class Day11b(inputFileName: String) : Day11a(inputFileName) {
    override fun solve(): Int {
        return -1
    }
}