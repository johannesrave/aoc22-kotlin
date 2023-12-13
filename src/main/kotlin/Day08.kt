import kotlin.system.measureTimeMillis


fun main() {
    val durationA = measureTimeMillis { println("Solution for Day08a: ${Day08a("input/08.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day08b: ${Day08b("input/08.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

open class Day08a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Int {
        return -1
    }
}

class Day08b(inputFileName: String) : Day08a(inputFileName) {
    override fun solve(): Int {
        return -1
    }
}