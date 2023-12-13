import kotlin.system.measureTimeMillis


fun main() {
    val durationA = measureTimeMillis { println("Solution for Day09a: ${Day09a("input/09.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day09b: ${Day09b("input/09.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

open class Day09a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Long {
        return 0
    }
}

class Day09b(inputFileName: String) : Day09a(inputFileName) {
    override fun solve(): Long {
        return 0
    }
}