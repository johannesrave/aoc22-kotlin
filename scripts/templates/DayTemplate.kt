import kotlin.system.measureTimeMillis

fun main() {
    val durationA = measureTimeMillis { println("Solution for DayDAY_PLACEHOLDERa: ${DayDAY_PLACEHOLDERa("input/DAY_PLACEHOLDER.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for DayDAY_PLACEHOLDERb: ${DayDAY_PLACEHOLDERb("input/DAY_PLACEHOLDER.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

open class DayDAY_PLACEHOLDERa(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Int {
        return -1
    }
}

class DayDAY_PLACEHOLDERb(inputFileName: String) : DayDAY_PLACEHOLDERa(inputFileName) {
    override fun solve(): Int {
        return -1
    }
}