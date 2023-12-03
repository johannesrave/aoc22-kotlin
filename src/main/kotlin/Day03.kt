import kotlin.system.measureTimeMillis


fun main() {
    val durationA = measureTimeMillis { println("Solution for Day03a: ${Day03a("input/03.txt").solve()}") }
    println("solution took $durationA milliseconds")

//    val durationB = measureTimeMillis { println("Solution for Day03b: ${Day03b("input/03.txt").solve()}") }
//    println("solution took $durationB milliseconds")
}

class Day03a(inputFileName: String) : Day(inputFileName) {
    private val symbolRegex = """[^0-9.]""".toRegex()
    private val numberRegex = """[0-9]+""".toRegex()

    fun solve(): Int {
        val contactZones = getContactZones(parseSymbols(input))
        val numbers = parseNumbers(input)


        val numbersInContact = emptySet<Triple<Int, IntRange, Int>>().toMutableSet()
        contactZones.forEach { (ys, xs) ->
            ys.forEach { y ->
                xs.forEach { zoneXRange ->
                    numbers[y]?.forEach { (numberXRange, number) ->
                        if (zoneXRange.intersect(numberXRange).isNotEmpty()) {
                            numbersInContact.add(Triple(y, numberXRange, number))
                        }
                    }
                }
            }
        }

        return numbersInContact.sumOf { it.third }
    }

    private fun getContactZones(symbols: Map<Int, Set<Int>>): Map<IntRange, Set<IntRange>> {
        return symbols
            .mapKeys { (y) -> y - 1..y + 1 }
            .mapValues { (_, xs) -> xs.map { x -> (x - 1)..(x + 1) }.toSet() }
    }

    private fun parseSymbols(input: String): Map<Int, Set<Int>> {
        val symbols = emptyMap<Int, MutableSet<Int>>().toMutableMap()
        input.split("\n").forEachIndexed { y, row ->
            symbolRegex.findAll(row).forEach { result ->
                val x = result.range.first
                symbols[y]?.add(x) ?: (symbols.put(y, mutableSetOf(x)))
            }
        }
        return symbols
    }

    private fun parseNumbers(input: String): MutableMap<Int, MutableMap<IntRange, Int>> {
        val numbers = emptyMap<Int, MutableMap<IntRange, Int>>().toMutableMap()
        input.split("\n").forEachIndexed { y, row ->
            numberRegex.findAll(row).forEach { result ->
                val number = result.value.toInt()
                val xRange = result.range
                if (numbers.containsKey(y)) {
                    numbers[y]!![xRange] = number
                } else {
                    numbers[y] = mutableMapOf(xRange to number)
                }
            }
        }
        return numbers
    }
}


/*
class Day03b(inputFileName: String) : Day(inputFileName) {
    private val cubesPerColor = mapOf("red" to 12, "green" to 13, "blue" to 14)

    fun solve(): Int = input.split("\n")
        .sumOf { it ->
            val productOfMaxCubesPerColor = cubesPerColor.keys.map { color ->
                val allHandsInGame = getColorRegex(color).findAll(it)
                allHandsInGame.maxOf { it.groupValues[1].toInt() }
            }.fold(1) { product, maxCubesPerColor -> product * maxCubesPerColor }
            return@sumOf productOfMaxCubesPerColor
        }

    private fun getColorRegex(color: String) = """(\d+) $color""".toRegex()
}*/