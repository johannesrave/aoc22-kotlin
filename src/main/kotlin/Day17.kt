import kotlin.system.measureTimeMillis

fun main() {
    val durationA = measureTimeMillis { println("Solution for Day17a: ${Day17a("input/17.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day17b: ${Day17b("input/17.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

open class Day17a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Int {
        return -1
    }
}

class Day17b(inputFileName: String) : Day17a(inputFileName) {
    override fun solve(): Int {
        return -1
    }
}