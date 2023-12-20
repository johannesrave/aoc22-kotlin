import kotlin.system.measureTimeMillis

fun main() {
    val durationA = measureTimeMillis { println("Solution for Day14a: ${Day14a("input/14.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day14b: ${Day14b("input/14.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

open class Day14a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Int {
        return -1
    }
}

class Day14b(inputFileName: String) : Day14a(inputFileName) {
    override fun solve(): Int {
        return -1
    }
}