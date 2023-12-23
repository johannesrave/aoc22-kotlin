import kotlin.system.measureTimeMillis

fun main() {
    val durationA = measureTimeMillis { println("Solution for Day16a: ${Day16a("input/16.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day16b: ${Day16b("input/16.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

open class Day16a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Int {
        return -1
    }
}

class Day16b(inputFileName: String) : Day16a(inputFileName) {
    override fun solve(): Int {
        return -1
    }
}