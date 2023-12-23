import kotlin.system.measureTimeMillis

fun main() {
    val durationA = measureTimeMillis { println("Solution for Day15a: ${Day15a("input/15.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day15b: ${Day15b("input/15.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

open class Day15a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Int {
        return -1
    }
}

class Day15b(inputFileName: String) : Day15a(inputFileName) {
    override fun solve(): Int {
        return -1
    }
}