import kotlin.system.measureTimeMillis


fun main() {
    val durationA = measureTimeMillis { println("Solution for Day08a: ${Day08a("input/08.txt").solve()}") }
    println("solution took $durationA milliseconds")

    val durationB = measureTimeMillis { println("Solution for Day08b: ${Day08b("input/08.txt").solve()}") }
    println("solution took $durationB milliseconds")
}

open class Day08a(inputFileName: String) : Day(inputFileName) {
    open fun solve(): Int {
        val nodeMap = Node.parseMap(input)
        val instructionSequence = Instruction.sequenceFrom(input)

        var currentLabel = "AAA"
        val finalLabel = "ZZZ"

        instructionSequence.forEachIndexed { i, inst ->
            if (currentLabel == finalLabel) return i
            currentLabel = nodeMap[currentLabel]!!.move(inst)
        }

        println(nodeMap)
        println(instructionSequence)

        return -1
    }

    data class Node(val label: String, val left: String, val right: String) {
        fun move(instruction: Instruction) = when (instruction) {
            Instruction.L -> this.left
            Instruction.R -> this.right
        }

        companion object {
            fun parseMap(input: String): Map<String, Node> =
                """([A-Z]{3}) = \(([A-Z]{3}), ([A-Z]{3})\)""".toRegex().findAll(input).map {
                    val (_, label, left, right) = it.groupValues
                    label to Node(label, left, right)
                }.toMap()
        }
    }

    enum class Instruction {
        L, R;

        companion object {
            fun parseList(input: String): List<Instruction> = input.lines().first().map {
                when (it) {
                    'L' -> L
                    'R' -> R
                    else -> throw IllegalArgumentException("'$it' is not a valid Instruction.")
                }
            }

            fun sequenceFrom(input: String): Sequence<Instruction> =
                parseList(input).asSequence().repeat()
        }
    }
}

class Day08b(inputFileName: String) : Day08a(inputFileName) {
    override fun solve(): Int {
        val nodeMap = Node.parseMap(input)
        val instructionSequence = Instruction.sequenceFrom(input)
        val noOfInstructions = Instruction.parseList(input).size

        var currentLabels = nodeMap.filter { (label, _) -> label[2] == 'A' }.map { (label) -> label }
        val initialLabels = currentLabels.map { label -> nodeMap[label]!!.move(Instruction.L) }
        val finalLabels = nodeMap.filter { (label, _) -> label[2] == 'Z' }.map { (label) -> label }

        println("initialLabels:     $initialLabels")
        println("currentLabels:     $currentLabels")
        println("finalLabels:       $finalLabels")
        println("noOfInstructions:  $noOfInstructions")

        instructionSequence.forEachIndexed { i, inst ->
            if (i % 100_000_000 == 0) println("$i moves made. Current labels: $currentLabels")
            if (currentLabels.all { it[2] == 'Z' }) return i
            (currentLabels zip initialLabels)
                .forEach { (cur, init) ->
                    if (cur == init && i-1 % noOfInstructions == 0) println("$cur has cycled back in $i moves")
                }

            currentLabels.filter { it[2] == 'Z' }
                .also {
                    if (it.size > 3) {
                        println("${it.size} ghosts are on a final Node after $i moves: $it")
                    }
                }

            currentLabels = currentLabels.map { label -> nodeMap[label]!!.move(inst) }
        }

        return -1
    }
}

class Memoize1<in T, out R>(val f: (T) -> R) : (T) -> R {
    private val values = mutableMapOf<T, R>()
    override fun invoke(x: T): R {
        return values.getOrPut(x, { f(x) })
    }
}

fun <T, R> ((T) -> R).memoize(): (T) -> R = Memoize1(this)

val memoizedSumFactors = { x: Int -> (x + x) }.memoize()