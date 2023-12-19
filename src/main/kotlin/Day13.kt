import kotlin.system.measureTimeMillis

fun main() {
    val durationA = measureTimeMillis { println("Solution for Day13a: ${Day13a("input/13.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day13b: ${Day13b("input/13.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

open class Day13a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Int {
        return -1
    }
}

class Day13b(inputFileName: String) : Day13a(inputFileName) {
    override fun solve(): Int {
        return -1
    }
}