import kotlin.system.measureTimeMillis


fun main() {
    val durationA = measureTimeMillis { println("Solution for Day06a: ${Day06a("input/06.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day06b: ${Day06b("input/06.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

open class Day06a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Long {
        val seeds = parseSeeds(input)
        val horticulturalMappings = parseHorticulturalSteps(input)

        return horticulturalMappings
            .fold(seeds) { items, mapping ->
                items.map { item ->
                    mapping.transformations.find { item in it.range }
                        ?.let { item + it.offset } ?: item
                }
            }.min()
    }

    private fun parseSeeds(input: String) = input.split("\n\n")
        .take(1).flatMap { line -> line.split(" ").drop(1).map { it.toLong() } }

    fun parseHorticulturalSteps(input: String) = input.split("\n\n")
        .drop(1)
        .map { block ->
            val lines = block.split("\n")
            val name = lines[0].split(" ")[0]
            val transformations = lines.drop(1)
                .map { it.split(" ").map { it.toLong() } }
                .map {
                    val (destination, source, length) = it
                    Transformation(Day06b.Range(source, source + length - 1), destination - source)
                }.sortedBy { it.range.first }
            TransformationStep(name, transformations)
        }

}

class Day06b(inputFileName: String) : Day06a(inputFileName) {
    override fun solve(): Long {
        return -1
    }
}